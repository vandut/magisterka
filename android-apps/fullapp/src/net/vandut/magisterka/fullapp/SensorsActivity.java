package net.vandut.magisterka.fullapp;

import net.vandut.magisterka.ksoap.soap.SoapService;
import net.vandut.magisterka.ksoap.soap.SoapService.SoapMethod;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.inject.Inject;

@ContentView(R.layout.activity_sensors)
public class SensorsActivity extends RoboSherlockFragmentActivity implements KsoapService.ChangeListener {

	@InjectView(R.id.button_sensors_temperature) Button buttonSensorsTemperature;
	@InjectView(R.id.button_sensors_humidity)    Button buttonSensorsHumidity;
	@InjectView(R.id.button_sensors_pressure)    Button buttonSensorsPressure;
	
	@Inject SharedPreferences sharedPreferences;

	@InjectResource(R.string.pref_default_sensors_port)
	String sensorsServiceDefaultPort;
	@InjectResource(R.string.pref_default_sensors_ip_address)
	String sensorsServiceDefaultIP;
	
	private SoapService soapService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		buttonSensorsTemperature.setOnClickListener(buttonSensorsTemperatureListener);
	   	   buttonSensorsHumidity.setOnClickListener(buttonSensorsHumidityListener);
		   buttonSensorsPressure.setOnClickListener(buttonSensorsPressureListener);
	}

	@Override
	public void onStart() {
		super.onStart();
		soapService = buildSoapService();
	}
	
	private SoapService buildSoapService() {
		String sensorsServicePort;
		String sensorsServiceIP;

		sensorsServicePort = sharedPreferences.getString("sensors_port", sensorsServiceDefaultPort);
		if(sharedPreferences.getBoolean("custom_ips_checkbox", false)) {
			sensorsServiceIP = sharedPreferences.getString("sensors_ip_address", sensorsServiceDefaultIP);
		} else {
			sensorsServiceIP = sharedPreferences.getString("general_ip_address", sensorsServiceDefaultIP);
		}
		
		String url = String.format("http://%s:%s/SensorsService", sensorsServiceIP, sensorsServicePort);
		
		return new SoapService("HSOA_1", url);
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
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onKsoapServiceChanged(boolean running) {
		buttonSensorsTemperature.setEnabled(!running);
		buttonSensorsHumidity.setEnabled(!running);
		buttonSensorsPressure.setEnabled(!running);
	}
	
	private KsoapFragment getKsoapFragment() {
		return (KsoapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentKsoap);
	}

	private View.OnClickListener buttonSensorsTemperatureListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("get_temperature");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

	private View.OnClickListener buttonSensorsHumidityListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("get_humidity");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

	private View.OnClickListener buttonSensorsPressureListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("get_pressure");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

}
