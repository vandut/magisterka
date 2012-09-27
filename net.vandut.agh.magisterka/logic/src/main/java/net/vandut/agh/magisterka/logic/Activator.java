package net.vandut.agh.magisterka.logic;

import net.vandut.agh.magisterka.logic.service.Logic;
import net.vandut.agh.magisterka.logic.service.LogicImpl;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static final Logger logger = Logger.getLogger(Activator.class);

	private BundleContext context;

	private EcfServer ecfServer;
	
	private Logic logic;
	
	private static Activator _INSTANCE;

	public static Logic setService(Logic logic) throws Exception {
		if (_INSTANCE == null) {
			throw new IllegalStateException("Excpected Activator instance to be not null");
		}
		_INSTANCE.logic = logic;
		((LogicImpl)logic).setActivator(_INSTANCE);
		_INSTANCE.initLogic();
		return logic;
	}

	public void start(BundleContext context) throws Exception {
		_INSTANCE = this;
		this.context = context;
	}

	public void stop(BundleContext context) throws Exception {
		cleanupLogic();
		context = null;
		logger.info("Logic activator stopped");
	}
	
	private void initLogic() throws Exception {
		ecfServer = new EcfServer(context);
		ecfServer.registerService(Logic.class, logic);
//		logic.logicStart();
		logger.info("Logic activator started");
	}
	
	private void cleanupLogic() {
		ecfServer.unregisterAll();
		logic.logicStop();
	}

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> clazz) {
		ServiceReference serviceReference = context.getServiceReference(clazz
				.getName());
		return (T) context.getService(serviceReference);
	}

}
