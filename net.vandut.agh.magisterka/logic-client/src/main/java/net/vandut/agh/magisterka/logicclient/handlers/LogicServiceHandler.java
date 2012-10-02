package net.vandut.agh.magisterka.logicclient.handlers;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.vandut.agh.magisterka.logic.service.Logic;
import net.vandut.agh.magisterka.logicclient.ServiceHandler;

public class LogicServiceHandler extends ServiceHandler<Logic> {

	public LogicServiceHandler(String description, Class<Logic> clazz,
			JLabel statusLabel, JButton... buttons) {
		super(description, clazz, statusLabel, buttons);
	}

	@Override
	public void onButtonPressed(JButton btn, int index) {
		final Logic service = getService();
		switch (index) {
		case 0:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.logicStatus();
					displayInfoMessage("Logic status: " + result);
				}
			}.start();
			break;
		case 1:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.logicStart();
					displayInfoMessage("Logic service started, result: " + result);
				}
			}.start();
			break;
		case 2:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.logicStop();
					displayInfoMessage("Logic service stopped, result: " + result);
				}
			}.start();
			break;
		}
	}

}
