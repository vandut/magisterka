package net.vandut.magisterka.fullapp;

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

@ContentView(R.layout.activity_logic)
public class LogicActivity extends RoboSherlockFragmentActivity implements KsoapService.ChangeListener {

	@InjectView(R.id.button_logic_status) Button buttonLogicStatus;
	@InjectView(R.id.button_logic_start)  Button buttonLogicStart;
	@InjectView(R.id.button_logic_stop)   Button buttonLogicStop;
	
	@Inject SharedPreferences sharedPreferences;

	@InjectResource(R.string.pref_default_logic_port)
	String logicServiceDefaultPort;
	@InjectResource(R.string.pref_default_logic_ip_address)
	String logicServiceDefaultIP;
	
	private SoapService soapService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		buttonLogicStatus.setOnClickListener(buttonLogicStatusListener);
		buttonLogicStart.setOnClickListener(buttonLogicStartListener);
		buttonLogicStop.setOnClickListener(buttonLogicStopListener);
	}

	@Override
	public void onStart() {
		super.onStart();
		soapService = buildSoapService();
	}
	
	private SoapService buildSoapService() {
		String logicServicePort;
		String logicServiceIP;

		logicServicePort = sharedPreferences.getString("logic_port", logicServiceDefaultPort);
		if(sharedPreferences.getBoolean("custom_ips_checkbox", false)) {
			logicServiceIP = sharedPreferences.getString("logic_ip_address", logicServiceDefaultIP);
		} else {
			logicServiceIP = sharedPreferences.getString("general_ip_address", logicServiceDefaultIP);
		}
		
		String url = String.format("http://%s:%s/LogicService", logicServiceIP, logicServicePort);
		
		return new SoapService("http://service.logic.magisterka.agh.vandut.net/", url);
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
			startActivity(new Intent(LogicActivity.this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onKsoapServiceChanged(boolean running) {
		buttonLogicStatus.setEnabled(!running);
		buttonLogicStart.setEnabled(!running);
		buttonLogicStop.setEnabled(!running);
	}
	
	private KsoapFragment getKsoapFragment() {
		return (KsoapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentKsoap);
	}

	private View.OnClickListener buttonLogicStatusListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("statusLogic");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

	private View.OnClickListener buttonLogicStartListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("startLogic");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

	private View.OnClickListener buttonLogicStopListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("stopLogic");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

}
