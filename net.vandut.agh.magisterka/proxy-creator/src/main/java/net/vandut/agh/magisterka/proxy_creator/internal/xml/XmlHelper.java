package net.vandut.agh.magisterka.proxy_creator.internal.xml;

/*
 * Copyright (C) 2011 Konrad Bielak
 * Zabronione rozpowszechnianie tego pliku bez zgody autora
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlHelper {

	private static XmlHelper INSTANCE = null;

	public static XmlHelper getInstance() throws Exception {
		if (INSTANCE == null) {
			INSTANCE = new XmlHelper();
		}
		return INSTANCE;
	}
	private DocumentBuilder documentBuilder;

	private XPath xpath;

	private Map<String, XPathExpression> xpathExpressionsMap = new HashMap<String, XPathExpression>();

	private XmlHelper() throws Exception {
		try {
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			documentBuilder = domFactory.newDocumentBuilder();
			XPathFactory xpathFactory = XPathFactory.newInstance();
			xpath = xpathFactory.newXPath();
		} catch (Exception e) {
			throw new Exception("Failed to initiale XpathResolver", e);
		}
	}
	
	public void setNamespaceContext(NamespaceContext namespaceContext) {
		xpath.setNamespaceContext(namespaceContext);
	}

	public Node eval2node(Node node, String xpathString) throws XPathExpressionException {
		return (Node) evaluate(node, xpathString, XPathConstants.NODE);
	}

	public Double eval2double(Node node, String xpathString) throws XPathExpressionException {
		return (Double) evaluate(node, xpathString, XPathConstants.NUMBER);
	}

	public int eval2int(Node node, String xpathString) throws XPathExpressionException {
		return (int) (double) eval2double(node, xpathString);
	}

	public IterableNodeList eval2list(Node node, String xpathString) throws XPathExpressionException {
		return new IterableNodeList((NodeList) evaluate(node, xpathString, XPathConstants.NODESET));
	}

	public String eval2string(Node node, String xpathString) throws XPathExpressionException {
		return (String) evaluate(node, xpathString, XPathConstants.STRING);
	}

	public Object evaluate(Node node, String xpathString, QName expectedObjectType) throws XPathExpressionException {
		return getXPath(xpathString).evaluate(node, expectedObjectType);
	}

	public String getAttribute(Node node, String name) {
		Node attrNode = node.getAttributes().getNamedItem(name);
		if (attrNode != null) {
			return attrNode.getNodeValue();
		}
		return null;
	}

	public Document getDocument(InputStream stream) throws SAXException, IOException {
		return documentBuilder.parse(stream);
	}

	public Document getDocument(String fileLocation) throws SAXException, IOException {
		return getDocument(new FileInputStream(fileLocation));
	}

	public XPathExpression getXPath(String xpathString) throws XPathExpressionException {
		if (!xpathExpressionsMap.containsKey(xpathString)) {
			xpathExpressionsMap.put(xpathString, xpath.compile(xpathString));
		}
		return xpathExpressionsMap.get(xpathString);
	}

}
