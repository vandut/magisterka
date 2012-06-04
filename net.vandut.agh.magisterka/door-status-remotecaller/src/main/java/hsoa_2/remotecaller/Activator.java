package hsoa_2.remotecaller;

import hsoa_2.ServiceSoap;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(Activator.class);
	
	private EcfClient ecfClient;
	
	private ServiceWatcher<ServiceSoap> serviceWatcher = new ServiceWatcher<ServiceSoap>() {
		@Override
		public void onServiceFound(ServiceReference reference, ServiceSoap service) {
			String status;
			logger.info("Calling serviceSoap.doorStatus()");
			logger.info("---> serviceSoap.doorStatus()");
			status = service.doorStatus();
			logger.info("<--- Returned data: status=" + status);
			logger.info("---> serviceSoap.doorOpen()");
			status = service.doorOpen();
			logger.info("<--- Returned data: status=" + status);
			logger.info("---> serviceSoap.doorStatus()");
			status = service.doorStatus();
			logger.info("<--- Returned data: status=" + status);
			logger.info("---> serviceSoap.doorClose()");
			status = service.doorClose();
			logger.info("<--- Returned data: status=" + status);
			logger.info("---> serviceSoap.doorStatus()");
			status = service.doorStatus();
			logger.info("<--- Returned data: status=" + status);
		}
		@Override
		public void onServiceLost(ServiceReference reference, ServiceSoap service) {
		}
	};

	public void start(BundleContext bundleContext) throws Exception {
		ecfClient = new EcfClient(bundleContext);
		ecfClient.register(System.getProperty("net.vandut.ecf.zooip", "127.0.0.1"));
		serviceWatcher.setContext(bundleContext);
		ecfClient.addServiceTracker(ServiceSoap.class, serviceWatcher);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		ecfClient.unregister();
	}
}
