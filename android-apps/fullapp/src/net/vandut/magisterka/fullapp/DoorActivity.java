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

@ContentView(R.layout.activity_door)
public class DoorActivity extends RoboSherlockFragmentActivity implements KsoapService.ChangeListener {
	
	@InjectView(R.id.button_door_status) Button buttonDoorStatus;
	@InjectView(R.id.button_door_open)   Button buttonDoorOpen;
	@InjectView(R.id.button_door_close)  Button buttonDoorClose;
	
	@Inject SharedPreferences sharedPreferences;

	@InjectResource(R.string.pref_default_door_port)
	String doorServiceDefaultPort;
	@InjectResource(R.string.pref_default_door_ip_address)
	String doorServiceDefaultIP;
	
	private SoapService soapService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		buttonDoorStatus.setOnClickListener(buttonDoorStatusListener);
		  buttonDoorOpen.setOnClickListener(buttonDoorOpenListener);
		 buttonDoorClose.setOnClickListener(buttonDoorCloseListener);
	}

	@Override
	public void onStart() {
		super.onStart();
		soapService = buildSoapService();
	}
	
	private SoapService buildSoapService() {
		String doorServicePort;
		String doorServiceIP;

		doorServicePort = sharedPreferences.getString("door_port", doorServiceDefaultPort);
		if(sharedPreferences.getBoolean("custom_ips_checkbox", false)) {
			doorServiceIP = sharedPreferences.getString("door_ip_address", doorServiceDefaultIP);
		} else {
			doorServiceIP = sharedPreferences.getString("general_ip_address", doorServiceDefaultIP);
		}
		
		String url = String.format("http://%s:%s/DoorService", doorServiceIP, doorServicePort);
		
		return new SoapService("HSOA_2", url);
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
		buttonDoorStatus.setEnabled(!running);
		buttonDoorOpen.setEnabled(!running);
		buttonDoorClose.setEnabled(!running);
	}
	
	private KsoapFragment getKsoapFragment() {
		return (KsoapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentKsoap);
	}

	private View.OnClickListener buttonDoorStatusListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("door_status");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};
	
	private View.OnClickListener buttonDoorOpenListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("door_open");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};
	
	private View.OnClickListener buttonDoorCloseListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			SoapMethod method = soapService.getSoapMethod("door_close");
			getKsoapFragment().deliverKsoapInstruction(method);
		}
	};

}
