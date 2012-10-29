package net.vandut.magisterka.ksoap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.vandut.magisterka.ksoap.data.HostModel;
import net.vandut.magisterka.ksoap.data.MethodModel;
import net.vandut.magisterka.ksoap.data.ServiceModel;
import net.vandut.magisterka.ksoap.soap.SoapService;
import net.vandut.magisterka.ksoap.soap.SoapService.SoapMethod;

import org.ksoap2.serialization.SoapPrimitive;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {

	private static final String TAG = MainActivity.class.getSimpleName();

	private static final int RC_EDIT_HOST = 1;

	private static final String PREFS_HOSTMODELS_FILE = "HostModelsFile";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fillView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void fillView() {
		Button buttonEditHost = (Button) findViewById(R.id.buttonEditHost);
		buttonEditHost.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putString(AddHostListActivity.QUERY_MARSHALLED_HOST, getHostModelFromPreferences().marshall());
				startActivityForResult(AddHostListActivity.class, bundle, RC_EDIT_HOST);
			}
		});
		HostModel hostModel = getHostModelFromPreferences();
		setHostAdapter(hostModel);
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setOnItemClickListener(this);
		listView.setItemsCanFocus(true);
	}

	private void startActivityForResult(Class<? extends Activity> clazz, Bundle bundle,
			int requestCode) {
		Intent intent = new Intent(this, clazz);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_EDIT_HOST && resultCode == Activity.RESULT_OK) {
			String marshalledHost = data.getExtras().getString(AddHostListActivity.RESULT_MARSHALLED_HOST);
			Log.d(TAG, "marshalledHost: "+marshalledHost);
			saveHostToProperties(marshalledHost);
		}
	}
	
	private void setHostAdapter(HostModel hostModel) {
		ListView listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(createAdapterFromHostModel(hostModel));
	}
	
	private HostModel getHostModelFromPreferences() {
		SharedPreferences hostsPref = getSharedPreferences(PREFS_HOSTMODELS_FILE, 0);
		String marshalledHost = hostsPref.getString("host", null);
		if(marshalledHost == null) {
			return HostModel.unmarshall("Hathor|192.168.1.3|6|"
		                               +"Door|10001|DoorService|HSOA_2|3|door_status|0|door_open|0|door_close|0|"
					                   +"Power|10002|PowerSwitch|HSOA_3|9|switch_status|0|switch_on1|0|switch_off1|0|switch_on2|0|switch_off2|0|switch_on3|0|switch_off3|0|switch_on4|0|switch_off4|0|"
		                               +"Sensors|10003|SensorsService|HSOA_1|3|get_temperature|0|get_humidity|0|get_pressure|0|"
					                   +"Camera|10004||cam|2|StartClassifier|0|GetLast|0|"
		                               +"Logic|9090|LogicService|http://service.logic.magisterka.agh.vandut.net/|3|startLogic|0|stopLogic|0|statusLogic|0|"
					                   +"Pyro|10005|PyroService|http://pyro/|1|getTemp|0");
		}

		return HostModel.unmarshall(marshalledHost);
	}

	private void saveHostToProperties(String marshalledHost) {
		SharedPreferences hostsPref = getSharedPreferences(PREFS_HOSTMODELS_FILE, 0);
		SharedPreferences.Editor hostsEditor = hostsPref.edit();
		hostsEditor.putString("host", marshalledHost);
		hostsEditor.commit();
		Log.d(TAG, "New host saved to preferences");
		HostModel hostModel = HostModel.unmarshall(marshalledHost);
		setHostAdapter(hostModel);
	}
	
	private ArrayAdapter<String> createAdapterFromHostModel(HostModel hostModel) {
		List<String> items = new ArrayList<String>();
		for(ServiceModel s : hostModel.getServices()) {
			for(MethodModel m : s.getMethods()) {
				items.add(makeListMethodString(hostModel, s, m));
			}
		}
		return new ArrayAdapter<String>(
			      this,
			      android.R.layout.simple_list_item_1,
			      items);
	}
	
	private String makeListMethodString(HostModel h, ServiceModel s, MethodModel m) {
		return String.format("%s.%s()", s.getName(), m.getName());
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		HostModel hostModel = getHostModelFromPreferences();
		MethodModel method = getModelFromPosition(hostModel, position);
		AlertDialog dialog = getMethodSendDialog(hostModel, method);
		if(dialog != null) {
			dialog.show();
		}
	}

	private View inflateLayout(int laoutId) {
		return (View) getLayoutInflater().inflate(laoutId, null);
	}
	
	private AlertDialog getMethodSendDialog(final HostModel hostModel, final MethodModel method) {
		if (method.getArgumentCount() == 0) {
			sentSoapMethod(hostModel, method, Collections.<String, String>emptyMap());
			return null;
		}
		final View view = inflateLayout(R.layout.alert_dialog_method_args);
		EditText editTextArgName1 = (EditText) view.findViewById(R.id.editTextArgName1);
		EditText editTextArgName2 = (EditText) view.findViewById(R.id.editTextArgName2);
		if (method.getArgumentCount() > 0) {
			editTextArgName1.setText(method.getArguments().get(0));
		}
		if (method.getArgumentCount() > 1) {
			editTextArgName2.setText(method.getArguments().get(1));
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(method.getName() + "()").setCancelable(true)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Map<String, String> args = getMethodArgsFromDialog(view);
						dialog.dismiss();
						sentSoapMethod(hostModel, method, args);
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				}).setView(view);
		return builder.create();
	}
	
	private Map<String, String> getMethodArgsFromDialog(View view) {
		String editTextArgName1  = ((EditText) view.findViewById(R.id.editTextArgName1) ).getEditableText().toString();
		String editTextArgValue1 = ((EditText) view.findViewById(R.id.editTextArgValue1)).getEditableText().toString();
		String editTextArgName2  = ((EditText) view.findViewById(R.id.editTextArgName2) ).getEditableText().toString();
		String editTextArgValue2 = ((EditText) view.findViewById(R.id.editTextArgValue2)).getEditableText().toString();
		Map<String, String> map = new HashMap<String, String>();
		if(editTextArgName1.length() > 0) {
			map.put(editTextArgName1, editTextArgValue1);
		}
		if(editTextArgName2.length() > 0) {
			map.put(editTextArgName2, editTextArgValue2);
		}
		return map;
	}
	
	private void sentSoapMethod(HostModel hostModel, MethodModel method, Map<String, String> args) {
		final SoapMethod soapMethod = getSoapMethodFromModel(hostModel, method, args);
		final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, method.getName()
				+ "()", "Sending SOAP. Please wait...", true);
		dialog.setCancelable(true);
		dialog.show();
		final Handler handler = new Handler();
		final AsyncTask<Object, Object, Object> asyncTask = new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object... params) {
				try {
					SoapPrimitive result = soapMethod.call();
					showToast(handler, "Result: "+result.toString(), Toast.LENGTH_LONG);
				} catch (Exception e) {
					Log.e(TAG, "Error while sending SOAP", e);
					showToast(handler, "Error: "+e.getMessage(), Toast.LENGTH_LONG);
				} finally {
					dialog.dismiss();
				}
				return null;
			}
		};
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.cancel();
				asyncTask.cancel(true);
			}
		});
		asyncTask.execute();
	}
	
	private void showToast(Handler handler, final String message, final int length) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(MainActivity.this, message, length).show();
			}
		});
	}

	private SoapMethod getSoapMethodFromModel(HostModel hostModel, MethodModel method, Map<String, String> args) {
		String location = String.format("http://%s:%s/%s/", hostModel.getIpAddress(), method.getService().getPort(), method.getService().getPath());
		Log.i(TAG, "location: "+location);
		SoapService soapService = new SoapService(
				method.getService().getNamespace(),
				location);
		SoapMethod soapMethod = soapService.getSoapMethod(method.getName());
		for(Entry<String, String> e : args.entrySet()) {
			soapMethod.addParameter(e.getKey(), e.getValue());
		}
		return soapMethod;
	}

	private MethodModel getModelFromPosition(HostModel hostModel, int position) {
		int i = 0;
		for (ServiceModel s : hostModel.getServices()) {
			for (MethodModel m : s.getMethods()) {
				if (i++ == position) {
					return m;
				}
			}
		}
		return null;
	}

}
