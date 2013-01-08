package net.vandut.magisterka.fullapp;

import net.vandut.magisterka.fullapp_logic.R;
import roboguice.inject.ContentView;
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
		}
	};

	private View.OnClickListener buttonLogicStartListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
		}
	};

	private View.OnClickListener buttonLogicStopListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
		}
	};

}
