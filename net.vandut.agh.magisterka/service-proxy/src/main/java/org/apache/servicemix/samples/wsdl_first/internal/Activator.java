package org.apache.servicemix.samples.wsdl_first.internal;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.apache.servicemix.samples.wsdl_first.Person;
import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator, IDistributionConstants {

	private static final Logger logger = Logger.getLogger(Activator.class);

	private static Person proxy;
	private static Activator _INSTANCE;
	
	private EcfServer ecfServer;

	@Override
	public void start(BundleContext context) throws Exception {
		_INSTANCE = this;
		ecfServer = new EcfServer(context);
	}

	public static Object setProxy(Person proxy) throws UnknownHostException {
		Activator.proxy = proxy;
		logger.info("Activator.setProxy(): proxy=" + Activator.proxy);
		if (_INSTANCE == null) {
			throw new IllegalStateException("Excpected Activator instance to be not null");
		}
		_INSTANCE.ecfServer.registerService(Person.class, proxy);
		return proxy;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		ecfServer.unregisterAll();
	}

}
