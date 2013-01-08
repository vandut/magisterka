package net.vandut.magisterka.fullapp;

import net.vandut.magisterka.fullapp.KsoapService.ChangeListener;
import net.vandut.magisterka.ksoap.soap.SoapService.SoapMethod;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class KsoapFragment extends RoboFragment implements KsoapService.ChangeListener {
	
	@InjectView(R.id.textViewActionStatusValue) TextView textViewActionStatusValue;
	@InjectView(R.id.progressBarKsoapStatus)    ProgressBar progressBarKsoapStatus;
	
	private KsoapService.ChangeListener activityChangeListener;

	KsoapService mService;
	boolean bound = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.ksoap_fragment_layout, container,
				false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			activityChangeListener = (ChangeListener) activity;
		} catch(ClassCastException e) {
			// do nothing
		}
	}

	@Override
	public void onStart() {
		super.onStart();

		Intent intent = new Intent(getActivity(), KsoapService.class);
		getActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onStop() {
		super.onStop();

		if (bound) {
			mService.unregisterChangeListener(this);
			getActivity().unbindService(connection);
			bound = false;
		}
	}

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			KsoapService.LocalBinder binder = (KsoapService.LocalBinder) service;
			mService = binder.getService();
			bound = true;
			mService.registerChangeListener(KsoapFragment.this);
			onKsoapServiceChanged(mService.isServiceRunning());
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
			bound = false;
		}

	};

	@Override
	public void onKsoapServiceChanged(boolean running) {
		if(running) {
			textViewActionStatusValue.setText(R.string.ksoap_action_value_running);
			progressBarKsoapStatus.setVisibility(View.VISIBLE);
		} else {
			textViewActionStatusValue.setText(R.string.ksoap_action_value_idle);
			progressBarKsoapStatus.setVisibility(View.INVISIBLE);
		}
		if(activityChangeListener != null) {
			activityChangeListener.onKsoapServiceChanged(running);
		}
	}
	
	public void deliverKsoapInstruction(SoapMethod method) {
		mService.callKsoap(method);
	}

}
