package net.vandut.agh.magisterka.servlet.caller;

import org.apache.log4j.Logger;
import org.apache.servicemix.samples.wsdl_first.Person;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(Activator.class);

	public void start(BundleContext bundleContext) throws Exception {
		logger.info("Activator start()");

		ServiceReference reference = bundleContext.getServiceReference(Person.class.getName());
		Person person = (Person) bundleContext.getService(reference);
		ProxyCaller.setPerson(person);
	}

	public void stop(BundleContext context) throws Exception {
		logger.info("Activator stop()");
	}

}
