package net.vandut.agh.magisterka.logicclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public abstract class ServiceHandler<T extends Object> extends
		ServiceWatcher<T> implements ActionListener {

	private final static String STATUS_OFFLINE = "offline";
	private final static String STATUS_ONLINE = "online";

	private final String description;
	private final Class<T> clazz;
	private final JLabel statusLabel;

	private JButton[] buttons;

	public ServiceHandler(String description, Class<T> clazz,
			JLabel statusLabel, JButton... buttons) {
		this.description = description;
		this.clazz = clazz;
		this.statusLabel = statusLabel;
		this.buttons = buttons;

		setEnabledButtons(false);
		setLabelStatus(STATUS_OFFLINE);
		setButtonListeners();
	}

	public void setLabelStatus(String status) {
		statusLabel.setText(description + ": " + status);
	}

	public void setEnabledButtons(boolean enabled) {
		for (JButton b : buttons) {
			b.setEnabled(enabled);
		}
	}

	private void setButtonListeners() {
		for (JButton b : buttons) {
			b.addActionListener(this);
		}
	}

	public JButton getButton(int index) {
		return buttons[index];
	}

	public void register(EcfClient ecfClient) throws InvalidSyntaxException {
		setContext(ecfClient.getBundleContext());
		ecfClient.addServiceTracker(clazz, this);
	}

	public void unregister(EcfClient ecfClient) {
		ecfClient.removeServiceTracker(clazz);
	}

	@Override
	public void onServiceFound(ServiceReference reference, T service) {
		setLabelStatus(STATUS_ONLINE);
		setEnabledButtons(true);
	}

	@Override
	public void onServiceLost(ServiceReference reference, T service) {
		setLabelStatus(STATUS_OFFLINE);
		setEnabledButtons(false);
	}

	private int indexOfButtons(JButton b) {
		for (int i = 0; i < buttons.length; i++) {
			if (b == buttons[i]) {
				return i;
			}
		}
		return -1;
	}

	public void actionPerformed(ActionEvent action) {
		if (!(action.getSource() instanceof JButton)) {
			throw new IllegalArgumentException("Expected JButton event source");
		}
		JButton btn = (JButton) action.getSource();
		int index = indexOfButtons(btn);
		if (index < 0) {
			throw new IllegalArgumentException("Button not on the service list");
		}
		try {
			onButtonPressed(btn, index);
		} catch (Exception e) {
			displayErrorMessage(e);
		}
	}

	public abstract void onButtonPressed(JButton btn, int index);

	public void displayGeneralMessage(Object message, String title,
			int messageType) {
		JOptionPane.showMessageDialog(null, message, title, messageType);
	}

	public void displayErrorMessage(Exception e) {
		JOptionPane.showMessageDialog(null,
				"Exception occured, details: " + e.getMessage(), "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public void displayInfoMessage(String info) {
		JOptionPane.showMessageDialog(null, info, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public abstract class ServiceActionThread extends Thread {
		@Override
		public void run() {
			try {
				setEnabledButtons(false);
				performAction();
			} catch (Exception e) {
				displayErrorMessage(e);
			} finally {
				setEnabledButtons(true);
			}
		}

		public abstract void performAction();
	}

}
