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

@ContentView(R.layout.activity_pyro)
public class PyroActivity extends RoboSherlockFragmentActivity implements KsoapService.ChangeListener {
	
	@InjectView(R.id.button_pyro_get_temp) Button buttonPowerGetTemp;
	
	@Inject SharedPreferences sharedPreferences;

	@InjectResource(R.string.pref_default_pyro_port)
	String pyroServiceDefaultPort;
	@InjectResource(R.string.pref_default_pyro_ip_address)
	String pyroServiceDefaultIP;
	
	private SoapService soapService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		buttonPowerGetTemp.setOnClickListener(buttonPowerGetTempListener);
	}

	@Override
	public void onStart() {
		super.onStart();
		soapService = buildSoapService();
	}
	
	private SoapService buildSoapService() {
		String pyroServicePort;
		String pyroServiceIP;

		pyroServicePort = sharedPreferences.getString("pyro_port", pyroServiceDefaultPort);
		if(sharedPreferences.getBoolean("custom_ips_checkbox", true)) {
			pyroServiceIP = sharedPreferences.getString("pyro_ip_address", pyroServiceDefaultIP);
		} else {
			pyroServiceIP = sharedPreferences.getString("general_ip_address", pyroServiceDefaultIP);
		}
		
		String url = String.format("http://%s:%s/PyroService", pyroServiceIP, pyroServicePort);
		
		return new SoapService("http://pyro/", url);
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
			startActivity(new Intent(PyroActivity.this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onKsoapServiceChanged(boolean running, SoapMethod method) {
		buttonPowerGetTemp.setEnabled(!running);
	}
	
	private KsoapFragment getKsoapFragment() {
		return (KsoapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentKsoap);
	}

	private View.OnClickListener buttonPowerGetTempListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("getTemp");
			getKsoapFragment().deliverKsoapInstruction(method, false);
		}
	};

}
