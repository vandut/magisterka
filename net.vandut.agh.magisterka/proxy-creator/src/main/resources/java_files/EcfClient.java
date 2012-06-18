package %PACKAGE%.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.ecf.core.ContainerConnectException;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.ContainerFactory;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.IContainerFactory;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class EcfClient {

	private static final Logger logger = Logger.getLogger(EcfClient.class);

	private static final String DEFAULT_CONTAINER_TYPE = "ecf.generic.client";

	private final BundleContext context;

	private Map<Class<?>, ServiceTracker> serviceTrackerMap = new HashMap<Class<?>, ServiceTracker>();

	private IContainer zooContainer;

	public EcfClient(BundleContext bundleContext) {
		this.context = bundleContext;
	}

	public void register(String zoodiscoveryServerIp) throws ContainerCreateException, ContainerConnectException {
		IContainerFactory containerFactory = ContainerFactory.getDefault();
		containerFactory.createContainer(DEFAULT_CONTAINER_TYPE);

		zooContainer = containerFactory.createContainer("ecf.discovery.zoodiscovery");

		if (zooContainer.getConnectedID() == null) {
			ID target = zooContainer.getConnectNamespace().createInstance(
					new String[] { "zoodiscovery.flavor.centralized=" + zoodiscoveryServerIp });
			zooContainer.connect(target, null);
			logger.info("ZooDiscovery connected");
		} else {
			logger.warn("ZooDiscovery already connected: " + zooContainer.getConnectedID().toExternalForm());
		}
	}

	public void unregister() {
		for (Entry<Class<?>, ServiceTracker> entry : serviceTrackerMap.entrySet()) {
			logger.info(String.format("ServiceTracker for class %s unregistered", entry.getKey().getName()));
			entry.getValue().close();
		}
		serviceTrackerMap.clear();
	}

	public <T> void addServiceTracker(Class<T> clazz, ServiceTrackerCustomizer serviceTracker)
			throws InvalidSyntaxException {
		if (serviceTrackerMap.containsKey(clazz)) {
			removeServiceTracker(clazz);
		}
		ServiceTracker proxyServiceTracker = new ServiceTracker(context, createRemoteFilter(clazz), serviceTracker);
		proxyServiceTracker.open();
		serviceTrackerMap.put(clazz, proxyServiceTracker);
		logger.info(String.format("ServiceTracker for class %s registered", clazz.getName()));
	}

	public <T> void removeServiceTracker(Class<T> clazz) {
		logger.info(String.format("ServiceTracker for class %s unregistered", clazz.getName()));
		serviceTrackerMap.get(clazz).close();
		serviceTrackerMap.remove(clazz);
	}

	private <T> Filter createRemoteFilter(Class<T> clazz) throws InvalidSyntaxException {
		return context.createFilter("(&(" + org.osgi.framework.Constants.OBJECTCLASS + "=" + clazz.getName() + ")("
				+ IDistributionConstants.SERVICE_IMPORTED + "=*))");
	}

	public Object getService(ServiceReference reference) {
		return context.getService(reference);
	}
	
	public BundleContext getBundleContext() {
		return context;
	}

}
