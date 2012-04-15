/****************************************************************************
 * Copyright (c) 2009 Composent, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Composent, Inc. - initial API and implementation
 *****************************************************************************/
/****************************************************************************
 * Copyright (c) 2009 Composent, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Composent, Inc. - initial API and implementation
 *****************************************************************************/
package net.vandut.agh.magisterka.sample.remoteservice.host;

import java.util.Properties;

import net.vandut.agh.magisterka.sample.remoteservice.IHello;

import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator, IDistributionConstants {

	private static final String DEFAULT_CONTAINER_TYPE = "ecf.generic.server";
	private static final String DEFAULT_CONTAINER_ID = "ecftcp://localhost:3787/server";

	private ServiceRegistration helloRegistration;

	public void start(BundleContext context) throws Exception {
		registerHelloRemoteService(context);
	}

	public void stop(BundleContext context) throws Exception {
		unregisterHelloRemoteService();
	}

	public void registerHelloRemoteService(BundleContext bundleContext) {
		Properties props = new Properties();
		props.put(IDistributionConstants.SERVICE_EXPORTED_INTERFACES, IDistributionConstants.SERVICE_EXPORTED_INTERFACES_WILDCARD);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONFIGS, DEFAULT_CONTAINER_TYPE);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONTAINER_FACTORY_ARGUMENTS, DEFAULT_CONTAINER_ID);
		helloRegistration = bundleContext.registerService(IHello.class.getName(), new Hello(), props);
		System.out.println("Host: Hello Service Registered");
	}

	public void unregisterHelloRemoteService() {
		if (helloRegistration != null) {
			helloRegistration.unregister();
			helloRegistration = null;
			System.out.println("Host: Hello Remote Service Unregistered");
		}
	}

}
