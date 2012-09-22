package net.vandut.magisterka.ksoap;

import java.util.HashMap;

import net.vandut.magisterka.ksoap.soap.SoapAction;
import net.vandut.magisterka.ksoap.soap.SoapService;
import net.vandut.magisterka.ksoap.soap.SoapService.SoapMethod;

import org.ksoap2.serialization.SoapPrimitive;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class TestActivity extends Activity {

	private static final String TAG = TestActivity.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println("HELLO FROM TAG="+TAG);
		doSoapAction1();
		doSoapAction2();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void doSoapAction1() {
		SoapAction soapAction = new SoapAction(
				"http://servicemix.apache.org/samples/wsdl-first/types",
				"GetPerson",
				"http://192.168.1.3:8093/PersonService/");
		try {
			Log.i(TAG, "Calling SoapAction1");
			@SuppressWarnings("serial")
			SoapPrimitive result = soapAction.callMethod(new HashMap<String, Object>() {{
			    put("personId", "world");
			}});
			Log.i(TAG, "SoapAction1, Result: " + result.toString());
		} catch (Exception e) {
			Log.e(TAG, "Error while calling WS", e);
		}
	}
	
	public void doSoapAction2() {
		SoapService soapService = new SoapService(
				"http://servicemix.apache.org/samples/wsdl-first/types",
				"http://192.168.1.3:8093/PersonService/");
		SoapMethod soapMethod = soapService.getSoapMethod("GetPerson");
		soapMethod.addParameter("personId", "world");
		try {
			Log.i(TAG, "Calling SoapAction2");
			SoapPrimitive result = soapMethod.call();
			Log.i(TAG, "SoapAction2, Result: " + result.toString());
		} catch (Exception e) {
			Log.e(TAG, "Error while calling WS", e);
		}
	}

}
