package %PACKAGE%.internal;

import org.apache.log4j.Logger;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public abstract class ServiceWatcher<T extends Object> implements ServiceTrackerCustomizer {

	private static final Logger logger = Logger.getLogger(ServiceWatcher.class);

	private BundleContext context;

	private boolean registered = false;
	
	private T service = null;

	@SuppressWarnings("unchecked")
	public Object addingService(ServiceReference reference) {
		if (context == null) {
			throw new IllegalStateException("BundleContext is null");
		}
		service = (T) context.getService(reference);
		logger.info(String.format("Service %s registered", service.getClass().getName()));
		registered = true;
		onServiceFound(reference, service);
		return service;
	}

	public void modifiedService(ServiceReference reference, Object service) {
	}

	@SuppressWarnings("unchecked")
	public void removedService(ServiceReference reference, Object service) {
		this.service = null;
		registered = false;
		logger.info(String.format("Service %s unregistered", service.getClass().getName()));
		onServiceLost(reference, (T) service);
	}

	public boolean isRegistered() {
		return registered;
	}

	abstract public void onServiceFound(ServiceReference reference, T service);

	abstract public void onServiceLost(ServiceReference reference, T service);

	public BundleContext getContext() {
		return context;
	}

	public void setContext(BundleContext context) {
		this.context = context;
	}

	public T getService() {
		if(!registered) {
			throw new IllegalStateException("Service is not registered");
		}
		return service;
	}

}
