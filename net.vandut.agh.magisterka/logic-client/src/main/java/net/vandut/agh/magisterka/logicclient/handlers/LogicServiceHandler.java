package net.vandut.agh.magisterka.logicclient.handlers;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.vandut.agh.magisterka.logic.service.LogicService;
import net.vandut.agh.magisterka.logicclient.ServiceHandler;

public class LogicServiceHandler extends ServiceHandler<LogicService> {

	public LogicServiceHandler(String description, Class<LogicService> clazz,
			JLabel statusLabel, JButton... buttons) {
		super(description, clazz, statusLabel, buttons);
	}

	@Override
	public void onButtonPressed(JButton btn, int index) {
		final LogicService service = getService();
		switch (index) {
		case 0:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					service.startLogic();
					displayInfoMessage("Logic service started");
				}
			}.start();
			break;
		case 1:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					service.stopLogic();
					displayInfoMessage("Logic service stopped");
				}
			}.start();
			break;
		}
	}

}
