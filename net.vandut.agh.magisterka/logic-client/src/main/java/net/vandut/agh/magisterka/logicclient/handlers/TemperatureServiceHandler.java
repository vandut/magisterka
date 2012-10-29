package net.vandut.agh.magisterka.logicclient.handlers;

import pyro.TemperatureService;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.vandut.agh.magisterka.logicclient.ServiceHandler;

public class TemperatureServiceHandler extends ServiceHandler<TemperatureService> {

	public TemperatureServiceHandler(String description, Class<TemperatureService> clazz,
			JLabel statusLabel, JButton... buttons) {
		super(description, clazz, statusLabel, buttons);
	}

	@Override
	public void onButtonPressed(JButton btn, int index) {
		final TemperatureService service = getService();
		switch (index) {
		case 0:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					Integer result = service.getTemp();
					displayInfoMessage("Door status: " + result);
				}
			}.start();
			break;
		}
	}

}
