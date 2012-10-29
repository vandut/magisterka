package net.vandut.agh.magisterka.logicclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;
import net.vandut.agh.magisterka.logic.service.Logic;
import net.vandut.agh.magisterka.logicclient.handlers.CamServiceHandler;
import net.vandut.agh.magisterka.logicclient.handlers.DoorServiceHandler;
import net.vandut.agh.magisterka.logicclient.handlers.LogicServiceHandler;
import net.vandut.agh.magisterka.logicclient.handlers.PowerSwitchServiceHandler;
import net.vandut.agh.magisterka.logicclient.handlers.SensorsServiceHandler;
import net.vandut.agh.magisterka.logicclient.handlers.TemperatureServiceHandler;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;

import cam.SmartCameraPortType;

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String FRAME_TITLE = "Logic Client";
	private static Gui _INSTANCE = null;

	private final EcfClient ecfClient;

	private JTextField textFieldIpAddress;
	private JButton btnConnection;

	private JLabel statusLabelDoorService;
	private JLabel statusLabelSensorsService;
	private JLabel statusLabelCamService;
	private JLabel statusLabelPowerSwitchService;
	private JLabel statusLabelLogicService;
	private JLabel statusLabelTemperatureService;

	private JButton actionBtnDoorStatus;
	private JButton actionBtnDoorOpen;
	private JButton actionBtnDoorClose;

	private JButton actionBtnSensorsTemperature;
	private JButton actionBtnSensorsHumidity;
	private JButton actionBtnSensorsPressure;
	
	private JButton actionBtnCamStartClassifier;
	private JButton actionBtnCamGetLast;

	private JButton actionBtnPowerSwitchOn1;

	private JButton actionBtnLogicStatus;
	private JButton actionBtnLogicStart;
	private JButton actionBtnLogicStop;

	private JButton actionBtnTemperatureGetTemp;

	DoorServiceHandler serviceHandlerDoor;
	SensorsServiceHandler serviceHandlerSensors;
	CamServiceHandler serviceHandlerCam;
	PowerSwitchServiceHandler serviceHandlerPowerSwitch;
	LogicServiceHandler serviceHandlerLogic;
	TemperatureServiceHandler serviceHandlerTemperature;

	private final ActionListener actionListenerBtnConnection = new ActionListener() {
		public void actionPerformed(ActionEvent action) {
			if (ecfClient.isRegistered()) {
				ecfClient.unregister();
				btnConnection.setText("Connect");
			} else {
				try {
					String zoodiscoveryServerIp = textFieldIpAddress.getText().trim();
					if(zoodiscoveryServerIp.isEmpty()) {
						throw new IllegalArgumentException("Empty address");
					}
					ecfClient.register(zoodiscoveryServerIp);
					btnConnection.setText("Disconnect");
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Connection failed, details: " + e.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	private Gui(BundleContext context) throws Exception {
		super(FRAME_TITLE);
		_INSTANCE = this;
		this.ecfClient = new EcfClient(context);

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
		textFieldIpAddress = new JTextField("127.0.0.1");
		btnConnection = new JButton("Connect");

		statusLabelDoorService = new JLabel("Pending...");
		statusLabelSensorsService = new JLabel("Pending...");
		statusLabelCamService = new JLabel("Pending...");
		statusLabelPowerSwitchService = new JLabel("Pending...");
		statusLabelLogicService = new JLabel("Pending...");
		statusLabelTemperatureService = new JLabel("Pending...");

		actionBtnDoorStatus = new JButton("Status");
		actionBtnDoorOpen = new JButton("Open");
		actionBtnDoorClose = new JButton("Close");

		actionBtnSensorsTemperature = new JButton("Temperature");
		actionBtnSensorsHumidity = new JButton("Humidity");
		actionBtnSensorsPressure = new JButton("Pressure");
		
		actionBtnCamStartClassifier = new JButton("Start Classifier");
		actionBtnCamGetLast = new JButton("Get Last");
		
		actionBtnPowerSwitchOn1 = new JButton("On 1");

		actionBtnLogicStatus = new JButton("Status");
		actionBtnLogicStart = new JButton("Start");
		actionBtnLogicStop = new JButton("Stop");
		
		actionBtnTemperatureGetTemp = new JButton("Get Temp");

		btnConnection.addActionListener(actionListenerBtnConnection);
	}

	private void createGUI() {
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new MigLayout("fill"));

		GuiUtils.addSeparator(panel, "ZooKeeper server");
		panel.add(textFieldIpAddress, "split 2, growx");
		panel.add(btnConnection, "wrap");

		GuiUtils.addSeparator(panel, "Status");
		panel.add(statusLabelDoorService, "growx, wrap");
		panel.add(statusLabelSensorsService, "growx, wrap");
		panel.add(statusLabelCamService, "growx, wrap");
		panel.add(statusLabelPowerSwitchService, "growx, wrap");
		panel.add(statusLabelLogicService, "growx, wrap");
		panel.add(statusLabelTemperatureService, "growx, wrap");

		GuiUtils.addSeparator(panel, "Door Service");
		panel.add(actionBtnDoorStatus, "split 3");
		panel.add(actionBtnDoorOpen, "");
		panel.add(actionBtnDoorClose, "wrap");
		GuiUtils.addSeparator(panel, "Sensors Service");
		panel.add(actionBtnSensorsTemperature, "split 3");
		panel.add(actionBtnSensorsHumidity, "");
		panel.add(actionBtnSensorsPressure, "wrap");
		GuiUtils.addSeparator(panel, "Camera Service");
		panel.add(actionBtnCamStartClassifier, "split 2");
		panel.add(actionBtnCamGetLast, "wrap");

		GuiUtils.addSeparator(panel, "Power Switch Service");
		panel.add(actionBtnPowerSwitchOn1, "wrap");
		
		GuiUtils.addSeparator(panel, "Logic Service");
		panel.add(actionBtnLogicStatus, "split 3");
		panel.add(actionBtnLogicStart, "");
		panel.add(actionBtnLogicStop, "wrap");

		GuiUtils.addSeparator(panel, "Temperature Service");
		panel.add(actionBtnTemperatureGetTemp, "wrap");

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void createServiceHandlers() throws InvalidSyntaxException {
		serviceHandlerDoor = new DoorServiceHandler("Door Service",
				hsoa_2.ServiceSoap.class, statusLabelDoorService,
				actionBtnDoorStatus, actionBtnDoorOpen, actionBtnDoorClose);
		serviceHandlerSensors = new SensorsServiceHandler("Sensors Service",
				hsoa_1.ServiceSoap.class, statusLabelSensorsService,
				actionBtnSensorsTemperature, actionBtnSensorsHumidity,
				actionBtnSensorsPressure);
		serviceHandlerCam = new CamServiceHandler("Camera Service",
				SmartCameraPortType.class, statusLabelCamService,
				actionBtnCamStartClassifier, actionBtnCamGetLast);
		serviceHandlerPowerSwitch = new PowerSwitchServiceHandler("Power Switch Service",
				hsoa_3.ServiceSoap.class, statusLabelPowerSwitchService,
				actionBtnPowerSwitchOn1);
		serviceHandlerLogic = new LogicServiceHandler("Logic Service",
				Logic.class, statusLabelLogicService,
				actionBtnLogicStatus, actionBtnLogicStart, actionBtnLogicStop);
		serviceHandlerTemperature = new TemperatureServiceHandler("Temperature Service",
				pyro.TemperatureService.class, statusLabelTemperatureService,
				actionBtnTemperatureGetTemp);

		serviceHandlerDoor.register(ecfClient);
		serviceHandlerSensors.register(ecfClient);
		serviceHandlerCam.register(ecfClient);
		serviceHandlerPowerSwitch.register(ecfClient);
		serviceHandlerLogic.register(ecfClient);
		serviceHandlerTemperature.register(ecfClient);
	}

	public void close() {
		super.dispose();
		_INSTANCE = null;
	}

	public static Gui getInstance() {
		return _INSTANCE;
	}

	public static void start(final BundleContext context) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new Gui(context);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void main(String[] args) {
		start(null);
	}

}
