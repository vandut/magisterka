package net.vandut.magisterka.fullapp;

import net.vandut.magisterka.fullapp_logic.R;
import net.vandut.magisterka.ksoap.soap.SoapService;
import net.vandut.magisterka.ksoap.soap.SoapService.SoapMethod;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.inject.Inject;

@ContentView(R.layout.activity_camera)
public class CameraActivity extends RoboSherlockFragmentActivity implements KsoapService.ChangeListener {

	@InjectView(R.id.button_camera_start_classifier) Button buttonCameraStartClassifier;
	@InjectView(R.id.button_camera_get_last)         Button buttonCameraGetLast;
	
	@Inject SharedPreferences sharedPreferences;

	@InjectResource(R.string.pref_default_camera_port)
	String cameraServiceDefaultPort;
	@InjectResource(R.string.pref_default_camera_ip_address)
	String cameraServiceDefaultIP;
	
	private SoapService soapService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		buttonCameraStartClassifier.setOnClickListener(buttonCameraStartClassifierListener);
	            buttonCameraGetLast.setOnClickListener(buttonCameraGetLastListener);
	}

	@Override
	public void onStart() {
		super.onStart();
		soapService = buildSoapService();
	}
	
	private SoapService buildSoapService() {
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.activity_service_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.menu_settings:
			startActivity(new Intent(CameraActivity.this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onKsoapServiceChanged(boolean running) {
		buttonCameraStartClassifier.setEnabled(!running);
		buttonCameraGetLast.setEnabled(!running);
	}
	
	private KsoapFragment getKsoapFragment() {
		return (KsoapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentKsoap);
	}

	private View.OnClickListener buttonCameraStartClassifierListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("StartClassifier");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};
	private View.OnClickListener buttonCameraGetLastListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("GetLast");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

}
