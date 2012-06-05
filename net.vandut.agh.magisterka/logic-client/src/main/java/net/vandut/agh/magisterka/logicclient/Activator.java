package net.vandut.agh.magisterka.logicclient;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {
	
	private EcfClient ecfClient;

	public void start(BundleContext context) throws Exception {
		ecfClient = new EcfClient(context);
		ecfClient.register(System.getProperty("net.vandut.ecf.zooip", "127.0.0.1"));
		
		Gui.start(context, ecfClient);
	}

	public void stop(BundleContext context) throws Exception {
		Gui.getInstance().close();
		ecfClient.unregister();
	}

}
