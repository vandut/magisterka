package net.vandut.agh.magisterka.servlet.creator;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger
			.getLogger(Activator.class);
	
	private static String karafBase;

	public void start(BundleContext bundleContext) throws Exception {
		logger.info("Servlet-Proxy-Creator Bundle STARTED");
		karafBase = bundleContext.getProperty("karaf.base");
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Servlet-Proxy-Creator Bundle STOPPED");
	}

	public static String getKarafBase() {
		return karafBase;
	}

}
