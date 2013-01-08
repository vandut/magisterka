package net.vandut.magisterka.fullapp;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboSherlockFragmentActivity {
	
	@InjectView(R.id.doorService)    Button buttonDoorService;
	@InjectView(R.id.powerService)   Button buttonPowerService;
	@InjectView(R.id.sensorsService) Button buttonSensorsService;
	@InjectView(R.id.cameraService)  Button buttonCameraService;
	@InjectView(R.id.pyroService)    Button buttonPyroService;
	@InjectView(R.id.logicService)   Button buttonLogicService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		   buttonDoorService.setOnClickListener(buttonDoorServiceListener);
		  buttonPowerService.setOnClickListener(buttonPowerServiceListener);
		buttonSensorsService.setOnClickListener(buttonSensorsServiceListener);
		 buttonCameraService.setOnClickListener(buttonCameraServiceListener);
		   buttonPyroService.setOnClickListener(buttonPyroServiceListener);
		  buttonLogicService.setOnClickListener(buttonLogicServiceListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			startActivity(new Intent(MainActivity.this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void startActivity(Class<? extends Activity> clazz) {
		startActivity(new Intent(this, clazz));
	}
	
	private View.OnClickListener buttonDoorServiceListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(DoorActivity.class);
		}
	};
	private View.OnClickListener buttonPowerServiceListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(PowerActivity.class);
		}
	};
	private View.OnClickListener buttonSensorsServiceListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(SensorsActivity.class);
		}
	};
	private View.OnClickListener buttonCameraServiceListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(CameraActivity.class);
		}
	};
	private View.OnClickListener buttonPyroServiceListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(PyroActivity.class);
		}
	};
	private View.OnClickListener buttonLogicServiceListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			startActivity(LogicActivity.class);
		}
	};

}
