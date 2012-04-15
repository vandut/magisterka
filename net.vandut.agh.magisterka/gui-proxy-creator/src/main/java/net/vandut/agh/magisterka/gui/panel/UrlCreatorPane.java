package net.vandut.agh.magisterka.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JTextField;

import net.vandut.agh.magisterka.gui.GuiUtils;
import net.vandut.agh.magisterka.gui.thread.UrlProxyCreatorThread;

public class UrlCreatorPane extends CreatorPane {
	
	private static final long serialVersionUID = 1L;

	private JTextField wsdlUrlLocation;

	@Override
	protected void setUpOptionsSection() {
		wsdlUrlLocation = new JTextField();
		GuiUtils.addRow(this, "WSDL url:", wsdlUrlLocation);
	}

	@Override
	protected void setListeners() {
		super.setListeners();
		wsdlUrlLocation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(isWsdlLocationValid()) {
					setOperationEnabled(isServicemixLocationValid());
				}
			}
		});
	}

	@Override
	public boolean isWsdlLocationValid() {
		if(wsdlUrlLocation.getText().isEmpty()) {
			return false;
		}
		try {
			new URL(wsdlUrlLocation.getText());
			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}

	@Override
	public String getWsdlLocation() {
		return wsdlUrlLocation.getText();
	}

	@Override
	public Thread getWorkerThread() {
		return new UrlProxyCreatorThread(this, getWsdlLocation(), getOutputLocation());
	}

}
