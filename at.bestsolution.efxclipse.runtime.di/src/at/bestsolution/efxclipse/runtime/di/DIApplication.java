/*******************************************************************************
 * Copyright (c) 2012 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tom Schindl<tom.schindl@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.efxclipse.runtime.di;

import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

import at.bestsolution.efxclipse.runtime.application.AbstractJFXApplication;

@SuppressWarnings({ "restriction", "deprecation" })
public class DIApplication extends AbstractJFXApplication implements IExecutableExtension {
	private String bundleName;
	private String applicationClass;
	
	@Override
	protected void jfxStart(IApplicationContext applicationContext,
			Application jfxApplication, Stage primaryStage) {
		System.err.println("DI application is a launching");
		BundleContext context = FrameworkUtil.getBundle(DIApplication.class).getBundleContext();
		ServiceReference<PackageAdmin> ref = context.getServiceReference(PackageAdmin.class);
		PackageAdmin packageAdmin = context.getService(ref);
		System.err.println("Searching for bundle: " + bundleName);
		Bundle[] bundles = packageAdmin.getBundles(bundleName, null);
		if( bundles != null && bundles.length > 0 ) {
			try {
				Class<?> cl = bundles[0].loadClass(applicationClass);
				System.err.println("loaded class: " + cl);
				IEclipseContext eContext = EclipseContextFactory.getServiceContext(context);
				eContext.set(IApplicationContext.class, applicationContext);
				eContext.set(Application.class, jfxApplication);
				eContext.set(Stage.class, primaryStage);
				ContextInjectionFactory.make(cl, eContext);
				System.err.println("Injection is done" );
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.err.println("Unable to locate bundle: " + bundleName);
		}
	}

	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		bundleName = config.getContributor().getName();
		applicationClass = ((Map<String,String>) data).get("mainClass");
	}

}
