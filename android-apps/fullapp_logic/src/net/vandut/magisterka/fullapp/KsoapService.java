package net.vandut.magisterka.fullapp;

import java.util.ArrayList;
import java.util.List;

import net.vandut.magisterka.ksoap.soap.SoapService.SoapMethod;

import org.ksoap2.serialization.SoapPrimitive;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

public class KsoapService extends Service {
	
	private final List<ChangeListener> listeners = new ArrayList<ChangeListener>();
	
	private final IBinder binder = new LocalBinder();
	
	boolean serviceRunning = false;
	private Handler uiHandler;
	
	static KsoapService ksoapService;

	@Override
	public void onCreate() {
		super.onCreate();
		ksoapService = this;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ksoapService = null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
    public class LocalBinder extends Binder {
    	KsoapService getService() {
            return KsoapService.this;
        }
    }
    
    public boolean isServiceRunning() {
    	return serviceRunning;
    }
    
    public void registerChangeListener(ChangeListener listener) {
    	listeners.add(listener);
    }
    
    public void unregisterChangeListener(ChangeListener listener) {
    	listeners.remove(listener);
    }
    
    protected void informListenersOnChange(SoapMethod method) {
    	for(ChangeListener chl : listeners) {
    		chl.onKsoapServiceChanged(serviceRunning, method);
    	}
    }
	
	public interface ChangeListener {
		void onKsoapServiceChanged(boolean running, SoapMethod method);
	}
	
	public void callKsoap(SoapMethod method, boolean silent) {
		uiHandler = new Handler(Looper.getMainLooper());
		serviceRunning = true;
		informListenersOnChange(null);
		callMethod(method, silent);
	}
	
	private void callFinished(final SoapPrimitive result, final Exception exc, final boolean silent, final SoapMethod method) {
		serviceRunning = false;
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				if(!silent) {
					if (exc != null) {
						Toast.makeText(KsoapService.this, exc.getMessage(), Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(KsoapService.this, result.toString(), Toast.LENGTH_LONG).show();
					}
				}
				informListenersOnChange(method);
			}
		});
	}

	private void callMethod(final SoapMethod method, final boolean silent) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				SoapPrimitive result = null;
				Exception exc = null;
				try {
					result = method.call();
				} catch (Exception e) {
					exc = e;
				} finally {
					callFinished(result, exc, silent, method);
				}
			}
		}).start();
	}

}
