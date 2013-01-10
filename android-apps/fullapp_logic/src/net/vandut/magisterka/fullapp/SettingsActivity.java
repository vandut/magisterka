package net.vandut.magisterka.fullapp;

import java.util.regex.Pattern;

import net.vandut.magisterka.fullapp_logic.R;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class SettingsActivity extends SherlockPreferenceActivity {

	private static final Pattern PARTIAl_IP_ADDRESS = Pattern
			.compile("^((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])\\.){0,3}"
					+ "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9])){0,1}$");
	
	@SuppressWarnings("deprecation")
	private void createIpTextWatcherForPreference(String preferenceKey) {
		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			private String mPreviousText = "";

			@Override
			public void afterTextChanged(Editable s) {
				if (PARTIAl_IP_ADDRESS.matcher(s).matches()) {
					mPreviousText = s.toString();
				} else {
					s.replace(0, s.length(), mPreviousText);
				}
			}
		};
		EditTextPreference editTextPreference = (EditTextPreference)findPreference(preferenceKey);
		editTextPreference.getEditText().addTextChangedListener(textWatcher);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		setupSimplePreferencesScreen();
	}

	/**
	 * Shows the simplified settings UI if the device configuration dictates
	 * that a simplified, single-pane UI should be shown.
	 */
	@SuppressWarnings("deprecation")
	private void setupSimplePreferencesScreen() {
		// In the simplified UI, fragments are not used at all and we instead
		// use the older PreferenceActivity APIs.

		// Add 'general' preferences.
		addPreferencesFromResource(R.xml.pref_general);

		// Add corresponding header.
		PreferenceCategory fakeDoorHeader = new PreferenceCategory(this);
		fakeDoorHeader.setTitle(R.string.pref_header_door);
		getPreferenceScreen().addPreference(fakeDoorHeader);
		addPreferencesFromResource(R.xml.pref_door);
		// Add corresponding header.
		PreferenceCategory fakePowerHeader = new PreferenceCategory(this);
		fakePowerHeader.setTitle(R.string.pref_header_power);
		getPreferenceScreen().addPreference(fakePowerHeader);
		addPreferencesFromResource(R.xml.pref_power);
		// Add corresponding header.
		PreferenceCategory fakeSensorsHeader = new PreferenceCategory(this);
		fakeSensorsHeader.setTitle(R.string.pref_header_sensors);
		getPreferenceScreen().addPreference(fakeSensorsHeader);
		addPreferencesFromResource(R.xml.pref_sensors);
		// Add corresponding header.
		PreferenceCategory fakeCameraHeader = new PreferenceCategory(this);
		fakeCameraHeader.setTitle(R.string.pref_header_camera);
		getPreferenceScreen().addPreference(fakeCameraHeader);
		addPreferencesFromResource(R.xml.pref_camera);
		// Add corresponding header.
		PreferenceCategory fakePyroHeader = new PreferenceCategory(this);
		fakePyroHeader.setTitle(R.string.pref_header_pyro);
		getPreferenceScreen().addPreference(fakePyroHeader);
		addPreferencesFromResource(R.xml.pref_pyro);
		// Add corresponding header.
		PreferenceCategory fakeLogicHeader = new PreferenceCategory(this);
		fakeLogicHeader.setTitle(R.string.pref_header_logic);
		getPreferenceScreen().addPreference(fakeLogicHeader);
		addPreferencesFromResource(R.xml.pref_logic);

		createIpTextWatcherForPreference("door_ip_address");
		createIpTextWatcherForPreference("power_ip_address");
		createIpTextWatcherForPreference("sensors_ip_address");
		createIpTextWatcherForPreference("camera_ip_address");
		createIpTextWatcherForPreference("pyro_ip_address");

		// Bind the summaries of EditText/List/Dialog/Ringtone preferences to
		// their values. When their values change, their summaries are updated
		// to reflect the new value, per the Android Design guidelines.
		bindPreferenceSummaryToValue(findPreference("door_port"));
		bindPreferenceSummaryToValue(findPreference("door_ip_address"));
		bindPreferenceSummaryToValue(findPreference("power_port"));
		bindPreferenceSummaryToValue(findPreference("power_ip_address"));
		bindPreferenceSummaryToValue(findPreference("sensors_port"));
		bindPreferenceSummaryToValue(findPreference("sensors_ip_address"));
		bindPreferenceSummaryToValue(findPreference("camera_port"));
		bindPreferenceSummaryToValue(findPreference("camera_ip_address"));
		bindPreferenceSummaryToValue(findPreference("pyro_port"));
		bindPreferenceSummaryToValue(findPreference("pyro_ip_address"));
		bindPreferenceSummaryToValue(findPreference("logic_interval"));
		bindPreferenceSummaryToValue(findPreference("logic_open"));
		bindPreferenceSummaryToValue(findPreference("logic_close"));
	}

	/**
	 * A preference value change listener that updates the preference's summary
	 * to reflect its new value.
	 */
	private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
		@Override
		public boolean onPreferenceChange(Preference preference, Object value) {
			String stringValue = value.toString();

			if (preference instanceof ListPreference) {
				// For list preferences, look up the correct display value in
				// the preference's 'entries' list.
				ListPreference listPreference = (ListPreference) preference;
				int index = listPreference.findIndexOfValue(stringValue);

				// Set the summary to reflect the new value.
				preference
						.setSummary(index >= 0 ? listPreference.getEntries()[index]
								: null);

			} else {
				// For all other preferences, set the summary to the value's
				// simple string representation.
				preference.setSummary(stringValue);
			}
			return true;
		}
	};

	/**
	 * Binds a preference's summary to its value. More specifically, when the
	 * preference's value is changed, its summary (line of text below the
	 * preference title) is updated to reflect the value. The summary is also
	 * immediately updated upon calling this method. The exact display format is
	 * dependent on the type of preference.
	 * 
	 * @see #sBindPreferenceSummaryToValueListener
	 */
	private static void bindPreferenceSummaryToValue(Preference preference) {
		// Set the listener to watch for value changes.
		preference
				.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

		// Trigger the listener immediately with the preference's
		// current value.
		sBindPreferenceSummaryToValueListener.onPreferenceChange(
				preference,
				PreferenceManager.getDefaultSharedPreferences(
						preference.getContext()).getString(preference.getKey(),
						""));
	}
}
