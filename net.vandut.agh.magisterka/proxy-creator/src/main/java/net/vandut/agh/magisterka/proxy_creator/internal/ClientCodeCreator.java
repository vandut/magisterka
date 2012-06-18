package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.IOException;

import net.vandut.agh.magisterka.proxy_creator.ConnectionUtils;
import net.vandut.agh.magisterka.proxy_creator.WSDLAnalyzer;
import net.vandut.agh.magisterka.proxy_creator.WSDLAnalyzer.Service;

import org.apache.log4j.Logger;

public class ClientCodeCreator {

	private static final Logger logger = Logger.getLogger(ClientCodeCreator.class);

	public static void createClientCode(WSDLAnalyzer analyzer, String rootClientDir, String filesDestination)
			throws IOException {
		logger.info("Copying and modifying client code");
		replaceAndSave(analyzer, "/java_files/EcfClient.java", filesDestination + "/EcfClient.java");
		replaceAndSave(analyzer, "/java_files/ServiceWatcher.java", filesDestination + "/ServiceWatcher.java");
		replaceAndSave(analyzer, "/java_files/ClientActivator.java", filesDestination + "/ClientActivator.java");
		replaceAndSave(analyzer, "/client_pom.xml", rootClientDir + "/pom.xml");
		replaceAndSave(analyzer, "/README.txt", rootClientDir + "/README.txt");
	}
	
	public static void replaceAndSave(WSDLAnalyzer analyzer, String sourceClasspathFile, String destinationFile) throws IOException {
		logger.info("destinationFile="+destinationFile);
		
		String contents = FileUtils.stringFromClasspathFile(sourceClasspathFile);
		
		// TODO: accept multiple services
		Service service = analyzer.getServiceList().get(0);

		contents = contents.replaceAll("%PACKAGE%", analyzer.getTargetPackage());
		contents = contents.replaceAll("%INTERFACE%", service.type);
		contents = contents.replaceAll("%IP_ADDRESS%", ConnectionUtils.getRegisteredIPAddress());
		contents = contents.replaceAll("%ECF_PORT%", String.valueOf(ConnectionUtils.getGeneratedECFPort()));
		
		logger.info("Saving file: " + destinationFile);
		FileUtils.writeFile(destinationFile, contents);
	}

}
