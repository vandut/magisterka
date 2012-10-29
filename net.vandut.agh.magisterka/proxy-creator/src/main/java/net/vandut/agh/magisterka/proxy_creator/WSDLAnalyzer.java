package net.vandut.agh.magisterka.proxy_creator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPathExpressionException;

import net.vandut.agh.magisterka.proxy_creator.internal.xml.IterableNodeList;
import net.vandut.agh.magisterka.proxy_creator.internal.xml.XmlHelper;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class WSDLAnalyzer {

	private static final Logger logger = Logger.getLogger(WSDLAnalyzer.class);

	private final Map<String, String> namespaceMap = new HashMap<String, String>();
	private final Map<String, String> reverseNamespace = new HashMap<String, String>();

	private String targetNamespace;
	private String targetPackage;
	private String typesNamespace;
	private String typesPackage;
	private List<Service> serviceList = new ArrayList<Service>();

	private String NS_WSDL;
	private String NS_XSD;
	private String NS_SOAP;

	private XmlHelper xmlHelper;

	public String getTargetNamespace() {
		return targetNamespace;
	}

	public String getConvertedToPackageTargetNamespace() throws MalformedURLException {
		return convertNamespaceToPackage(targetNamespace);
	}

	public String getTargetPackage() {
		return targetPackage;
	}

	public String getTypesNamespace() {
		return typesNamespace;
	}

	public String getTypesPackage() {
		return typesPackage;
	}

	public List<Service> getServiceList() {
		return serviceList;
	}

	public void analyze(String wsdlFilePath) throws Exception {
		logger.info("Loading WSDL file for analysis");
		xmlHelper = XmlHelper.getInstance();
		xmlHelper.setNamespaceContext(createNamespaceContext());
		Document wsdlDocument = xmlHelper.getDocument(wsdlFilePath);

		discoverNamespaces(wsdlDocument);

		targetNamespace = xmlHelper.eval2string(wsdlDocument, "/" + NS_WSDL + ":definitions/@targetNamespace");
		namespaceMap.put(namespaceMap.get(targetNamespace), targetNamespace);

		typesNamespace = xmlHelper.eval2string(wsdlDocument, "/" + NS_WSDL + ":definitions/" + NS_WSDL + ":types/"
				+ NS_XSD + ":schema/@targetNamespace");

		targetPackage = convertNamespaceToPackage(targetNamespace);
		typesPackage = convertNamespaceToPackage(typesNamespace);

		IterableNodeList wsdlServiceNodeList = xmlHelper.eval2list(wsdlDocument, "/" + NS_WSDL + ":definitions/"
				+ NS_WSDL + ":service");

		for (Node serviceNode : wsdlServiceNodeList) {
			Service service = new Service();
			service.name = xmlHelper.getAttribute(serviceNode, "name");
			service.httpAddress = xmlHelper.eval2string(serviceNode, NS_WSDL + ":port/" + NS_SOAP
					+ ":address/@location");
			String binding = xmlHelper.eval2string(serviceNode, NS_WSDL + ":port/@binding").split(":")[1];
			service.type = xmlHelper.eval2string(serviceNode,
					"/" + NS_WSDL + ":definitions/" + NS_WSDL + ":binding[@name=\"" + binding + "\"]/@type").split(":")[1];
			serviceList.add(service);
		}

		logger.debug("Found target namespace: " + targetNamespace);
		logger.debug("Generated package: " + targetPackage);
		logger.debug("Found types namespace: " + typesNamespace);
		for (Service s : serviceList) {
			logger.debug("Found service: name=" + s.name + " httpAddress=" + s.httpAddress + " type=" + s.type);
		}
	}

	private void discoverNamespaces(Document document) throws XPathExpressionException {
		for (Node n : xmlHelper.eval2list(document, "//namespace::*")) {
			String arg = n.getNodeName();
			String namespace;
			if (arg.contains(":")) {
				namespace = arg.split(":")[1];
			} else {
				namespace = "_def_";
			}
			if (!namespaceMap.containsKey(namespace)) {
				logger.debug("Found namespace: " + namespace + ": " + n.getNodeValue());
				reverseNamespace.put(n.getNodeValue(), namespace);
				namespaceMap.put(namespace, n.getNodeValue());
			}
		}

		NS_WSDL = reverseNamespace.get("http://schemas.xmlsoap.org/wsdl/");
		NS_XSD = reverseNamespace.get("http://www.w3.org/2001/XMLSchema");
		NS_SOAP = reverseNamespace.get("http://schemas.xmlsoap.org/wsdl/soap/");
	}

	private NamespaceContext createNamespaceContext() {
		return new NamespaceContext() {
			public String getNamespaceURI(String prefix) {
				if (namespaceMap.containsKey(prefix)) {
					return namespaceMap.get(prefix);
				}
				if (namespaceMap.containsKey("_def_")) {
					return namespaceMap.get("_def_");
				}
				logger.error("Prefix not found: " + prefix);
				return null;
			}

			public Iterator<?> getPrefixes(String val) {
				return null;
			}

			public String getPrefix(String uri) {
				return null;
			}
		};
	}

	private String convertNamespaceToPackage(String namespace) throws MalformedURLException {
		if (namespace.startsWith("http")) {
			return convertHttpNamespaceToPackage(namespace);
		} else if (namespace.startsWith("urn")) {
			return convertUrnNamespaceToPackage(namespace);
		} else {
			if (namespace.contains("://")) {
				throw new MalformedURLException("unknown protocol: " + namespace);
			} else {
				logger.warn("Unknown namespace signature, trying to parse as standard one: " + namespace);
				return convertHttpNamespaceToPackage("http://" + namespace);
			}
		}
	}

	// http://www.java.net/node/690286
	// http://fusesource.com/docs/esb/4.4/cxf_jaxws/JAXWSDataNamespaceMapping.html
	private String convertHttpNamespaceToPackage(String namespace) throws MalformedURLException {
		URL url = new URL(namespace);
		String[] host = url.getHost().split("\\.");
		String[] path = url.getPath().split("/");

		String pckg = "";
		int stop = 0;
		if (host[0].equalsIgnoreCase("www")) {
			stop = 1;
		}
		for (int i = host.length - 1; i >= stop; i--) {
			pckg += host[i] + ".";
		}

		for (String p : path) {
			if (!p.isEmpty()) {
				pckg += p + ".";
			}
		}

		pckg = pckg.replace("-", "_");

		if (pckg.charAt(0) == '.') {
			pckg = pckg.substring(1);
		}

		return pckg.substring(0, pckg.length() - 1).toLowerCase();
	}

	private String convertUrnNamespaceToPackage(String namespace) throws MalformedURLException {
		return convertHttpNamespaceToPackage("http" + namespace.substring(3));
	}

	public static class Service {
		public String name;
		public String httpAddress;
		public String type;
	}

}
