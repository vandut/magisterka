package net.vandut.agh.magisterka.logic;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jws.WebService;

import net.vandut.agh.magisterka.logic.service.LogicService;

import org.apache.log4j.Logger;

import cam.SmartCameraPortType;

@WebService(serviceName = "LogicServiceClient",
            targetNamespace = "http://service.logic.magisterka.agh.vandut.net/",
            endpointInterface = "net.vandut.agh.magisterka.logic.service.LogicService"
            )
public class Logic implements LogicService {

	private static final Logger logger = Logger.getLogger(Logic.class);

	private static final int NO_DELAY = 0;
	private static final int ONE_SECOND = 1000;
	
	private Activator activator;

	private Timer logicTimer = new Timer();

	private AtomicBoolean status = new AtomicBoolean(false);
	
	public Logic(Activator activator) {
		this.activator = activator;
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

	public class LogicTask extends TimerTask {
		@Override
		public void run() {
			try {
				hsoa_1.ServiceSoap temperatureService = activator.getService(hsoa_1.ServiceSoap.class);
				hsoa_2.ServiceSoap doorService = activator.getService(hsoa_2.ServiceSoap.class);
				hsoa_3.ServiceSoap powerSwitchService = activator.getService(hsoa_3.ServiceSoap.class);
				SmartCameraPortType camService = activator.getService(SmartCameraPortType.class);
				
				// powerSwitchService not yet used, but package is in OSGi manifest file

				logger.info("Checking temperature");
				float temperature = parseTemperature(temperatureService
						.getTemperature());

				/*if (temperature < 10.0f) {
					logger.info("Temperature too low, closing door");
					doorService.doorClose();
				} else*/
				if (temperature > 25.0f) {
					logger.info("Temperature too high, opening door");
					doorService.doorOpen();
				} else {
					logger.info("Temperature ok, no action required");
				}
				
				logger.info("Checking camera");
				String classifier = camService.startClassifier();

				
				if ("s1".equalsIgnoreCase(classifier)) {
					logger.info("Camera classifier accepted, opening door");
					doorService.doorOpen();
				} else {
					logger.info("Invalid camera classifier");
				}
				
				
			} catch (RuntimeException e) {
//				status.set(false);
				logger.error("ERROR while executing scheduled task", e);
//				throw e;
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
