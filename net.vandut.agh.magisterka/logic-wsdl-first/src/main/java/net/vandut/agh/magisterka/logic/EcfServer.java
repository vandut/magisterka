package net.vandut.agh.magisterka.logic;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class EcfServer {

	private static final Logger logger = Logger.getLogger(EcfServer.class);

	private static final int DEFAULT_ECF_PORT = 3787;
	private static final String DEFAULT_CONTAINER_TYPE = "ecf.generic.server";
	private static final String DEFAULT_CONTAINER_ID = "ecftcp://%s:%d/server";

	private final BundleContext context;

	private Map<Class<?>, ServiceRegistration> serviceRegistrations = new HashMap<Class<?>, ServiceRegistration>();

	public EcfServer(BundleContext context) throws UnknownHostException {
		this.context = context;
	}

	public <T> void registerService(Class<T> interfaze, T service) throws UnknownHostException {
		String formatContainer = generateFormatContainer();
		Properties props = new Properties();
		props.put(IDistributionConstants.SERVICE_EXPORTED_INTERFACES,
				IDistributionConstants.SERVICE_EXPORTED_INTERFACES_WILDCARD);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONFIGS, DEFAULT_CONTAINER_TYPE);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONTAINER_FACTORY_ARGUMENTS, formatContainer);
		serviceRegistrations.put(interfaze, context.registerService(interfaze.getName(), service, props));
		logger.info(String.format("Service %s registered using %s", interfaze.getName(), formatContainer));
	}

	public <T> void unregisterService(Class<T> clazz) {
		if (serviceRegistrations.containsKey(clazz)) {
			serviceRegistrations.get(clazz).unregister();
			serviceRegistrations.remove(clazz);
			logger.info(String.format("Service %s unregistered", clazz.getName()));
		}
	}

	public void unregisterAll() {
		for (Entry<Class<?>, ServiceRegistration> entry : serviceRegistrations.entrySet()) {
			entry.getValue().unregister();
			logger.info(String.format("Service %s unregistered", entry.getKey().getName()));
		}
		serviceRegistrations.clear();
	}

	private String generateFormatContainer() throws UnknownHostException {
		return String.format(DEFAULT_CONTAINER_ID, getRegisteredIPAddress(), getRandomEcfPort());
	}

	private static String getRegisteredIPAddress() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static int getRandomEcfPort() {
		return DEFAULT_ECF_PORT + (int) (Math.random() * 1000);
	}

}
