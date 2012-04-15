package net.vandut.agh.magisterka.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import net.vandut.agh.magisterka.gui.GuiUtils;

public abstract class CreatorPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField OutputLocation;
	private JButton outputBtn;

	private JProgressBar progress;
	private JButton operationBtn;
	
	public CreatorPane() {
		createGUI();
		setListeners();
	}
	
	private void createGUI() {
		setLayout(new MigLayout("", "[]10[200]10[]", ""));
		
		GuiUtils.addSeparator(this, "Options");
		setUpOptionsSection();

		OutputLocation = new JTextField();
		outputBtn = GuiUtils.addRow(this, "Output:", OutputLocation, "Select");
		OutputLocation.setEditable(false);

		GuiUtils.addSeparator(this, "Action");
		progress = new JProgressBar();
		operationBtn = GuiUtils.addRow(this, "Status:", progress, "Start");
		operationBtn.setEnabled(false);
	}
	
	protected abstract void setUpOptionsSection();
	
	protected void setListeners() {
		outputBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setAcceptAllFileFilterUsed(false);
				if(!OutputLocation.getText().isEmpty()) {
					fc.setSelectedFile(new File(OutputLocation.getText()));
				}
				if (fc.showOpenDialog(CreatorPane.this) == JFileChooser.APPROVE_OPTION) {
					OutputLocation.setText(fc.getSelectedFile().getAbsolutePath());
					setOperationEnabled(isWsdlLocationValid());
				}
			}
		});
		operationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = getWorkerThread();
				t.start();
			}
		});
	}
	
	public abstract boolean isWsdlLocationValid();
	
	public abstract String getWsdlLocation();
	
	public boolean isServicemixLocationValid() {
		return !OutputLocation.getText().isEmpty();
	}
	
	public String getOutputLocation() {
		return OutputLocation.getText();
	}
	
	public abstract Thread getWorkerThread();
	
	public void setOperationEnabled(boolean flag) {
		operationBtn.setEnabled(flag);
	}
	
	public void setProgress(int value) {
		progress.setValue(value);
	}

}
