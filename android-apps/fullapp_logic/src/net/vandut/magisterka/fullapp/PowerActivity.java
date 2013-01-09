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

@ContentView(R.layout.activity_power)
public class PowerActivity extends RoboSherlockFragmentActivity implements KsoapService.ChangeListener {

	@InjectView(R.id.button_power_status) Button buttonPowerStatus;
	@InjectView(R.id.button_power_on1)    Button buttonPowerOn1;
	@InjectView(R.id.button_power_off1)   Button buttonPowerOff1;
	@InjectView(R.id.button_power_on2)    Button buttonPowerOn2;
	@InjectView(R.id.button_power_off2)   Button buttonPowerOff2;
	@InjectView(R.id.button_power_on3)    Button buttonPowerOn3;
	@InjectView(R.id.button_power_off3)   Button buttonPowerOff3;
	@InjectView(R.id.button_power_on4)    Button buttonPowerOn4;
	@InjectView(R.id.button_power_off4)   Button buttonPowerOff4;
	
	@Inject SharedPreferences sharedPreferences;

	@InjectResource(R.string.pref_default_power_port)
	String powerServiceDefaultPort;
	@InjectResource(R.string.pref_default_power_ip_address)
	String powerServiceDefaultIP;
	
	private SoapService soapService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		buttonPowerStatus.setOnClickListener(buttonPowerStatusListener);
		buttonPowerOn1.setOnClickListener(buttonPowerOn1Listener);
		buttonPowerOff1.setOnClickListener(buttonPowerOff1Listener);
		buttonPowerOn2.setOnClickListener(buttonPowerOn2Listener);
		buttonPowerOff2.setOnClickListener(buttonPowerOff2Listener);
		buttonPowerOn3.setOnClickListener(buttonPowerOn3Listener);
		buttonPowerOff3.setOnClickListener(buttonPowerOff3Listener);
		buttonPowerOn4.setOnClickListener(buttonPowerOn4Listener);
		buttonPowerOff4.setOnClickListener(buttonPowerOff4Listener);
	}

	@Override
	public void onStart() {
		super.onStart();
		soapService = buildSoapService();
	}
	
	private SoapService buildSoapService() {
		String powerServicePort;
		String powerServiceIP;

		powerServicePort = sharedPreferences.getString("power_port", powerServiceDefaultPort);
		if(sharedPreferences.getBoolean("custom_ips_checkbox", true)) {
			powerServiceIP = sharedPreferences.getString("power_ip_address", powerServiceDefaultIP);
		} else {
			powerServiceIP = sharedPreferences.getString("general_ip_address", powerServiceDefaultIP);
		}
		
		String url = String.format("http://%s:%s/PowerSwitch", powerServiceIP, powerServicePort);
		
		return new SoapService("HSOA_3", url);
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
			startActivity(new Intent(PowerActivity.this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onKsoapServiceChanged(boolean running, SoapMethod method) {
		buttonPowerStatus.setEnabled(!running);
		buttonPowerOn1.setEnabled(!running);
		buttonPowerOff1.setEnabled(!running);
		buttonPowerOn2.setEnabled(!running);
		buttonPowerOff2.setEnabled(!running);
		buttonPowerOn3.setEnabled(!running);
		buttonPowerOff3.setEnabled(!running);
		buttonPowerOn4.setEnabled(!running);
		buttonPowerOff4.setEnabled(!running);
	}
	
	private KsoapFragment getKsoapFragment() {
		return (KsoapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentKsoap);
	}

	private View.OnClickListener buttonPowerStatusListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_status");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};

	private View.OnClickListener buttonPowerOn1Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_on1");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};
	private View.OnClickListener buttonPowerOff1Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_off1");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};
	private View.OnClickListener buttonPowerOn2Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_on2");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};
	private View.OnClickListener buttonPowerOff2Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_off2");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};
	private View.OnClickListener buttonPowerOn3Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_on3");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};
	private View.OnClickListener buttonPowerOff3Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_off3");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};
	private View.OnClickListener buttonPowerOn4Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_on4");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};
	private View.OnClickListener buttonPowerOff4Listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("switch_on4");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};

}
