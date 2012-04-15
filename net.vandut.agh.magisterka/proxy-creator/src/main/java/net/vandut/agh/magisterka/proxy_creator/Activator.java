package net.vandut.agh.magisterka.proxy_creator;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger
			.getLogger(Activator.class);

	public void start(BundleContext bundleContext) throws Exception {
		logger.info("ProxyCreator Bundle STARTED");
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("ProxyCreator Bundle STOPPED");
	}

}
