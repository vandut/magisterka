package hsoa_1.caller;

import hsoa_1.ServiceSoap;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(Activator.class);

	public void start(BundleContext bundleContext) throws Exception {
		logger.info("Activator start()");

		ServiceReference reference = bundleContext.getServiceReference(ServiceSoap.class.getName());
		ServiceSoap serviceSoap = (ServiceSoap) bundleContext.getService(reference);
		
		StatusServlet.setServiceSoap(serviceSoap);
		OpenServlet.setServiceSoap(serviceSoap);
		CloseServlet.setServiceSoap(serviceSoap);
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Activator stop()");
	}

}
