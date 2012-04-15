package net.vandut.agh.magisterka.proxy_creator.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import net.vandut.agh.magisterka.proxy_creator.WSDLAnalyzer;

import org.apache.log4j.Logger;

public class ManifestMfCreator {

	private static final Logger logger = Logger
			.getLogger(ManifestMfCreator.class);

	private static String stringFromClasspathFile(String path)
			throws IOException {
		InputStream stream = FileUtils.getInputStreamFromClasspath(path);
		return FileUtils.readFile(stream);
	}

	public static void configureFile(WSDLAnalyzer analyzer, String outputPath)
			throws Exception {
		boolean samePackagesFlag = analyzer.getTargetPackage().equals(analyzer.getTypesPackage());
		
		String template;
		if(samePackagesFlag) {
			template = stringFromClasspathFile("/META-INF/ManifestMfTemplate1.txt");
		} else {
			template = stringFromClasspathFile("/META-INF/ManifestMfTemplate2.txt");
		}

		String pckg = analyzer.getTargetPackage();
		String typesPckg = analyzer.getTypesPackage();
		String namespace = analyzer.getTargetNamespace();
		String javaVersion = System.getProperty("java.version");
		String lastModified = Long.toString(Calendar.getInstance()
				.getTimeInMillis());

		String symbolicName = analyzer.getServiceList().get(0).name + "-Proxy";
//		symbolicName += "-" + UUID.randomUUID();

		String document;
		if (samePackagesFlag) {
			document = String.format(template, pckg, namespace,
					javaVersion, lastModified, namespace, pckg, symbolicName);
		} else {
			document = String.format(template, pckg, typesPckg, namespace,
					javaVersion, lastModified, namespace, pckg, typesPckg, symbolicName);
		}

		logger.info("Created MANIFEST.MF configuration file");
		FileUtils.writeFile(outputPath, document);
	}

}
