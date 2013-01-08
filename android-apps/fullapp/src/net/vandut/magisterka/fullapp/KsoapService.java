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
	
	private boolean serviceRunning = false;
	private Handler uiHandler;

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
    
    private void informListenersOnChange() {
    	for(ChangeListener chl : listeners) {
    		chl.onKsoapServiceChanged(serviceRunning);
    	}
    }
	
	public interface ChangeListener {
		void onKsoapServiceChanged(boolean running);
	}
	
	public void callKsoap(SoapMethod method) {
		uiHandler = new Handler(Looper.getMainLooper());
		serviceRunning = true;
		informListenersOnChange();
		callMethod(method);
	}
	
	private void callFinished(final SoapPrimitive result, final Exception exc) {
		serviceRunning = false;
		uiHandler.post(new Runnable() {
			@Override
			public void run() {
				if (exc != null) {
					Toast.makeText(KsoapService.this, exc.getMessage(), Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(KsoapService.this, result.toString(), Toast.LENGTH_LONG).show();
				}
				informListenersOnChange();
			}
		});
	}

	private void callMethod(final SoapMethod method) {
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
					callFinished(result, exc);
				}
			}
		}).start();
	}

}
