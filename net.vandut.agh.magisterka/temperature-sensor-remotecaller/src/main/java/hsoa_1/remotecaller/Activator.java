package hsoa_1.remotecaller;

import hsoa_1.ServiceSoap;

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
			logger.info("Calling Temperature Sensor Service");
			logger.info("---> serviceSoap.getTemperature()=" + service.getTemperature());
			logger.info("---> serviceSoap.getHumidity()=" + service.getHumidity());
			logger.info("---> serviceSoap.getPressure()=" + service.getPressure());
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
