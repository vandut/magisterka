package net.vandut.agh.magisterka.logicclient.handlers;

import hsoa_3.ServiceSoap;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.vandut.agh.magisterka.logicclient.ServiceHandler;

public class PowerSwitchServiceHandler extends ServiceHandler<ServiceSoap> {

	public PowerSwitchServiceHandler(String description, Class<ServiceSoap> clazz,
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
					String result = service.switchOn1();
					displayInfoMessage("Switch On 1: " + result);
				}
			}.start();
			break;
		}
	}

}
