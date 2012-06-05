package net.vandut.agh.magisterka.logicclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class ServiceHandler<T extends Object> extends ServiceWatcher<T> implements ActionListener {
	
	private final static String STATUS_OFFLINE = "offline";
	private final static String STATUS_ONLINE = "online";

	private final String description;
	private final Class<T> clazz;
	private final JLabel statusLabel;
	
	private JButton[] buttons;

	public ServiceHandler(String description, Class<T> clazz, JLabel statusLabel, JButton... buttons) {
		this.description = description;
		this.clazz = clazz;
		this.statusLabel = statusLabel;
		this.buttons = buttons;
		
		setEnabledButtons(false);
		setLabelStatus(STATUS_OFFLINE);
		setButtonListeners();
	}

	private void setLabelStatus(String status) {
		statusLabel.setText(description + ": " + status);
	}
	
	private void setEnabledButtons(boolean enabled) {
		for(JButton b : buttons) {
			b.setEnabled(enabled);
		}
	}
	
	private void setButtonListeners() {
		for(JButton b : buttons) {
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
		for(int i = 0; i < buttons.length; i++) {
			if(b == buttons[i]) {
				return i;
			}
		}
		return -1;
	}

	public void actionPerformed(ActionEvent e) {
		int index = indexOfButtons((JButton) e.getSource());
		if(index < 0) {
			return;
		}
		System.out.println("--------------------------------------------------------------");
		JOptionPane.showMessageDialog(null, "index="+index, "info", JOptionPane.ERROR_MESSAGE);
	}
	
	

}
