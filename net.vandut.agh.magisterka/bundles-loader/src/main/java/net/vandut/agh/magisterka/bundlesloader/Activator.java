package net.vandut.agh.magisterka.bundlesloader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(Activator.class);

	private static final String DEPLOY_DIR = "deploy";
	private static final String JARS_DIR = "jars_to_deploy";

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Starting late JARs deployment");
		moveBundlesToDeployDirectory(bundleContext);
		System.out.println("All JARs deployed");
	}

	public void stop(BundleContext bundleContext) throws Exception {
	}

	private void moveBundlesToDeployDirectory(BundleContext bundleContext) {
		File jarsDir = new File(bundleContext.getProperty("karaf.base") + "/" + JARS_DIR);
		File deployDir = new File(bundleContext.getProperty("karaf.base") + "/" + DEPLOY_DIR);
		
		if(!jarsDir.exists()) {
			logger.warn(String.format("Directory % doesn't exists, creating", jarsDir.getPath()));
			jarsDir.mkdir();
		}
		
		Iterator<File> files = FileUtils.iterateFiles(jarsDir, new String[] { "jar" }, false);
		
		File file;
		while(files.hasNext()) {
			file = files.next();
			System.out.println(String.format("Moving %s to deploy directory", file.getName()));
			logger.info(String.format("Moving %s to deploy directory", file.getName()));
			try {
				FileUtils.moveFileToDirectory(file, deployDir, false);
			} catch (IOException e) {
				logger.error(String.format("Failed to move %s", file.getName()), e);
			}
		}
	}

}
