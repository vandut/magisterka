package net.vandut.agh.magisterka.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import net.vandut.agh.magisterka.gui.panel.DiskCreatorPane;
import net.vandut.agh.magisterka.gui.panel.UrlCreatorPane;

public class ProxyCreatorGui extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final String FRAME_TITLE = "Proxy Creator Gui";

	public ProxyCreatorGui() {
		super(FRAME_TITLE);
		setNativeLookAndFeel();
		createAndShowGUI();
	}

	private void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting native LAF: " + e);
		}
	}

	private void createAndShowGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		getContentPane().add(tabbedPane);

		JPanel panel1 = new DiskCreatorPane();
		tabbedPane.addTab("Disk", null, panel1, "Select file from disk");
		JPanel panel2 = new UrlCreatorPane();
		tabbedPane.addTab("Url", null, panel2, "Download file using url");		

		pack();
//		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ProxyCreatorGui();
			}
		});
	}

}
