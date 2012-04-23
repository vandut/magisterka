package hsoa_1.remotecaller;

import hsoa_1.ServiceSoap;

import org.apache.log4j.Logger;
import org.eclipse.ecf.core.ContainerConnectException;
import org.eclipse.ecf.core.ContainerCreateException;
import org.eclipse.ecf.core.IContainer;
import org.eclipse.ecf.core.IContainerFactory;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

	private static final Logger logger = Logger.getLogger(Activator.class);

	private static final String DEFAULT_CONTAINER_TYPE = "ecf.generic.client";

	private BundleContext context;

	private ServiceTracker containerFactoryServiceTracker;
	private ServiceTracker proxyServiceTracker;
	
	private IContainer d0;
	private IContainer z0;

	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
		register();
	}

	private void register() throws ContainerCreateException, InvalidSyntaxException, ContainerConnectException {
		IContainerFactory containerFactory = getContainerFactory();
		d0 = containerFactory.createContainer(DEFAULT_CONTAINER_TYPE);

		// ////////////////////////////////////////////////////////////////// //
		z0 = containerFactory.createContainer("ecf.discovery.zoodiscovery");
		ID target = z0.getConnectNamespace().createInstance(
				new String[] { "zoodiscovery.flavor.centralized=192.168.1.7" });
		z0.connect(target, null);
		// ////////////////////////////////////////////////////////////////// //

		proxyServiceTracker = new ServiceTracker(context, createRemoteFilter(), this);
		proxyServiceTracker.open();
		logger.info("proxyServiceTracker opened");
	}

	private IContainerFactory getContainerFactory() {
		if (containerFactoryServiceTracker == null) {
			containerFactoryServiceTracker = new ServiceTracker(context, IContainerFactory.class.getName(), null);
			containerFactoryServiceTracker.open();
			logger.info("containerFactoryServiceTracker opened");
		}
		return (IContainerFactory) containerFactoryServiceTracker.getService();
	}

	private Filter createRemoteFilter() throws InvalidSyntaxException {
		return context.createFilter("(&(" + org.osgi.framework.Constants.OBJECTCLASS + "=" + ServiceSoap.class.getName()
				+ ")(" + IDistributionConstants.SERVICE_IMPORTED + "=*))");
	}

	private void unregister() {
		if (proxyServiceTracker != null) {
			proxyServiceTracker.close();
			proxyServiceTracker = null;
			logger.info("proxyServiceTracker closed");
		}
		z0.disconnect();
		d0.disconnect();
		if (containerFactoryServiceTracker != null) {
			containerFactoryServiceTracker.close();
			containerFactoryServiceTracker = null;
			logger.info("containerFactoryServiceTracker closed");
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		unregister();
		context = null;
	}

	public Object addingService(ServiceReference reference) {
		logger.info("ServiceSoap service registered");
		ServiceSoap proxy = (ServiceSoap) context.getService(reference);
		try {
			logger.info("Taking action");
			action(proxy);
		} catch (Exception e) {
			logger.error(e);
		}
		return proxy;
	}

	private void action(ServiceSoap serviceSoap) throws Exception {
		String status;
		logger.info("Calling serviceSoap.doorStatus()");
		logger.info("---> serviceSoap.doorStatus()");
		status = serviceSoap.doorStatus();
		logger.info("<--- Returned data: status=" + status);
		logger.info("---> serviceSoap.doorOpen()");
		status = serviceSoap.doorOpen();
		logger.info("<--- Returned data: status=" + status);
		logger.info("---> serviceSoap.doorStatus()");
		status = serviceSoap.doorStatus();
		logger.info("<--- Returned data: status=" + status);
		logger.info("---> serviceSoap.doorClose()");
		status = serviceSoap.doorClose();
		logger.info("<--- Returned data: status=" + status);
		logger.info("---> serviceSoap.doorStatus()");
		status = serviceSoap.doorStatus();
		logger.info("<--- Returned data: status=" + status);
	}

	public void modifiedService(ServiceReference reference, Object service) {
	}

	public void removedService(ServiceReference reference, Object service) {
		logger.info("ServiceSoap service unregistered");
	}
}
