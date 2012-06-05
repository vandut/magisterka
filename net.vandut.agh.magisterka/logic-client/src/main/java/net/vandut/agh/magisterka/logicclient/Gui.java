package net.vandut.agh.magisterka.logicclient;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import net.vandut.agh.magisterka.logic.service.LogicService;
import net.vandut.agh.magisterka.logicclient.handlers.DoorServiceHandler;
import net.vandut.agh.magisterka.logicclient.handlers.LogicServiceHandler;
import net.vandut.agh.magisterka.logicclient.handlers.SensorsServiceHandler;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String FRAME_TITLE = "Logic Client";
	private static Gui _INSTANCE = null;

	private final EcfClient ecfClient;

	private JLabel statusLabelDoorService;
	private JLabel statusLabelSensorsService;
	private JLabel statusLabelLogicService;

	private JButton actionBtnDoorStatus;
	private JButton actionBtnDoorOpen;
	private JButton actionBtnDoorClose;

	private JButton actionBtnSensorsTemperature;
	private JButton actionBtnSensorsHumidity;
	private JButton actionBtnSensorsPressure;

	private JButton actionBtnLogicStart;
	private JButton actionBtnLogicStop;

	DoorServiceHandler serviceHandlerDoor;
	SensorsServiceHandler serviceHandlerSensors;
	LogicServiceHandler serviceHandlerLogic;

	private Gui(BundleContext context, EcfClient ecfClient) throws Exception {
		super(FRAME_TITLE);
		_INSTANCE = this;
		this.ecfClient = ecfClient;

		setNativeLookAndFeel();
		createControls();
		createGUI();
		createServiceHandlers();

		setVisible(true);
	}

	private void setNativeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting native L&F: " + e);
		}
	}

	private void createControls() {
		statusLabelDoorService = new JLabel("pending");
		statusLabelSensorsService = new JLabel("pending");
		statusLabelLogicService = new JLabel("pending");

		actionBtnDoorStatus = new JButton("status");
		actionBtnDoorOpen = new JButton("open");
		actionBtnDoorClose = new JButton("close");

		actionBtnSensorsTemperature = new JButton("temperature");
		actionBtnSensorsHumidity = new JButton("humidity");
		actionBtnSensorsPressure = new JButton("pressure");

		actionBtnLogicStart = new JButton("start");
		actionBtnLogicStop = new JButton("stop");
	}

	private void createGUI() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new MigLayout("fill"));

		GuiUtils.addSeparator(panel, "Status");
		panel.add(statusLabelDoorService, "growx, wrap");
		panel.add(statusLabelSensorsService, "growx, wrap");
		panel.add(statusLabelLogicService, "growx, wrap");

		GuiUtils.addSeparator(panel, "Door Service");
		panel.add(actionBtnDoorStatus, "split 3");
		panel.add(actionBtnDoorOpen, "");
		panel.add(actionBtnDoorClose, "wrap");
		GuiUtils.addSeparator(panel, "Sensors Service");
		panel.add(actionBtnSensorsTemperature, "split 3");
		panel.add(actionBtnSensorsHumidity, "");
		panel.add(actionBtnSensorsPressure, "wrap");
		GuiUtils.addSeparator(panel, "Logic Service");
		panel.add(actionBtnLogicStart, "split 2");
		panel.add(actionBtnLogicStop, "wrap");

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void createServiceHandlers() throws InvalidSyntaxException {
		serviceHandlerDoor = new DoorServiceHandler("Door Service", hsoa_2.ServiceSoap.class,
				statusLabelDoorService, actionBtnDoorStatus, actionBtnDoorOpen, actionBtnDoorClose);
		serviceHandlerSensors = new SensorsServiceHandler("Sensors Service", hsoa_1.ServiceSoap.class,
				statusLabelSensorsService, actionBtnSensorsTemperature, actionBtnSensorsHumidity,
				actionBtnSensorsPressure);
		serviceHandlerLogic = new LogicServiceHandler("Logic Service", LogicService.class,
				statusLabelLogicService, actionBtnLogicStart, actionBtnLogicStop);

		serviceHandlerDoor.register(ecfClient);
		serviceHandlerSensors.register(ecfClient);
		serviceHandlerLogic.register(ecfClient);
	}

	public void close() {
		super.dispose();
		_INSTANCE = null;
	}

	public static Gui getInstance() {
		return _INSTANCE;
	}

	public static void start(final BundleContext context, final EcfClient ecfClient) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Gui(context, ecfClient);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		start(null, null);
	}

}
