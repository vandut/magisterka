package net.vandut.agh.magisterka.servlet.tester;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;
import org.osgi.service.metatype.ObjectClassDefinition;
import org.osgi.service.packageadmin.PackageAdmin;

public class OSGiMetatype {

	private ServiceReference metatypeRef;
	private MetaTypeService mts;

	public MetaTypeService getService(BundleContext bundleContext) {
		if (mts == null) {
			metatypeRef = bundleContext.getServiceReference(MetaTypeService.class.getName());
			mts = (MetaTypeService) bundleContext.getService(metatypeRef);
		}
		return mts;
	}

	public void ungetService(BundleContext bundleContext) {
		bundleContext.ungetService(metatypeRef);
	}

	public MetaTypeInformation getInformation(BundleContext bundleContext, String bundleId) {
		ServiceReference packageAdminRef = bundleContext.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin pa = (PackageAdmin) bundleContext.getService(packageAdminRef);
		Bundle[] bundles = pa.getBundles(bundleId, null);

		return mts.getMetaTypeInformation(bundles[0]);
	}

	public static AttributeDefinition[] getAttributes(MetaTypeInformation information, String ocdPid) {
		ObjectClassDefinition ocd = information.getObjectClassDefinition("org.apache.felix.fileinstall", null);
		return ocd.getAttributeDefinitions(ObjectClassDefinition.REQUIRED);
	}

	public static String extractAttributeValue(AttributeDefinition[] attributes, String id) {
		for (int i = 0; true; i++) {
			if (attributes[i].getID().equals(id)) {
				return null; // attributes[i].getOptionValues();
			}
		}
	}

	public void test(BundleContext bc) {
		Bundle[] bundles = bc.getBundles();
		for (int i = 0; i < bundles.length; i++) {

			// Getting the org.osgi.service.metatype.MetaTypeInformation object
			// for all available bundles.
			MetaTypeInformation mti = mts.getMetaTypeInformation(bundles[i]);

			// Obtaining the available FPIDs and PIDs for each bundle
			String[] factorypids = mti.getFactoryPids();
			String[] pids = mti.getPids();

			// Retrieving the Object Class Definitions and Attribute Definitions
			// for
			// bundles with PIDs
			if (pids != null) {
				for (int pid = 0; pid < pids.length; pid++) {
					ObjectClassDefinition ocd = mti.getObjectClassDefinition(pids[pid], null);
					AttributeDefinition[] ads = ocd.getAttributeDefinitions(ObjectClassDefinition.ALL);

					// Printing the available Object Class Definitions and
					// Attribute Definitions to the system output
					for (int j = 0; j < ads.length; j++) {
						System.out.println("AD= " + ads[j].getName() + " OCD= " + ocd.getName());
					}
				}

				// Extracting ObjectClassDefinition and AttributeDefinition
				// objects for bundles
				// with FPIDs
			}
			if (factorypids != null) {
				for (int fpid = 0; fpid < factorypids.length; fpid++) {
					ObjectClassDefinition ocdfactory = mti.getObjectClassDefinition(factorypids[fpid], null);
					AttributeDefinition[] adsfactory = ocdfactory.getAttributeDefinitions(ObjectClassDefinition.ALL);

					// Printing all Object Class Definitions and Attribute
					// Definitions
					// for the available bundles to the system output
					for (int k = 0; k < adsfactory.length; k++) {
						System.out.println("AD= " + adsfactory[k].getName() + " OCD= " + ocdfactory.getName());
					}
				}
			}
		}
	}
}
