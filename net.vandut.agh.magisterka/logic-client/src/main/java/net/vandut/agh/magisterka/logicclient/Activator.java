package net.vandut.agh.magisterka.logicclient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	public void start(BundleContext context) throws Exception {
		Gui.start(context);
	}

	public void stop(BundleContext context) throws Exception {
		Gui.getInstance().close();
	}

}
