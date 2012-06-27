package %PACKAGE%.internal;

import %PACKAGE%.%INTERFACE%;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ClientActivator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(ClientActivator.class);
	
	private EcfClient ecfClient;
	
	// !!!!!!!!!! CHANGE ME !!!!!!!!!! //
	// Optional. Change only if proxy bundle is on host with other ip address
	private String ipAddress = "%IP_ADDRESS%";
	// !!!!!!!!!! CHANGE ME !!!!!!!!!! //
	
	private ServiceWatcher<%INTERFACE%> serviceWatcher = new ServiceWatcher<%INTERFACE%>() {
		@Override
		public void onServiceFound(ServiceReference reference, %INTERFACE% service) {
			logger.info("Service Found");
			// insert your code here
			// WSDL service methods are in %INTERFACE% object
		}
		@Override
		public void onServiceLost(ServiceReference reference, %INTERFACE% service) {
			logger.info("Service Lost");
			// insert your code here
			// WSDL service methods are in %INTERFACE% object
		}
	};

	public void start(BundleContext bundleContext) throws Exception {
		ecfClient = new EcfClient(bundleContext);
		ecfClient.register(ipAddress);
		serviceWatcher.setContext(bundleContext);
		ecfClient.addServiceTracker(%INTERFACE%.class, serviceWatcher);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		ecfClient.unregister();
	}
}
