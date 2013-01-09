package net.vandut.magisterka.fullapp;

import net.vandut.magisterka.fullapp_logic.R;
import net.vandut.magisterka.ksoap.soap.SoapService;
import net.vandut.magisterka.ksoap.soap.SoapService.SoapMethod;
import roboguice.inject.InjectResource;
import roboguice.service.RoboService;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.google.inject.Inject;

public class LogicService extends RoboService {
	
	@Inject SharedPreferences sharedPreferences;
	
	@InjectResource(R.string.pref_default_camera_port)
	String cameraServiceDefaultPort;
	@InjectResource(R.string.pref_default_camera_ip_address)
	String cameraServiceDefaultIP;

	@InjectResource(R.string.pref_default_door_port)
	String doorServiceDefaultPort;
	@InjectResource(R.string.pref_default_door_ip_address)
	String doorServiceDefaultIP;

	private static boolean running = false;
	
	private Handler uiHandler;

	public static boolean isRunning() {
		return running;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	synchronized public int onStartCommand(Intent intent, int flags, int startId) {
		uiHandler = new Handler(Looper.getMainLooper());
		new Thread(new Runnable() {
			@Override
			public void run() {
				running = true;
				
				doWork();
				
				running = false;
			}
		}).start();

		return Service.START_NOT_STICKY;
	}

	private void doWork() {
		showToast("Logic Service started");
		cameraStartClassifier();
		String person = cameraGetLast();
		if("s1".equals(person)) {
			doorOpen();
			showToast("Logic Service Opened Door");
		} else if("s2".equals(person)) {
			doorClose();
			showToast("Logic Service Closed Door");
		} else {
			showToast("Logic Service did nothing");
		}
	}
	
	private void showToast(final String msg) {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(LogicService.this, msg, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
	
	private SoapService buildCameraSoapService() {
		String cameraServicePort;
		String cameraServiceIP;

		cameraServicePort = sharedPreferences.getString("camera_port", cameraServiceDefaultPort);
		if(sharedPreferences.getBoolean("custom_ips_checkbox", true)) {
			cameraServiceIP = sharedPreferences.getString("camera_ip_address", cameraServiceDefaultIP);
		} else {
			cameraServiceIP = sharedPreferences.getString("general_ip_address", cameraServiceDefaultIP);
		}
		
		String url = String.format("http://%s:%s/", cameraServiceIP, cameraServicePort);
		
		return new SoapService("cam", url);
	}
	
	private SoapService buildDoorSoapService() {
		String doorServicePort;
		String doorServiceIP;

		doorServicePort = sharedPreferences.getString("door_port", doorServiceDefaultPort);
		if(sharedPreferences.getBoolean("custom_ips_checkbox", true)) {
			doorServiceIP = sharedPreferences.getString("door_ip_address", doorServiceDefaultIP);
		} else {
			doorServiceIP = sharedPreferences.getString("general_ip_address", doorServiceDefaultIP);
		}
		
		String url = String.format("http://%s:%s/DoorService", doorServiceIP, doorServicePort);
		
		return new SoapService("HSOA_2", url);
	}
	
	synchronized private String callSoapMethod(SoapService soapService, String method) {
		SoapMethod soapMethod = soapService.getSoapMethod(method);
		try {
			while(KsoapService.ksoapService.serviceRunning) {
				wait(100);
			}
			changeKsoapServiceState(true, null);
			String result = soapMethod.call().toString();
			changeKsoapServiceState(false, soapMethod);
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	private void changeKsoapServiceState(final boolean state, final SoapMethod soapMethod) {
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				KsoapService.ksoapService.serviceRunning = state;
				KsoapService.ksoapService.informListenersOnChange(soapMethod);
			}
		});
	}

	private void cameraStartClassifier() {
		SoapService soapService = buildCameraSoapService();
		callSoapMethod(soapService, "StartClassifier");
	}

	private String cameraGetLast() {
		SoapService soapService = buildCameraSoapService();
		return callSoapMethod(soapService, "GetLast");
	}

	private void doorOpen() {
		SoapService soapService = buildDoorSoapService();
		callSoapMethod(soapService, "door_open");
	}

	private void doorClose() {
		SoapService soapService = buildDoorSoapService();
		callSoapMethod(soapService, "door_close");
	}

}
