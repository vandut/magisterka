package net.vandut.agh.magisterka.logic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import net.vandut.agh.magisterka.logic.service.LogicService;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator, LogicService {

	private static final int NO_DELAY = 0;
	private static final int ONE_SECOND = 1000;

	private static final Logger logger = Logger.getLogger(Activator.class);

	private BundleContext context;

	private Timer logicTimer = new Timer();

	private EcfServer ecfServer;

	private AtomicBoolean status = new AtomicBoolean(false);

	public void start(BundleContext context) throws Exception {
		this.context = context;
		ecfServer = new EcfServer(context);
		ecfServer.registerService(LogicService.class, this);
		startLogic();
		logger.info("Logic activator started");
	}

	public void stop(BundleContext context) throws Exception {
		ecfServer.unregisterAll();
		stopLogic();
		context = null;
		logger.info("Logic activator stopped");
	}

	public void startLogic() {
		logicTimer.scheduleAtFixedRate(new LogicTask(), NO_DELAY, ONE_SECOND);
		status.set(true);
	}

	public void stopLogic() {
		logicTimer.cancel();
		logicTimer = new Timer();
		status.set(false);
	}

	public boolean statusLogic() {
		return status.get();
	}

	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> clazz) {
		ServiceReference serviceReference = context.getServiceReference(clazz
				.getName());
		return (T) context.getService(serviceReference);
	}

	public class LogicTask extends TimerTask {
		@Override
		public void run() {
			try {
				hsoa_1.ServiceSoap temperatureService = getService(hsoa_1.ServiceSoap.class);
				hsoa_2.ServiceSoap doorService = getService(hsoa_2.ServiceSoap.class);

				logger.info("Checking temperature");
				float temperature = parseTemperature(temperatureService
						.getTemperature());

				if (temperature < 10.0f) {
					logger.info("Temperature too low, closing door");
					doorService.doorClose();
				} else if (temperature > 15.0f) {
					logger.info("Temperature too high, opening door");
					doorService.doorOpen();
				} else {
					logger.info("Temperature ok, no action required");
				}
			} catch (RuntimeException e) {
				status.set(false);
				logger.error("ERROR while executing scheduled task", e);
				throw e;
			}
		}

		private float parseTemperature(String temp) {
			try {
				temp = temp.trim();
				logger.info("Parsin temperature: " + temp);
				return Float.parseFloat(temp.split("\\ ")[0]);
			} catch (NumberFormatException e) {
				logger.error("Invalid temperature format", e);
				return -1;
			}
		}
	}

}
