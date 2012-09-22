package net.vandut.agh.magisterka.logic;

import net.vandut.agh.magisterka.logic.service.LogicService;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(Activator.class);

	private BundleContext context;

	private EcfServer ecfServer;
	
	private Logic logic;
	
	public Activator() {
		logic = new Logic(this);
	}

	public void start(BundleContext context) throws Exception {
		this.context = context;
		ecfServer = new EcfServer(context);
		ecfServer.registerService(LogicService.class, logic);
		logic.startLogic();
		logger.info("Logic activator started");
	}

	public void stop(BundleContext context) throws Exception {
		ecfServer.unregisterAll();
		logic.stopLogic();
		context = null;
		logger.info("Logic activator stopped");
	}

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> clazz) {
		ServiceReference serviceReference = context.getServiceReference(clazz
				.getName());
		return (T) context.getService(serviceReference);
	}

}
