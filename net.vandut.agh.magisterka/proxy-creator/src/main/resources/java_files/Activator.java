package %PACKAGE%.internal;

import java.util.Properties;

import org.apache.log4j.Logger;
import %PACKAGE%.%INTERFACE%;
import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator, IDistributionConstants {

	private static final Logger logger = Logger.getLogger(Activator.class);

	private static final String DEFAULT_CONTAINER_TYPE = "ecf.generic.server";
	private static final String DEFAULT_CONTAINER_ID = "ecftcp://localhost:3787/server";

	private static %INTERFACE% proxy;
	private static Activator _INSTANCE;

	private BundleContext context;
	private ServiceRegistration serviceRegistration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_INSTANCE = this;
		context = bundleContext;
	}

	public static Object setProxy(%INTERFACE% proxy) {
		Activator.proxy = proxy;
		logger.info("Activator.setProxy(): proxy=" + Activator.proxy);
		if (_INSTANCE == null) {
			throw new IllegalStateException("Excpected Activator instance to be not null");
		}
		_INSTANCE.registerRemoteService();
		return proxy;
	}

	private void registerRemoteService() {
		Properties props = new Properties();
		props.put(IDistributionConstants.SERVICE_EXPORTED_INTERFACES, IDistributionConstants.SERVICE_EXPORTED_INTERFACES_WILDCARD);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONFIGS, DEFAULT_CONTAINER_TYPE);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONTAINER_FACTORY_ARGUMENTS, DEFAULT_CONTAINER_ID);
		serviceRegistration = context.registerService(%INTERFACE%.class.getName(), proxy, props);
		logger.info("Host: Proxy Remote Service Registered");
	}

	private void unregisterRemoteService() {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
			serviceRegistration = null;
			logger.info("Host: Proxy Remote Service Unregistered");
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		unregisterRemoteService();
		context = null;
	}

}
