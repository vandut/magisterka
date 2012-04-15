package net.vandut.agh.magisterka.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import net.vandut.agh.magisterka.gui.GuiUtils;
import net.vandut.agh.magisterka.gui.thread.DiskProxyCreatorThread;

public class DiskCreatorPane extends CreatorPane {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField wsdlFileLocation;
	private JButton wsdlFileBtn;

	@Override
	protected void setUpOptionsSection() {
		wsdlFileLocation = new JTextField();
		wsdlFileBtn = GuiUtils.addRow(this, "WSDL file:", wsdlFileLocation, "Select");
		wsdlFileLocation.setEditable(false);
	}

	@Override
	protected void setListeners() {
		super.setListeners();
		wsdlFileBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return "WSDL files (*.wsdl)";
					}

					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith(".wsdl");
					}
				});
				if(!wsdlFileLocation.getText().isEmpty()) {
					fc.setSelectedFile(new File(wsdlFileLocation.getText()));
				}
				if (fc.showOpenDialog(DiskCreatorPane.this) == JFileChooser.APPROVE_OPTION) {
					wsdlFileLocation.setText(fc.getSelectedFile().getAbsolutePath());
					setOperationEnabled(isServicemixLocationValid());
				}
			}
		});
	}

	@Override
	public boolean isWsdlLocationValid() {
		return !wsdlFileLocation.getText().isEmpty();
	}

	@Override
	public Thread getWorkerThread() {
		return new DiskProxyCreatorThread(this, getWsdlLocation(), getOutputLocation());
	}

	@Override
	public String getWsdlLocation() {
		return wsdlFileLocation.getText();
	}

}
