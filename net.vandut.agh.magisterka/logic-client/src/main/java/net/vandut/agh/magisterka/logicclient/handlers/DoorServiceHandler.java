package net.vandut.agh.magisterka.logicclient.handlers;

import hsoa_2.ServiceSoap;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.vandut.agh.magisterka.logicclient.ServiceHandler;

public class DoorServiceHandler extends ServiceHandler<ServiceSoap> {

	public DoorServiceHandler(String description, Class<ServiceSoap> clazz,
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
					String result = service.doorStatus();
					displayInfoMessage("Door status: " + result);
				}
			}.start();
			break;
		case 1:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.doorOpen();
					displayInfoMessage("Door open action, result: " + result);
				}
			}.start();
			break;
		case 2:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.doorClose();
					displayInfoMessage("Door close action, result: " + result);
				}
			}.start();
			break;
		}
	}

}
