package net.vandut.agh.magisterka.logicclient.handlers;

import cam.SmartCameraPortType;

import javax.swing.JButton;
import javax.swing.JLabel;

import net.vandut.agh.magisterka.logicclient.ServiceHandler;

public class CamServiceHandler extends ServiceHandler<SmartCameraPortType> {

	public CamServiceHandler(String description, Class<SmartCameraPortType> clazz,
			JLabel statusLabel, JButton... buttons) {
		super(description, clazz, statusLabel, buttons);
	}

	@Override
	public void onButtonPressed(JButton btn, int index) {
		final SmartCameraPortType service = getService();
		switch (index) {
		case 0:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.startClassifier();
					displayInfoMessage("Start Classifier: " + result);
				}
			}.start();
			break;
		case 1:
			new ServiceActionThread() {
				@Override
				public void performAction() {
					String result = service.getLast();
					displayInfoMessage("Get Last: " + result);
				}
			}.start();
			break;
		}
	}

}
