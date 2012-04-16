package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.vandut.agh.magisterka.proxy_creator.WSDLAnalyzer;
import net.vandut.agh.magisterka.proxy_creator.WSDLAnalyzer.Service;

import org.apache.log4j.Logger;

public class CxfConfCreator {

	private static final Logger logger = Logger
			.getLogger(CxfConfCreator.class);
	
	public static void configureFile(WSDLAnalyzer analyzer, String wsdlFilePath, String outputPath) throws Exception {
		String header = FileUtils.stringFromClasspathFile("/beans_parts/header.txt");
		String footer = FileUtils.stringFromClasspathFile("/beans_parts/footer.txt");
		
		String consumer = FileUtils.stringFromClasspathFile("/beans_parts/consumer.txt");
		String proxy = FileUtils.stringFromClasspathFile("/beans_parts/proxy.txt");
		String provider = FileUtils.stringFromClasspathFile("/beans_parts/provider.txt");
		String osgi_service = FileUtils.stringFromClasspathFile("/beans_parts/osgi_service.txt");
		
		String document = "";
		document += String.format(header, analyzer.getTargetNamespace());
		
		List<Service> serviceList = analyzer.getServiceList();
		for(int serviceIdx = 0; serviceIdx < serviceList.size(); serviceIdx++) {
			Service service = serviceList.get(serviceIdx);
			String wsdlFileName = (new File(wsdlFilePath)).getName();
			String proxyBeanName = "proxy"+serviceIdx;
			String localAddress = convertHostToLocalhost(service.httpAddress);
			String type = analyzer.getTargetPackage()+"."+service.type;
			
			String consumerEndpoint = service.name + "Consumer" + serviceIdx;
			String proxyEndpoint = service.name + "Proxy" + serviceIdx;
			String providerEndpoint = service.name + "Provider" + serviceIdx;
			
			document += String.format(consumer, wsdlFileName, localAddress, consumerEndpoint, service.name, proxyEndpoint, service.name);
			document += String.format(proxy, proxyBeanName, service.name, providerEndpoint, type, proxyEndpoint, service.name, proxyBeanName);
			document += String.format(provider, wsdlFileName, service.httpAddress, providerEndpoint, service.name);
			if(serviceIdx == 0) {
				// TODO: accept multiple services
				document += String.format(osgi_service, proxyBeanName, type, analyzer.getTargetPackage(), proxyBeanName);
			}
		}
		
		document += footer;
		
		logger.info("Created CXF configuration file");
		FileUtils.writeFile(outputPath, document);
	}
	
	public static String convertHostToLocalhost(String address) throws MalformedURLException {
		URL url = new URL(address);
		if (url.getPort() > 0) {
			return url.getProtocol() + "://localhost:" + url.getPort() + url.getFile();
		} else {
			return url.getProtocol() + "://localhost" + url.getFile();
		}
	}

}
