package net.vandut.agh.magisterka.logicclient.handlers;

import hsoa_1.ServiceSoap;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.vandut.agh.magisterka.logicclient.ServiceHandler;

public class SensorsServiceHandler extends ServiceHandler<ServiceSoap> {

	public SensorsServiceHandler(String description, Class<ServiceSoap> clazz,
			JLabel statusLabel, JButton... buttons) {
		super(description, clazz, statusLabel, buttons);
	}

	@Override
	public void onButtonPressed(JButton btn, int index) {
		final ServiceSoap service = getService();
		switch (index) {
		case 0:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.getTemperature();
					displayInfoMessage("Temperature: " + result);
				}
			}.start();
			break;
		case 1:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.getHumidity();
					displayInfoMessage("Humidity: " + result);
				}
			}.start();
			break;
		case 2:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.getPressure();
					displayInfoMessage("Pressure: " + result);
				}
			}.start();
			break;
		}
	}

}
