package net.vandut.agh.magisterka.servlet.tester;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;
import org.osgi.service.metatype.ObjectClassDefinition;
import org.osgi.service.packageadmin.PackageAdmin;

public class TesterActivator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(TesterActivator.class);
	
//	private OSGiMetatype metatype;

	public void start(BundleContext bundleContext) throws Exception {
		logger.info("TesterActivator.start()");
//		metatype = new OSGiMetatype();
//		metatype.getService(bundleContext);

		test(bundleContext, "karaf.base");
		test(bundleContext, "karaf.shell.init.script");
		test(bundleContext, "felix.fileinstall.dir");
		test(bundleContext, "felix.fileinstall.filename");
		test(bundleContext, "felix.fileinstall.dir");
		
//		metatype.test(bundleContext);
//		setData(bundleContext);
	}
	
	void test(BundleContext bundleContext, String t) {
		System.out.println(t+"="+bundleContext.getProperty(t));
	}

	public void stop(BundleContext context) throws Exception {
//		metatype.ungetService(context);
		logger.info("TesterActivator.stop()");
	}

	private void setData(BundleContext bundleContext) {
//		MetaTypeInformation information = OSGiMetatype.getInformation(bundleContext, "org.apache.felix.fileinstall");
//		AttributeDefinition[] attributes = OSGiMetatype.getAttributes(information, "org.apache.felix.fileinstall");
	}

}
