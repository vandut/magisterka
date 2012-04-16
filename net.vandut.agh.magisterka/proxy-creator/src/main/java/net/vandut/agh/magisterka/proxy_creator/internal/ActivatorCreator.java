package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.IOException;

import net.vandut.agh.magisterka.proxy_creator.WSDLAnalyzer;
import net.vandut.agh.magisterka.proxy_creator.WSDLAnalyzer.Service;

import org.apache.log4j.Logger;

public class ActivatorCreator {

	private static final Logger logger = Logger.getLogger(ActivatorCreator.class);

	public static void createActivator(WSDLAnalyzer analyzer, String activatorFileLocation, String sourceLocation)
			throws IOException {
		String activatorContents = FileUtils.stringFromClasspathFile(activatorFileLocation);

		// TODO: accept multiple services
		Service service = analyzer.getServiceList().get(0);

		activatorContents = activatorContents.replaceAll("%PACKAGE%", analyzer.getTargetPackage());
		activatorContents = activatorContents.replaceAll("%INTERFACE%", service.type);

		String outputPath = sourceLocation + "/" + analyzer.getTargetPackage().replace('.', '/') + "/internal";
		String outputFile = outputPath + "/Activator.java";

		logger.info("Created Activator Java class");
		logger.debug("Output file: " + outputFile);
		FileUtils.createDirIfNotExists(outputPath);
		FileUtils.writeFile(outputFile, activatorContents);
	}

}
