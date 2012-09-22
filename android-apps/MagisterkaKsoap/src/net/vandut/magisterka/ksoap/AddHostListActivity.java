package net.vandut.magisterka.ksoap;

import java.util.List;

import net.vandut.magisterka.ksoap.data.HostModel;
import net.vandut.magisterka.ksoap.data.MethodModel;
import net.vandut.magisterka.ksoap.data.ServiceModel;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class AddHostListActivity extends ListActivity {

	private static final String TAG = AddHostListActivity.class.getSimpleName();

	public static final String QUERY_MARSHALLED_HOST = "marshalled_host";
	public static final String RESULT_MARSHALLED_HOST = "marshalled_host";

	static final int DIALOG_EDIT_METHOD = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_host);
		HostModel hostModel = null;
		String marshalledHost = getIntent().getStringExtra(QUERY_MARSHALLED_HOST);
		if(marshalledHost != null) {
			hostModel = HostModel.unmarshall(marshalledHost);
		}
		if(hostModel == null) {
			hostModel = new HostModel();
		}
		setListAdapter(new HostModelAdapter(hostModel));
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putString("host", getHostModel().marshall());
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		HostModel host = HostModel.unmarshall(savedInstanceState.getString("host"));
		setListAdapter(new HostModelAdapter(host));
	}

	private View inflateLayout(int laoutId) {
		return (View) getLayoutInflater().inflate(laoutId, null);
	}
	
	private void returnResult() {
		Intent resultIntent = new Intent();
		resultIntent.putExtra(RESULT_MARSHALLED_HOST, getHostModel().marshall());
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}
	
	private void returnCancel() {
		Intent resultIntent = new Intent();
		setResult(Activity.RESULT_CANCELED, resultIntent);
		finish();
	}

	private HostModelAdapter getHostModelAdapter() {
		return (HostModelAdapter) getListAdapter();
	}

	private HostModel getHostModel() {
		return getHostModelAdapter().getHost();
	}

	private void addService() {
		ServiceModel service = new ServiceModel();
		getHostModel().addService(service);

		HostModelAdapter adapter = getHostModelAdapter();
		adapter.notifyDataSetChanged();
		smoothScrollDelayed(adapter.getPositionFromObject(service));
	}

	private void removeService(final ServiceModel service) {
		Runnable removeRunnable = new Runnable() {
			@Override
			public void run() {
				getHostModel().getServices().remove(service);
				getHostModelAdapter().notifyDataSetInvalidated();
			}
		};
		animateRemoveItem(service, removeRunnable);
	}

	private void addMethod(ServiceModel service) {
		MethodModel method = new MethodModel(service);
		service.addMethod(method);

		HostModelAdapter adapter = getHostModelAdapter();
		adapter.notifyDataSetChanged();
		smoothScrollDelayed(adapter.getPositionFromObject(method));
	}

	private void removeMethod(final MethodModel method) {
		Runnable removeRunnable = new Runnable() {
			@Override
			public void run() {
				List<ServiceModel> services = getHostModel().getServices();
				ServiceModel service = services.get(getServiceIndex(method));
				int methodPosition = getHostModelAdapter().getPositionFromObject(method);
				service.getMethods().remove(method);
				getHostModelAdapter().notifyDataSetInvalidated();
				smoothScrollDelayed(methodPosition - 1);
			}
		};
		animateRemoveItem(method, removeRunnable);
	}

	private int getServiceIndex(MethodModel method) {
		return getHostModel().getServices().indexOf(method.getService());
	}

	private int getMethodIndex(MethodModel method) {
		List<ServiceModel> services = getHostModel().getServices();
		ServiceModel service = services.get(getServiceIndex(method));
		return service.getMethods().indexOf(method);
	}

	private void smoothScrollDelayed(final int position) {
		getListView().post(new Runnable() {
			@Override
			public void run() {
				ListView listview = getListView();
				if (listview.getFirstVisiblePosition() > position
						|| listview.getLastVisiblePosition() <= position) {
					listview.smoothScrollToPosition(position);
				}
			}
		});
	}

	private void animateRemoveItem(Object data, Runnable runnable) {
		Animation anim = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
		anim.setDuration(500);
		int position = getHostModelAdapter().getPositionFromObject(data);
		getViewAtPosition(position).startAnimation(anim);
//		if (data instanceof ServiceModel) {
//			for (int i = 0; i < ((ServiceModel) data).getMethodCount(); i++) {
//				getViewAtPosition(position + i + 1).startAnimation(anim);
//			}
//		}
		new Handler().postDelayed(runnable, anim.getDuration());
	}

	private View getViewAtPosition(int position) {
		return getListView().getChildAt(position - getListView().getFirstVisiblePosition());
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog;
		switch (id) {
		case DIALOG_EDIT_METHOD:
			int[] arr = args.getIntArray("index");
			MethodModel method = getHostModel().getServices().get(arr[0]).getMethods().get(arr[1]);
			dialog = new EditMethodDialog(this, method);
			break;

		default:
			dialog = null;
			break;
		}
		return dialog;
	}

	private class HostModelAdapter extends BaseAdapter {

		private HostModel host;

		public HostModelAdapter(HostModel host) {
			this.host = host;
		}

		public HostModel getHost() {
			return host;
		}

		@Override
		public int getCount() {
			int count = 1 + host.getServiceCount();
			for (ServiceModel s : host.getServices()) {
				count += s.getMethodCount();
			}
			return count;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public int getItemViewType(int position) {
			return getItemViewTypeByObject(getItem(position));
		}

		public int getItemViewTypeByObject(Object data) {
			if (data instanceof HostModel)
				return 0;
			if (data instanceof ServiceModel)
				return 1;
			if (data instanceof MethodModel)
				return 2;
			throw new IllegalArgumentException();
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}

		@Override
		public Object getItem(int position) {
			if (position == 0) {
				return host;
			}
			--position;
			for (ServiceModel s : host.getServices()) {
				if (position == 0) {
					return s;
				}
				if (position > s.getMethodCount()) {
					position -= s.getMethodCount() + 1;
				} else {
					return s.getMethods().get(position - 1);
				}
			}
			throw new IllegalArgumentException();
		}

		public int getPositionFromObject(Object data) {
			if (data == host) {
				return 0;
			}
			int position = 0;
			for (ServiceModel s : host.getServices()) {
				++position;
				if (data == s) {
					Log.i(TAG, "getPositionFromObject(), Found service at position: " + position);
					return position;
				}
				for (MethodModel m : s.getMethods()) {
					++position;
					if (data == m) {
						return position;
					}
				}
			}
			return -1;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Object data = getItem(position);
			int itemViewType = getItemViewTypeByObject(data);
			if (convertView == null) {
				switch (itemViewType) {
				case 0:
					convertView = inflateLayout(R.layout.add_host_header);
					convertView.setTag(new HosteRowTag());
					break;
				case 1:
					convertView = inflateLayout(R.layout.add_host_service_row);
					convertView.setTag(new ServiceRowTag());
					break;
				case 2:
					convertView = inflateLayout(R.layout.add_host_method_row);
					break;
				}
			}
			switch (itemViewType) {
			case 0:
				fillHostView(convertView, (HostModel) data);
				break;
			case 1:
				fillServiceView(convertView, (ServiceModel) data);
				break;
			case 2:
				fillMethodView(convertView, (MethodModel) data);
				break;
			}
			return convertView;
		}

		private void fillHostView(View view, final HostModel host) {
			HosteRowTag tag = (HosteRowTag) view.getTag();

			Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);
			Button buttonAddService = (Button) view.findViewById(R.id.buttonAddService);
			Button buttonOK = (Button) view.findViewById(R.id.buttonOK);

			buttonCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					returnCancel();
				}
			});
			buttonAddService.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addService();
				}
			});
			buttonOK.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					returnResult();
				}
			});

			EditText editTextName = (EditText) view.findViewById(R.id.editTextName);
			EditText editTextIpAddress = (EditText) view.findViewById(R.id.editTextIpAddress);

			editTextName.removeTextChangedListener(tag.textWatcherName);
			editTextIpAddress.removeTextChangedListener(tag.textWatcherIpAddress);

			tag.textWatcherName = new AfterTextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					host.setName(s.toString());
				}
			};
			tag.textWatcherIpAddress = new AfterTextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					host.setIpAddress(s.toString());
				}
			};

			editTextName.addTextChangedListener(tag.textWatcherName);
			editTextIpAddress.addTextChangedListener(tag.textWatcherIpAddress);

			editTextName.setText(host.getName());
			editTextIpAddress.setText(host.getIpAddress());
		}

		private void fillServiceView(View view, final ServiceModel service) {
			ServiceRowTag tag = (ServiceRowTag) view.getTag();

			Button removeServiceButton = (Button) view.findViewById(R.id.buttonRemoveService);
			Button buttonAddMethod = (Button) view.findViewById(R.id.buttonAddMethod);

			removeServiceButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					removeService(service);
				}
			});
			buttonAddMethod.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addMethod(service);
				}
			});

			EditText editTextName = (EditText) view.findViewById(R.id.editTextName);
			EditText editTextPort = (EditText) view.findViewById(R.id.editTextPort);
			EditText editTextPath = (EditText) view.findViewById(R.id.editTextPath);
			EditText editTextNamespace = (EditText) view.findViewById(R.id.editTextNamespace);

			editTextName.removeTextChangedListener(tag.textWatcherName);
			editTextPort.removeTextChangedListener(tag.textWatcherPort);
			editTextPath.removeTextChangedListener(tag.textWatcherPath);
			editTextNamespace.removeTextChangedListener(tag.textWatcherNamespace);

			tag.textWatcherName = new AfterTextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					service.setName(s.toString());
				}
			};
			tag.textWatcherPort = new AfterTextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					service.setPort(s.toString());
				}
			};
			tag.textWatcherPath = new AfterTextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					service.setPath(s.toString());
				}
			};
			tag.textWatcherNamespace = new AfterTextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					service.setNamespace(s.toString());
				}
			};

			editTextName.addTextChangedListener(tag.textWatcherName);
			editTextPort.addTextChangedListener(tag.textWatcherPort);
			editTextPath.addTextChangedListener(tag.textWatcherPath);
			editTextNamespace.addTextChangedListener(tag.textWatcherNamespace);

			editTextName.setText(service.getName());
			editTextPort.setText(service.getPort());
			editTextPath.setText(service.getPath());
			editTextNamespace.setText(service.getNamespace());
		}

		private void fillMethodView(View view, final MethodModel method) {
			Button buttonRemove = (Button) view.findViewById(R.id.buttonRemove);
			Button buttonEdit = (Button) view.findViewById(R.id.buttonEdit);

			buttonRemove.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					removeMethod(method);
				}
			});
			buttonEdit.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle args = new Bundle();
					args.putIntArray("index", new int[] { getServiceIndex(method),
							getMethodIndex(method) });
					removeDialog(DIALOG_EDIT_METHOD);
					showDialog(DIALOG_EDIT_METHOD, args);
				}
			});

			TextView textViewMethodName = (TextView) view.findViewById(R.id.textViewMethodName);
			if (method.getName() != null && method.getName().length() > 0) {
				textViewMethodName.setText(method.getName() + "(..)");
			} else {
				textViewMethodName.setText(null);
			}
		}

	}

	private class EditMethodDialog extends Dialog {

		private final MethodModel method;

		public EditMethodDialog(Context context, MethodModel method) {
			super(context);
			this.method = method;
			setContentView(R.layout.edit_method_dialog);
			setTitle("Edit Method");
			fillView();
		}

		private void fillView() {
			final Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
			final Button buttonOK = (Button) findViewById(R.id.buttonOK);
			final EditText editTextMethodName = (EditText) findViewById(R.id.editTextMethodName);
			final EditText editTextMethodArg1 = (EditText) findViewById(R.id.editTextMethodArg1);
			final EditText editTextMethodArg2 = (EditText) findViewById(R.id.editTextMethodArg2);

			buttonCancel.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					cancel();
				}
			});
			buttonOK.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					method.setName(editTextMethodName.getText().toString());
					String arg1 = editTextMethodArg1.getText().toString();
					String arg2 = editTextMethodArg2.getText().toString();
					method.getArguments().clear();
					if(arg1 != null && !"".equals(arg1)) {
						method.getArguments().add(arg1);
					}
					if(arg2 != null && !"".equals(arg2)) {
						method.getArguments().add(arg2);
					}
					dismiss();
					getHostModelAdapter().notifyDataSetChanged();
				}
			});

			if (method.getName() != null) {
				editTextMethodName.setText(method.getName());
			}
			if (method.getArgumentCount() > 0) {
				editTextMethodArg1.setText(method.getArguments().get(0));
			}
			if (method.getArgumentCount() > 1) {
				editTextMethodArg2.setText(method.getArguments().get(1));
			}
		}
	}

	private abstract class AfterTextWatcher implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	}

	private static class HosteRowTag {
		TextWatcher textWatcherName;
		TextWatcher textWatcherIpAddress;
	}

	private static class ServiceRowTag {
		TextWatcher textWatcherName;
		TextWatcher textWatcherPort;
		TextWatcher textWatcherPath;
		TextWatcher textWatcherNamespace;
	}

}
