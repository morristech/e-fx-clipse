/*******************************************************************************
 * Copyright (c) 2012 BestSolution.at and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Martin Bluehweis<martin.bluehweis@bestsolution.at> - initial API and implementation
 *******************************************************************************/
package at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors;

import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.ANT_TASK__BUILD_DIRECTORY;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.ANT_TASK__CSS_TO_BIN;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.ANT_TASK__DEPLOY;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.ANT_TASK__SIGNJAR;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__APPLICATION;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__EMBEDJNLP;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__EXTENSION;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__HEIGHT;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__INCLUDE_DT;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__INFO;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__NATIVE_PACKAGE;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__OFFLINE_ALLOWED;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__PLACEHOLDERID;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__PLACEHOLDERREF;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__SPLASH_IMAGE;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.DEPLOY__WIDTH;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.SIGN_JAR__ALIAS;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.SIGN_JAR__KEYPASS;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.SIGN_JAR__KEYSTORE;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.SIGN_JAR__STOREPASS;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksPackage.Literals.SIGN_JAR__STORETYPE;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.APPLICATION__MAINCLASS;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.APPLICATION__NAME;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.APPLICATION__PRELOADERCLASS;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.APPLICATION__VERSION;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.ICON__DEPTH;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.ICON__HEIGHT;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.ICON__HREF;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.ICON__KIND;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.ICON__WIDTH;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.INFO__SPLASH;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.INFO__VENDOR;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.PARAM__NAME;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.PARAM__VALUE;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.SPLASH__HREF;
import static at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersPackage.Literals.SPLASH__MODE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.MarkerHelper;
import org.eclipse.emf.common.ui.editor.ProblemEditorPart;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.emf.databinding.IEMFValueProperty;
import org.eclipse.emf.databinding.edit.EMFEditProperties;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.action.EditingDomainActionBarContributor;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.util.EditUIMarkerHelper;
import org.eclipse.emf.edit.ui.util.EditUIUtil;
import org.eclipse.emf.edit.ui.view.ExtendedPropertySheetPage;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.dialogs.MainTypeSelectionDialog;
import org.eclipse.jdt.internal.ui.util.BusyIndicatorRunnableContext;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.internal.ui.wizards.buildpaths.FolderSelectionDialog;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.databinding.viewers.IViewerValueProperty;
import org.eclipse.jface.databinding.viewers.ViewerProperties;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.PropertySheetPage;

import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTask;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.AntTasksFactory;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.Icon;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.IconType;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.Param;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.ParametersFactory;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.Splash;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.model.anttasks.parameters.SplashMode;
import at.bestsolution.efxclipse.tooling.jdt.ui.internal.editors.outline.PropertyContentOutlinePage;

public class JFXEMFBuildConfigurationEditor extends MultiPageEditorPart implements IResourceChangeListener {
	/**
	 * This keeps track of the editing domain that is used to track all changes to the model.
	 */
	private AdapterFactoryEditingDomain editingDomain;
	/**
	 * This is the one adapter factory used for providing views of the model.
	 */
	private ComposedAdapterFactory adapterFactory;
	/**
	 * This is the property sheet page.
	 */
	private PropertySheetPage propertySheetPage;
	/**
	 * This keeps track of the active content viewer, which may be either one of the viewers in the pages or the content outline viewer.
	 */
	private Viewer currentViewer;
	/**
	 * Controls whether the problem indication should be updated.
	 */
	private boolean updateProblemIndication = true;
	/**
	 * Resources that have been removed since last activation.
	 */
	private Collection<Resource> removedResources = new ArrayList<Resource>();
	/**
	 * Resources that have been changed since last activation.
	 */
	private Collection<Resource> changedResources = new ArrayList<Resource>();
	/**
	 * Resources that have been saved.
	 */
	private Collection<Resource> savedResources = new ArrayList<Resource>();
	/**
	 * Properties from old build file.
	 */
	private Properties properties = new Properties();
	/**
	 * The MarkerHelper is responsible for creating workspace resource markers presented.
	 */
	protected MarkerHelper markerHelper = new EditUIMarkerHelper();

	/**
	 * Map to store the diagnostic associated with a resource.
	 */
	protected Map<Resource, Diagnostic> resourceToDiagnosticMap = new LinkedHashMap<Resource, Diagnostic>();

	private PropertyTextEditor editor;

	private FormToolkit toolkit;
	private Form form;

	private static final int DELAY = 500;

	private DataBindingContext dbc;

	/**
	 * This listens to which ever viewer is active. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ISelectionChangedListener selectionChangedListener;

	/**
	 * This keeps track of the selection of the editor as a whole. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ISelection editorSelection = StructuredSelection.EMPTY;

	public JFXEMFBuildConfigurationEditor() {
		super();
		initializeEditingDomain();

		ResourcesPlugin.getWorkspace().addResourceChangeListener( this );
	}

	/**
	 * This sets up the editing domain for the model editor. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void initializeEditingDomain() {
		// Create an adapter factory that yields item providers.
		//
		adapterFactory = new ComposedAdapterFactory( ComposedAdapterFactory.Descriptor.Registry.INSTANCE );

		adapterFactory.addAdapterFactory( new ResourceItemProviderAdapterFactory() );
		// adapterFactory.addAdapterFactory( new AntTasksItemProviderAdapterFactory() );
		// adapterFactory.addAdapterFactory( new ParametersItemProviderAdapterFactory() );
		adapterFactory.addAdapterFactory( new ReflectiveItemProviderAdapterFactory() );

		// Create the command stack that will notify this editor as commands are executed.
		//
		BasicCommandStack commandStack = new BasicCommandStack();

		// Add a listener to set the most recent command's affected objects to be the selection of the viewer with focus.
		//
		commandStack.addCommandStackListener( new CommandStackListener() {
			public void commandStackChanged( final EventObject event ) {
				getContainer().getDisplay().asyncExec( new Runnable() {
					public void run() {
						firePropertyChange( IEditorPart.PROP_DIRTY );

						// Try to select the affected objects.
						//
						Command mostRecentCommand = ( (CommandStack) event.getSource() ).getMostRecentCommand();
						if ( mostRecentCommand != null ) {
							setSelectionToViewer( mostRecentCommand.getAffectedObjects() );
						}
						if ( propertySheetPage != null && !propertySheetPage.getControl().isDisposed() ) {
							propertySheetPage.refresh();
						}
					}
				} );
			}
		} );

		// Create the editing domain with a special command stack.
		//
		editingDomain = new AdapterFactoryEditingDomain( adapterFactory, commandStack, new HashMap<Resource, Boolean>() );
	}

	/**
	 * This sets the selection into whichever viewer is active. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSelectionToViewer( Collection<?> collection ) {
		final Collection<?> theSelection = collection;
		// Make sure it's okay.
		//
		if ( theSelection != null && !theSelection.isEmpty() ) {
			Runnable runnable = new Runnable() {
				public void run() {
					// Try to select the items in the current content viewer of the editor.
					//
					if ( currentViewer != null ) {
						currentViewer.setSelection( new StructuredSelection( theSelection.toArray() ), true );
					}
				}
			};
			getSite().getShell().getDisplay().asyncExec( runnable );
		}
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply tests the command stack. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isDirty() {
		return ( (BasicCommandStack) editingDomain.getCommandStack() ).isSaveNeeded();
	}

	/**
	 * This is for implementing {@link IEditorPart} and simply saves the model file. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void doSave( IProgressMonitor progressMonitor ) {
		// Save only resources that have actually changed.
		//
		final Map<Object, Object> saveOptions = new HashMap<Object, Object>();
		saveOptions.put( Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER );

		// Do the work within an operation because this is a long running activity that modifies the workbench.
		//
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
			// This is the method that gets invoked when the operation runs.
			//
			@Override
			public void execute( IProgressMonitor monitor ) {
				// Save the resources to the file system.
				//
				boolean first = true;
				for ( Resource resource : editingDomain.getResourceSet().getResources() ) {
					if ( ( first || !resource.getContents().isEmpty() || isPersisted( resource ) ) && !editingDomain.isReadOnly( resource ) ) {
						try {
							long timeStamp = resource.getTimeStamp();
							resource.save( saveOptions );
							if ( resource.getTimeStamp() != timeStamp ) {
								savedResources.add( resource );
							}
						}
						catch ( Exception exception ) {
							resourceToDiagnosticMap.put( resource, analyzeResourceProblems( resource, exception ) );
						}
						first = false;
					}
				}
			}
		};

		updateProblemIndication = false;
		try {
			// This runs the options, and shows progress.
			//
			new ProgressMonitorDialog( getSite().getShell() ).run( true, false, operation );

			// Refresh the necessary state.
			//
			( (BasicCommandStack) editingDomain.getCommandStack() ).saveIsDone();
			firePropertyChange( IEditorPart.PROP_DIRTY );
		}
		catch ( Exception exception ) {
			// TODO log this
			exception.printStackTrace();
		}
		updateProblemIndication = true;
		updateProblemIndication();
	}

	/**
	 * Updates the problems indication with the information described in the specified diagnostic. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void updateProblemIndication() {
		// System.err.println( "houston, we have a problem" );
		if ( updateProblemIndication ) {
			BasicDiagnostic diagnostic = new BasicDiagnostic( Diagnostic.OK, "at.bestsolution.efxclipse.tooling.jdt.ui.editor", 0, null,
					new Object[] { editingDomain.getResourceSet() } );
			for ( Diagnostic childDiagnostic : resourceToDiagnosticMap.values() ) {
				if ( childDiagnostic.getSeverity() != Diagnostic.OK ) {
					diagnostic.add( childDiagnostic );
				}
			}

			int lastEditorPage = getPageCount() - 1;
			if ( lastEditorPage >= 0 && getEditor( lastEditorPage ) instanceof ProblemEditorPart ) {
				( (ProblemEditorPart) getEditor( lastEditorPage ) ).setDiagnostic( diagnostic );
				if ( diagnostic.getSeverity() != Diagnostic.OK ) {
					setActivePage( lastEditorPage );
				}
			}
			else if ( diagnostic.getSeverity() != Diagnostic.OK ) {
				ProblemEditorPart problemEditorPart = new ProblemEditorPart();
				problemEditorPart.setDiagnostic( diagnostic );
				problemEditorPart.setMarkerHelper( markerHelper );
				try {
					addPage( ++lastEditorPage, problemEditorPart, getEditorInput() );
					setPageText( lastEditorPage, problemEditorPart.getPartName() );
					setActivePage( lastEditorPage );
					showTabs();
				}
				catch ( PartInitException exception ) {
					// TODO
					exception.printStackTrace();
				}
			}

			if ( markerHelper.hasMarkers( editingDomain.getResourceSet() ) ) {
				markerHelper.deleteMarkers( editingDomain.getResourceSet() );
				if ( diagnostic.getSeverity() != Diagnostic.OK ) {
					try {
						markerHelper.createMarkers( diagnostic );
					}
					catch ( CoreException exception ) {
						// TODO
						exception.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Returns a diagnostic describing the errors and warnings listed in the resource and the specified exception (if any). <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public Diagnostic analyzeResourceProblems( Resource resource, Exception exception ) {
		if ( !resource.getErrors().isEmpty() || !resource.getWarnings().isEmpty() ) {
			BasicDiagnostic basicDiagnostic = new BasicDiagnostic( Diagnostic.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui.editor", 0,
					"_UI_CreateModelError_message", new Object[] { exception == null ? (Object) resource : exception } );
			basicDiagnostic.merge( EcoreUtil.computeDiagnostic( resource, true ) );
			return basicDiagnostic;
		}
		else if ( exception != null ) {
			return new BasicDiagnostic( Diagnostic.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui.editor", 0, "_UI_CreateModelError_message",
					new Object[] { exception } );
		}
		else {
			return Diagnostic.OK_INSTANCE;
		}
	}

	/**
	 * This returns whether something has been persisted to the URI of the specified resource. The implementation uses the URI converter from the editor's
	 * resource set to try to open an input stream. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected boolean isPersisted( Resource resource ) {
		boolean result = false;
		try {
			InputStream stream = editingDomain.getResourceSet().getURIConverter().createInputStream( resource.getURI() );
			if ( stream != null ) {
				result = true;
				stream.close();
			}
		}
		catch ( IOException e ) {
			// Ignore
		}
		return result;
	}

	/**
	 * This always returns true because it is not currently supported. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * This also changes the editor's input. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void doSaveAs() {
		SaveAsDialog saveAsDialog = new SaveAsDialog( getSite().getShell() );
		saveAsDialog.open();
		IPath path = saveAsDialog.getResult();
		if ( path != null ) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile( path );
			if ( file != null ) {
				doSaveAs( URI.createPlatformResourceURI( file.getFullPath().toString(), true ), new FileEditorInput( file ) );
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void doSaveAs( URI uri, IEditorInput editorInput ) {
		( editingDomain.getResourceSet().getResources().get( 0 ) ).setURI( uri );
		setInputWithNotify( editorInput );
		setPartName( editorInput.getName() );
		IProgressMonitor progressMonitor = getActionBars().getStatusLineManager() != null ? getActionBars().getStatusLineManager().getProgressMonitor()
				: new NullProgressMonitor();
		doSave( progressMonitor );
	}

	public EditingDomainActionBarContributor getActionBarContributor() {
		return (EditingDomainActionBarContributor) getEditorSite().getActionBarContributor();
	}

	public IActionBars getActionBars() {
		return getActionBarContributor().getActionBars();
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener( this );
		dbc.dispose();
		super.dispose();
	}

	@Override
	protected void createPages() {
		createModel();

		// Only creates the other pages if there is something that can be edited
		//
		if ( !editingDomain.getResourceSet().getResources().isEmpty() ) {
			AntTask task = getTask();
			createPageOverview( task );
			createPageDeploy( task );
			createPageSigning( task );
			createPageEditor( task );
		}

		getSite().getShell().getDisplay().asyncExec( new Runnable() {
			public void run() {
				setActivePage( 0 );
			}
		} );
	}

	private AntTask getTask() {
		AntTask task;
		try {
			task = (AntTask) editingDomain.getResourceSet().getResources().get( 0 ).getContents().get( 0 );
		}
		catch ( Exception e ) {
			if ( !properties.isEmpty() ) {
				// This is an old properties file -> transform to EMF/XMI
				task = AntTasksFactory.eINSTANCE.createAntTask();
				task.setBuildDirectory( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.BUILD_DIRECTORY ) ) );

				// TODO extract Manifest entries from properties.

				// Deploy
				task.setDeploy( AntTasksFactory.eINSTANCE.createDeploy() );
				task.getDeploy().setSplashImage(
						properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.BUILD_SPLASH_IMAGE ) ) );

				// Deploy -> Info
				task.getDeploy().setInfo( ParametersFactory.eINSTANCE.createInfo() );
				task.getDeploy().getInfo()
						.setVendor( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.BUILD_VENDOR_NAME ) ) );

				task.getDeploy()
						.setWidth( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.DEPLOY_APPLET_WIDTH ) ) );
				task.getDeploy().setHeight(
						properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.DEPLOY_APPLET_HEIGHT ) ) );

				// TODO Webstart Splash

				// TODO Webstart Icons

				// Deploy -> Application
				task.getDeploy().setApplication( ParametersFactory.eINSTANCE.createApplication() );
				task.getDeploy().getApplication()
						.setName( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.BUILD_APP_TITLE ) ) );
				task.getDeploy().getApplication()
						.setVersion( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.BUILD_APP_VERSION ) ) );
				task.getDeploy()
						.getApplication()
						.setMainclass( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.BUILD_APPLICATION_CLASS ) ) );
				task.getDeploy()
						.getApplication()
						.setPreloaderclass(
								properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.BUILD_PRELOADER_CLASS ) ) );

				// SignJar
				task.setSignjar( AntTasksFactory.eINSTANCE.createSignJar() );
				task.getSignjar().setKeystore( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.SIGN_KEYSTORE ) ) );
				task.getSignjar().setStorepass( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.SIGN_PASSWORD ) ) );
				task.getSignjar().setAlias( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.SIGN_ALIAS ) ) );
				task.getSignjar()
						.setKeypass( properties.getProperty( JFXBuildConfigurationEditor.MAPPING.get( JFXBuildConfigurationEditor.SIGN_KEYPASSWOARD ) ) );

				for ( Object o : properties.keySet() ) {
					System.err.println( o + "|" + properties.get( o ) );
				}
				editingDomain.getResourceSet().getResources().get( 0 ).getContents().add( 0, task );
			}
			else {
				throw new UnsupportedOperationException( "Could not read file" );
			}
		}
		return task;
	}

	/**
	 * This is the method called to load a resource into the editing domain's resource set based on the editor's input. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private void createModel() {
		URI resourceURI = EditUIUtil.getURI( getEditorInput() );
		Exception exception = null;
		Resource resource = null;
		try {
			// Load the resource through the editing domain.
			//
			resource = editingDomain.getResourceSet().getResource( resourceURI, true );
		}
		catch ( Exception e ) {
			exception = e;
			resource = editingDomain.getResourceSet().getResource( resourceURI, false );
		}

		Diagnostic diagnostic = analyzeResourceProblems( resource, exception );
		if ( diagnostic.getSeverity() != Diagnostic.OK ) {
			resourceToDiagnosticMap.put( resource, analyzeResourceProblems( resource, exception ) );
		}
		editingDomain.getResourceSet().eAdapters().add( problemIndicationAdapter );
	}

	/**
	 * Adapter used to update the problem indication when resources are demanded loaded. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected EContentAdapter problemIndicationAdapter = new EContentAdapter() {
		@Override
		public void notifyChanged( Notification notification ) {
			if ( notification.getNotifier() instanceof Resource ) {
				switch ( notification.getFeatureID( Resource.class ) ) {
				case Resource.RESOURCE__IS_LOADED:
				case Resource.RESOURCE__ERRORS:
				case Resource.RESOURCE__WARNINGS: {
					Resource resource = (Resource) notification.getNotifier();
					Diagnostic diagnostic = analyzeResourceProblems( resource, null );
					if ( diagnostic.getSeverity() != Diagnostic.OK ) {
						resourceToDiagnosticMap.put( resource, diagnostic );
					}
					else {
						resourceToDiagnosticMap.remove( resource );
					}

					if ( updateProblemIndication ) {
						getSite().getShell().getDisplay().asyncExec( new Runnable() {
							public void run() {
								updateProblemIndication();
							}
						} );
					}
					break;
				}
				}
			}
			else {
				super.notifyChanged( notification );
			}
		}

		@Override
		protected void setTarget( Resource target ) {
			basicSetTarget( target );
		}

		@Override
		protected void unsetTarget( Resource target ) {
			basicUnsetTarget( target );
		}
	};

	@Override
	public void init( IEditorSite site, IEditorInput editorInput ) throws PartInitException {
		if ( !( editorInput instanceof IFileEditorInput ) )
			throw new PartInitException( "Invalid Input: Must be IFileEditorInput" );
		super.init( site, editorInput );
		try {
			IFileEditorInput i = (IFileEditorInput) editorInput;
			properties.load( i.getFile().getContents() );
		}
		catch ( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch ( CoreException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createPageOverview( final AntTask task ) {
		Composite composite = new Composite( getContainer(), SWT.NONE );
		FillLayout layout = new FillLayout();
		composite.setLayout( layout );

		// TODO
		final WritableValue bean = new WritableValue();
		bean.setValue( task );

		toolkit = new FormToolkit( composite.getDisplay() );

		form = toolkit.createForm( composite );
		form.setText( "FX Build Configuration" );
		form.setImage( getTitleImage() );
		form.getBody().setLayout( new FillLayout() );
		toolkit.decorateFormHeading( form );

		IToolBarManager mgr = form.getToolBarManager();
		mgr.add( new Action( "Build & Export FX Application", ImageDescriptor.createFromURL( getClass().getClassLoader().getResource(
				"/icons/exportrunnablejar_wiz.gif" ) ) ) {
			@Override
			public void run() {
				try {
					IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService( IHandlerService.class );
					hs.executeCommand( "at.bestsolution.efxclipse.tooling.jdt.ui.export", null );
				}
				catch ( ExecutionException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotDefinedException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotEnabledException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotHandledException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} );
		// mgr.add(new Action("Export Ant File",ImageDescriptor.createFromURL(getClass().getClassLoader().getResource("/icons/exportAnt_co.gif"))) {
		// @Override
		// public void run() {
		//
		// }
		// });
		form.updateToolBar();

		ScrolledForm scrolledForm = toolkit.createScrolledForm( form.getBody() );
		scrolledForm.getBody().setLayout( new GridLayout( 2, false ) );
		Composite sectionParent = scrolledForm.getBody();

		dbc = new DataBindingContext();
		IWidgetValueProperty textModify = WidgetProperties.text( SWT.Modify );
		IWidgetValueProperty selChange = WidgetProperties.selection();

		{
			Section section = toolkit.createSection( sectionParent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED );
			section.setText( "Build && Package Properties" );
			section.setDescription( "The following properties are needed to build the JavaFX-Application" );
			section.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

			Composite sectionClient = toolkit.createComposite( section );
			sectionClient.setLayout( new GridLayout( 4, false ) );

			{
				toolkit.createLabel( sectionClient, "Build Directory*:" );
				final Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
				toolkit.createButton( sectionClient, "Filesystem ...", SWT.PUSH ).addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected( SelectionEvent e ) {
						String dir = handleBuildFilesystemDirectorySelection( t.getShell() );
						if ( dir != null ) {
							t.setText( dir );
						}
					}
				} );
				toolkit.createButton( sectionClient, "Workspace ...", SWT.PUSH ).addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected( SelectionEvent e ) {
						String dir = handleBuildWorkbenchDirectorySelection( t.getShell() );
						if ( dir != null ) {
							t.setText( dir );
						}
					}
				} );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, ANT_TASK__BUILD_DIRECTORY );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Vendor name*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__INFO, INFO__VENDOR ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Application title*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain,
						FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__APPLICATION, APPLICATION__NAME ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Application version*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain,
						FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__APPLICATION, APPLICATION__VERSION ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Application class*:" );
				final Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				Button b = toolkit.createButton( sectionClient, "Browse ...", SWT.PUSH );
				b.addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected( SelectionEvent e ) {
						String name = handleRootclassSelection( t.getShell() );
						if ( name != null ) {
							t.setText( name );
						}
					}
				} );
				b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, false, false ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain,
						FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__APPLICATION, APPLICATION__MAINCLASS ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Preloader class:" );
				final Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				Button b = toolkit.createButton( sectionClient, "Browse ...", SWT.PUSH );
				b.addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected( SelectionEvent e ) {
						String name = handlePreloaderclassSelection( t.getShell() );
						if ( name != null ) {
							t.setText( name );
						}
					}
				} );
				b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, false, false ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain,
						FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__APPLICATION, APPLICATION__PRELOADERCLASS ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Splash:" );
				final Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				Button b = toolkit.createButton( sectionClient, "Browse ...", SWT.PUSH );
				b.addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected( SelectionEvent e ) {
						String name = handleSplashImage( t.getShell() );
						if ( name != null ) {
							t.setText( name );
						}
					}
				} );
				b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, false, false ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__SPLASH_IMAGE ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Manifest-Attributes:" )
						.setLayoutData( new GridData( GridData.BEGINNING, GridData.BEGINNING, false, false ) );
				Composite tableContainer = toolkit.createComposite( sectionClient );
				tableContainer.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				GridLayout gl = new GridLayout( 2, false );
				// gl.marginBottom = gl.marginHeight = gl.marginLeft = gl.marginRight = gl.marginTop = gl.marginWidth = 0;
				tableContainer.setLayout( gl );
				// tableContainer.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

				Table t = toolkit.createTable( tableContainer, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL );
				t.setHeaderVisible( true );
				t.setLinesVisible( true );

				final TableViewer v = new TableViewer( t );
				GridData gd = new GridData( GridData.FILL_HORIZONTAL );
				gd.heightHint = t.getItemHeight() * 5;
				v.getControl().setLayoutData( gd );
				v.setContentProvider( ArrayContentProvider.getInstance() );

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Param) element ).getName();
						}
					} );
					c.getColumn().setWidth( 100 );
					c.getColumn().setText( "Name" );
				}

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Param) element ).getValue();
						}
					} );
					c.getColumn().setWidth( 300 );
					c.getColumn().setText( "Value" );
				}

				v.setInput( task.getManifestEntries() );

				Composite buttonComp = toolkit.createComposite( sectionClient );
				buttonComp.setLayoutData( new GridData( GridData.BEGINNING, GridData.END, false, false ) );
				buttonComp.setLayout( new GridLayout() );

				{
					Button b = toolkit.createButton( buttonComp, "Add ...", SWT.PUSH );
					b.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, false, false ) );
					b.addSelectionListener( new SelectionAdapter() {
						@Override
						public void widgetSelected( final SelectionEvent e ) {
							if ( handleAddManifestAttr( getSite().getShell() ) ) {
								v.setInput( task.getManifestEntries() );
							}
						}
					} );
				}

				{
					Button b = toolkit.createButton( buttonComp, "Remove", SWT.PUSH );
					b.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, false, false ) );
					b.addSelectionListener( new SelectionAdapter() {
						@Override
						public void widgetSelected( final SelectionEvent e ) {
							Param value = (Param) ( (IStructuredSelection) v.getSelection() ).getFirstElement();
							if ( v != null ) {
								if ( handleRemoveManifestAttr( value ) ) {
									v.setInput( task.getManifestEntries() );
								}
							}
						}
					} );
				}
				{
					Button b = toolkit.createButton( sectionClient, "Convert CSS into binary form", SWT.CHECK );
					b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__CSS_TO_BIN ) );
					dbc.bindValue( selChange.observe( b ), prop.observeDetail( bean ) );
				}

			}

			section.setClient( sectionClient );
		}

		{
			Section section = toolkit.createSection( sectionParent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED );
			section.setText( "Building & Exporting" );
			section.setLayoutData( new GridData( GridData.FILL, GridData.FILL, false, true, 1, 2 ) );

			Composite sectionClient = toolkit.createComposite( section );
			sectionClient.setLayout( new GridLayout( 1, false ) );

			{
				FormText text = toolkit.createFormText( sectionClient, false );
				text.setText(
						"<p>To generate build instructions and export the project: <li style=\"bullet\" bindent=\"1\">Generate <a href=\"generateAnt\">ant build.xml</a> only</li><li style=\"bullet\" bindent=\"2\">Generate <a href=\"generateAndRun\">ant build.xml and run</a></li>&#160;</p>",
						true, false );
				text.addHyperlinkListener( new IHyperlinkListener() {

					@Override
					public void linkExited( final HyperlinkEvent e ) {

					}

					@Override
					public void linkEntered( HyperlinkEvent e ) {
					}

					@Override
					public void linkActivated( HyperlinkEvent e ) {
						try {
							if ( "generateAndRun".equals( e.getHref() ) ) {
								IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService( IHandlerService.class );
								hs.executeCommand( "at.bestsolution.efxclipse.tooling.jdt.ui.export", null );
							}
							else {
								IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService( IHandlerService.class );
								hs.executeCommand( "at.bestsolution.efxclipse.tooling.jdt.ui.generateAnt", null );
							}
						}
						catch ( Exception e1 ) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} );
			}

			section.setClient( sectionClient );
		}

		int index = addPage( composite );
		setPageText( index, "Overview" );
	}

	private void createPageDeploy( final AntTask task ) {
		Composite composite = new Composite( getContainer(), SWT.NONE );
		FillLayout layout = new FillLayout();
		composite.setLayout( layout );

		// TODO
		final WritableValue bean = new WritableValue();
		bean.setValue( task );

		toolkit = new FormToolkit( composite.getDisplay() );

		form = toolkit.createForm( composite );
		form.setText( "FX Build Configuration" );
		form.setImage( getTitleImage() );
		form.getBody().setLayout( new FillLayout() );
		toolkit.decorateFormHeading( form );

		IToolBarManager mgr = form.getToolBarManager();
		mgr.add( new Action( "Build & Export FX Application", ImageDescriptor.createFromURL( getClass().getClassLoader().getResource(
				"/icons/exportrunnablejar_wiz.gif" ) ) ) {
			@Override
			public void run() {
				try {
					IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService( IHandlerService.class );
					hs.executeCommand( "at.bestsolution.efxclipse.tooling.jdt.ui.export", null );
				}
				catch ( ExecutionException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotDefinedException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotEnabledException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotHandledException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} );
		// mgr.add(new Action("Export Ant File",ImageDescriptor.createFromURL(getClass().getClassLoader().getResource("/icons/exportAnt_co.gif"))) {
		// @Override
		// public void run() {
		//
		// }
		// });
		form.updateToolBar();

		ScrolledForm scrolledForm = toolkit.createScrolledForm( form.getBody() );
		scrolledForm.getBody().setLayout( new GridLayout( 2, false ) );
		Composite sectionParent = scrolledForm.getBody();

		dbc = new DataBindingContext();
		IWidgetValueProperty textModify = WidgetProperties.text( SWT.Modify );
		IWidgetValueProperty selChange = WidgetProperties.selection();

		{
			Section section = toolkit.createSection( sectionParent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED );
			section.setText( "Deploy Properties" );
			section.setDescription( "The following properties are needed to create a Java Webstart Deployment" );
			section.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

			Composite sectionClient = toolkit.createComposite( section );
			sectionClient.setLayout( new GridLayout( 2, false ) );

			{
				toolkit.createLabel( sectionClient, "Applet Width*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__WIDTH ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Applet Height*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__HEIGHT ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				Button b = toolkit.createButton( sectionClient, "Embed JNLP", SWT.CHECK );
				b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__EMBEDJNLP ) );
				dbc.bindValue( selChange.observe( b ), prop.observeDetail( bean ) );
			}

			{
				Button b = toolkit.createButton( sectionClient, "Treat files as extensions", SWT.CHECK );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__EXTENSION ) );
				dbc.bindValue( selChange.observe( b ), prop.observeDetail( bean ) );
			}

			{
				Button b = toolkit.createButton( sectionClient, "Include deployment toolkit", SWT.CHECK );
				b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__INCLUDE_DT ) );
				dbc.bindValue( selChange.observe( b ), prop.observeDetail( bean ) );
			}

			{
				Button b = toolkit.createButton( sectionClient, "Native Package", SWT.CHECK );
				b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__NATIVE_PACKAGE ) );
				dbc.bindValue( selChange.observe( b ), prop.observeDetail( bean ) );
			}

			{
				Button b = toolkit.createButton( sectionClient, "Offline allowed", SWT.CHECK );
				b.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 2, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__OFFLINE_ALLOWED ) );
				dbc.bindValue( selChange.observe( b ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Placeholder Ref.*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__PLACEHOLDERREF ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Placeholder ID*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__PLACEHOLDERID ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Webstart Splash:" ).setLayoutData( new GridData( GridData.BEGINNING, GridData.BEGINNING, false, false ) );
				Composite container = toolkit.createComposite( sectionClient );
				GridLayout gl = new GridLayout( 2, false );
				gl.marginBottom = gl.marginHeight = gl.marginLeft = gl.marginRight = gl.marginTop = gl.marginWidth = 0;
				container.setLayout( gl );
				container.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

				Table t = toolkit.createTable( container, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL );
				t.setHeaderVisible( true );
				t.setLinesVisible( true );

				final TableViewer v = new TableViewer( t );
				GridData gd = new GridData( GridData.FILL_HORIZONTAL );
				gd.heightHint = t.getItemHeight() * 5;
				v.getControl().setLayoutData( gd );
				v.setContentProvider( ArrayContentProvider.getInstance() );

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Splash) element ).getMode().getName();
						}
					} );
					c.getColumn().setWidth( 100 );
					c.getColumn().setText( "Mode" );
				}

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Splash) element ).getHref();
						}
					} );
					c.getColumn().setWidth( 300 );
					c.getColumn().setText( "URL" );
				}

				v.setInput( task.getDeploy().getInfo().getSplash() );

				Composite buttonComp = toolkit.createComposite( container );
				buttonComp.setLayoutData( new GridData( GridData.BEGINNING, GridData.END, false, false ) );
				buttonComp.setLayout( new GridLayout() );

				{
					Button b = toolkit.createButton( buttonComp, "Add ...", SWT.PUSH );
					b.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, false, false ) );
					b.addSelectionListener( new SelectionAdapter() {
						@Override
						public void widgetSelected( SelectionEvent e ) {
							if ( handleAddSplash() ) {
								v.setInput( task.getDeploy().getInfo().getSplash() );
							}
						}
					} );
				}

				{
					Button b = toolkit.createButton( buttonComp, "Remove", SWT.PUSH );
					b.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, false, false ) );
					b.addSelectionListener( new SelectionAdapter() {
						@Override
						public void widgetSelected( SelectionEvent e ) {
							Splash value = (Splash) ( (IStructuredSelection) v.getSelection() ).getFirstElement();
							if ( v != null ) {
								if ( handleRemoveSplash( value ) ) {
									v.setInput( task.getDeploy().getInfo().getSplash() );
								}
							}
						}
					} );
				}
			}

			{
				toolkit.createLabel( sectionClient, "Webstart Icons:" ).setLayoutData( new GridData( GridData.BEGINNING, GridData.BEGINNING, false, false ) );
				Composite container = toolkit.createComposite( sectionClient );
				GridLayout gl = new GridLayout( 2, false );
				gl.marginBottom = gl.marginHeight = gl.marginLeft = gl.marginRight = gl.marginTop = gl.marginWidth = 0;
				container.setLayout( gl );
				container.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

				Table t = toolkit.createTable( container, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL );
				t.setHeaderVisible( true );
				t.setLinesVisible( true );

				final TableViewer v = new TableViewer( t );
				GridData gd = new GridData( GridData.FILL_HORIZONTAL );
				gd.heightHint = t.getItemHeight() * 5;
				v.getControl().setLayoutData( gd );
				v.setContentProvider( ArrayContentProvider.getInstance() );

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Icon) element ).getDepth();
						}
					} );
					c.getColumn().setWidth( 50 );
					c.getColumn().setText( "Depth" );
				}

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Icon) element ).getKind().getName();
						}
					} );
					c.getColumn().setWidth( 50 );
					c.getColumn().setText( "Kind" );
				}

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Icon) element ).getWidth();
						}
					} );
					c.getColumn().setWidth( 50 );
					c.getColumn().setText( "Width" );
				}

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Icon) element ).getHeight();
						}
					} );
					c.getColumn().setWidth( 50 );
					c.getColumn().setText( "Height" );
				}

				{
					TableViewerColumn c = new TableViewerColumn( v, SWT.NONE );
					c.setLabelProvider( new ColumnLabelProvider() {
						@Override
						public String getText( Object element ) {
							return ( (Icon) element ).getHref();
						}
					} );
					c.getColumn().setWidth( 50 );
					c.getColumn().setText( "Url" );
				}

				v.setInput( task.getDeploy().getInfo().getIcon() );

				Composite buttonComp = toolkit.createComposite( container );
				buttonComp.setLayoutData( new GridData( GridData.BEGINNING, GridData.END, false, false ) );
				buttonComp.setLayout( new GridLayout() );

				{
					Button b = toolkit.createButton( buttonComp, "Add ...", SWT.PUSH );
					b.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, false, false ) );
					b.addSelectionListener( new SelectionAdapter() {
						@Override
						public void widgetSelected( final SelectionEvent e ) {
							if ( handleAddIcon() ) {
								v.setInput( task.getDeploy().getInfo().getIcon() );
							}
						}
					} );
				}

				{
					Button b = toolkit.createButton( buttonComp, "Remove", SWT.PUSH );
					b.setLayoutData( new GridData( GridData.FILL, GridData.BEGINNING, false, false ) );
					b.addSelectionListener( new SelectionAdapter() {
						@Override
						public void widgetSelected( final SelectionEvent e ) {
							Icon value = (Icon) ( (IStructuredSelection) v.getSelection() ).getFirstElement();
							if ( v != null ) {
								if ( handleRemoveIcon( value ) ) {
									v.setInput( task.getDeploy().getInfo().getIcon() );
								}
							}
						}
					} );
				}
			}

			section.setClient( sectionClient );
		}

		int index = addPage( composite );
		setPageText( index, "Deploy" );
	}

	private void createPageSigning( AntTask task ) {
		Composite composite = new Composite( getContainer(), SWT.NONE );
		FillLayout layout = new FillLayout();
		composite.setLayout( layout );

		// TODO
		final WritableValue bean = new WritableValue();
		bean.setValue( task );

		toolkit = new FormToolkit( composite.getDisplay() );

		form = toolkit.createForm( composite );
		form.setText( "FX Build Configuration" );
		form.setImage( getTitleImage() );
		form.getBody().setLayout( new FillLayout() );
		toolkit.decorateFormHeading( form );

		IToolBarManager mgr = form.getToolBarManager();
		mgr.add( new Action( "Build & Export FX Application", ImageDescriptor.createFromURL( getClass().getClassLoader().getResource(
				"/icons/exportrunnablejar_wiz.gif" ) ) ) {
			@Override
			public void run() {
				try {
					IHandlerService hs = (IHandlerService) PlatformUI.getWorkbench().getService( IHandlerService.class );
					hs.executeCommand( "at.bestsolution.efxclipse.tooling.jdt.ui.export", null );
				}
				catch ( ExecutionException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotDefinedException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotEnabledException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch ( NotHandledException e ) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} );
		form.updateToolBar();

		ScrolledForm scrolledForm = toolkit.createScrolledForm( form.getBody() );
		scrolledForm.getBody().setLayout( new GridLayout( 2, false ) );
		Composite sectionParent = scrolledForm.getBody();

		dbc = new DataBindingContext();
		IWidgetValueProperty textModify = WidgetProperties.text( SWT.Modify );
		// IWidgetValueProperty selChange = WidgetProperties.selection();

		{
			Section section = toolkit.createSection( sectionParent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED );
			section.setText( "Signing Properties" );
			section.setDescription( "Information for signing result jar" );
			section.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );

			Composite sectionClient = toolkit.createComposite( section );
			sectionClient.setLayout( new GridLayout( 4, false ) );

			{
				toolkit.createLabel( sectionClient, "Alias*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__SIGNJAR, SIGN_JAR__ALIAS ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Key-Password*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__SIGNJAR, SIGN_JAR__KEYPASS ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Keystore*:" );
				final Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
				toolkit.createButton( sectionClient, "Filesystem ...", SWT.PUSH ).addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected( final SelectionEvent e ) {
						String v = handleKeyStoreFilesystemSelection( t.getShell() );
						if ( v != null ) {
							t.setText( v );
						}
					}
				} );
				toolkit.createButton( sectionClient, "Workspace ...", SWT.PUSH ).addSelectionListener( new SelectionAdapter() {
					@Override
					public void widgetSelected( final SelectionEvent e ) {
						String v = handleKeyStoreWorkspaceSelection( t.getShell() );
						if ( v != null ) {
							t.setText( v );
						}
					}
				} );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__SIGNJAR, SIGN_JAR__KEYSTORE ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Store-Password*:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__SIGNJAR, SIGN_JAR__STOREPASS ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			{
				toolkit.createLabel( sectionClient, "Storetype:" );
				Text t = toolkit.createText( sectionClient, "", SWT.BORDER );
				t.setLayoutData( new GridData( GridData.FILL, GridData.CENTER, true, false, 3, 1 ) );
				IEMFValueProperty prop = EMFEditProperties.value( editingDomain, FeaturePath.fromList( ANT_TASK__SIGNJAR, SIGN_JAR__STORETYPE ) );
				dbc.bindValue( textModify.observeDelayed( DELAY, t ), prop.observeDetail( bean ) );
			}

			section.setClient( sectionClient );

			// TODO missing: fileset
		}

		int index = addPage( composite );
		setPageText( index, "Signing" );
	}

	boolean handleRemoveManifestAttr( final Param value ) {
		if ( MessageDialog.openConfirm( getSite().getShell(), "Confirm delete", "Would really like to remove the selected attribute" ) ) {
			getTask().getManifestEntries().remove( value );
			return true;
		}
		return false;
	}

	protected boolean handleAddManifestAttr( Shell shell ) {
		TitleAreaDialog d = new TitleAreaDialog( shell ) {
			private Param o = ParametersFactory.eINSTANCE.createParam();
			private DataBindingContext dbc = new DataBindingContext();
			private Text tName;

			private Text tValue;

			@Override
			protected Control createDialogArea( Composite parent ) {
				Composite area = (Composite) super.createDialogArea( parent );
				Composite container = new Composite( area, SWT.NONE );
				container.setLayoutData( new GridData( GridData.FILL_BOTH ) );
				container.setLayout( new GridLayout( 2, false ) );

				getShell().setText( "Add manifest attribute" );
				setTitle( "Add manifest attribute" );
				setMessage( "Enter informations about manifest header entry" );

				IWidgetValueProperty tProp = WidgetProperties.text( SWT.Modify );

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "Name*:" );

					Text t = new Text( container, SWT.BORDER );
					t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, PARAM__NAME );
					dbc.bindValue( tProp.observe( t ), prop.observe( o ) );
				}

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "Value*:" );

					Text t = new Text( container, SWT.BORDER );
					t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, PARAM__VALUE );
					dbc.bindValue( tProp.observe( t ), prop.observe( o ) );
				}

				return area;
			}

			@Override
			protected void okPressed() {
				getTask().getManifestEntries().add( o );
				dbc.dispose();
				super.okPressed();
			}
		};

		return d.open() == TitleAreaDialog.OK;
	}

	String handleSplashImage( Shell shell ) {
		FilteredResourcesSelectionDialog d = new FilteredResourcesSelectionDialog( shell, false,
				( (IFileEditorInput) getEditorInput() ).getFile().getProject(), IResource.FILE ) {
			@Override
			protected IStatus validateItem( Object item ) {
				IFile f = (IFile) item;
				if ( f.getParent() instanceof IProject ) {
					return new Status( IStatus.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui", "The selected resource has to part of the source folder" );
				}
				return super.validateItem( item );
			}
		};
		if ( d.open() == ResourceSelectionDialog.OK ) {
			Object[] rv = d.getResult();
			if ( rv.length == 1 ) {
				IFile f = (IFile) rv[0];
				IJavaElement j = JavaCore.create( f.getParent() );
				if ( j instanceof IPackageFragment ) {
					IPackageFragment p = (IPackageFragment) j;
					return p.getElementName().replace( '.', '/' ) + "/" + f.getName();
				}
				else if ( j instanceof IPackageFragmentRoot ) {
					return f.getName();
				}
				else {
					MessageDialog.openInformation( shell, "Not valid", "The selected resource has to part of the source folder" );
				}
			}
		}

		return null;
	}

	boolean handleRemoveIcon( Icon value ) {
		if ( MessageDialog.openConfirm( getSite().getShell(), "Confirm delete", "Would really like to remove the selected icon" ) ) {
			getTask().getDeploy().getInfo().getIcon().remove( value );
			return true;
		}
		return false;
	}

	boolean handleAddIcon() {
		TitleAreaDialog d = new TitleAreaDialog( getSite().getShell() ) {
			private Icon o = ParametersFactory.eINSTANCE.createIcon();
			private DataBindingContext dbc = new DataBindingContext();

			@Override
			protected Control createDialogArea( Composite parent ) {
				Composite area = (Composite) super.createDialogArea( parent );

				getShell().setText( "Add icon" );
				setTitle( "Add icon" );
				setMessage( "Enter informations about the icon to add" );

				Composite container = new Composite( area, SWT.NONE );
				container.setLayout( new GridLayout( 2, false ) );
				container.setLayoutData( new GridData( GridData.FILL_BOTH ) );

				IViewerValueProperty selProp = ViewerProperties.singleSelection();
				IWidgetValueProperty tProp = WidgetProperties.text( SWT.Modify );

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "Kind:" );

					ComboViewer v = new ComboViewer( container, SWT.READ_ONLY );
					v.setLabelProvider( new LabelProvider() );
					v.setContentProvider( ArrayContentProvider.getInstance() );
					v.setInput( IconType.VALUES );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, ICON__KIND );
					dbc.bindValue( selProp.observe( v ), prop.observe( o ) );
				}

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "URL*:" );

					Text t = new Text( container, SWT.BORDER );
					t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, ICON__HREF );
					dbc.bindValue( tProp.observeDelayed( DELAY, t ), prop.observe( o ) );
				}

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "Depth:" );

					ComboViewer v = new ComboViewer( container, SWT.READ_ONLY );
					v.setLabelProvider( new LabelProvider() );
					v.setContentProvider( ArrayContentProvider.getInstance() );
					// FIXME not hard coded here
					v.setInput( new String[] { "8", "24", "32" } );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, ICON__DEPTH );
					dbc.bindValue( selProp.observe( v ), prop.observe( o ) );
				}

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "Width:" );

					Text t = new Text( container, SWT.BORDER );
					t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, ICON__WIDTH );
					dbc.bindValue( tProp.observeDelayed( DELAY, t ), prop.observe( o ) );
				}

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "Height:" );

					Text t = new Text( container, SWT.BORDER );
					t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain, ICON__HEIGHT );
					dbc.bindValue( tProp.observeDelayed( DELAY, t ), prop.observe( o ) );
				}

				return area;
			}

			@Override
			protected void okPressed() {
				getTask().getDeploy().getInfo().getIcon().add( o );
				dbc.dispose();
				super.okPressed();
			}
		};
		return d.open() == TitleAreaDialog.OK;
	}

	boolean handleAddSplash() {
		TitleAreaDialog d = new TitleAreaDialog( getSite().getShell() ) {
			private Splash o = ParametersFactory.eINSTANCE.createSplash();
			private DataBindingContext dbc = new DataBindingContext();

			@Override
			protected Control createDialogArea( Composite parent ) {
				Composite area = (Composite) super.createDialogArea( parent );

				getShell().setText( "Add splash icon" );
				setTitle( "Add splash" );
				setMessage( "Enter informations about the splash to add" );

				Composite container = new Composite( area, SWT.NONE );
				container.setLayout( new GridLayout( 2, false ) );
				container.setLayoutData( new GridData( GridData.FILL_BOTH ) );

				IViewerValueProperty selProp = ViewerProperties.singleSelection();
				IWidgetValueProperty tProp = WidgetProperties.text( SWT.Modify );

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "Mode*:" );

					ComboViewer v = new ComboViewer( container, SWT.READ_ONLY );
					v.setLabelProvider( new LabelProvider() );
					v.setContentProvider( ArrayContentProvider.getInstance() );
					v.setInput( SplashMode.values() );
					;
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain,
							FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__INFO, INFO__SPLASH, SPLASH__MODE ) );
					dbc.bindValue( selProp.observe( v ), prop.observe( o ) );
				}

				{
					Label l = new Label( container, SWT.NONE );
					l.setText( "URL*:" );

					Text t = new Text( container, SWT.BORDER );
					t.setLayoutData( new GridData( GridData.FILL_HORIZONTAL ) );
					IEMFValueProperty prop = EMFEditProperties.value( editingDomain,
							FeaturePath.fromList( ANT_TASK__DEPLOY, DEPLOY__INFO, INFO__SPLASH, SPLASH__HREF ) );
					dbc.bindValue( tProp.observeDelayed( DELAY, t ), prop.observe( o ) );
				}
				return area;
			}

			@Override
			protected void okPressed() {
				getTask().getDeploy().getInfo().getSplash().add( o );
				dbc.dispose();
				super.okPressed();
			}
		};
		return d.open() == TitleAreaDialog.OK;

	}

	// String handleImageSelection() {
	// FileDialog d = new FileDialog(getSite().getShell());
	// }

	boolean handleRemoveSplash( Splash value ) {
		if ( MessageDialog.openConfirm( getSite().getShell(), "Confirm delete", "Would really like to remove the selected splash" ) ) {
			getTask().getDeploy().getInfo().getSplash().remove( value );
			return true;
		}
		return false;
	}

	void handleCreateKeyStore( Shell parent ) {

	}

	IStatus validateKeystoreAlias( Shell parent, String alias ) {
		return Status.OK_STATUS;
	}

	String handleBuildFilesystemDirectorySelection( Shell parent ) {
		DirectoryDialog dialog = new DirectoryDialog( parent );
		return dialog.open();
	}

	String handleBuildWorkbenchDirectorySelection( Shell parent ) {
		ILabelProvider lp = new WorkbenchLabelProvider();
		ITreeContentProvider cp = new WorkbenchContentProvider();

		Class<?>[] acceptedClasses = new Class[] { IProject.class, IFolder.class };
		ViewerFilter filter = new TypedViewerFilter( acceptedClasses );

		FolderSelectionDialog dialog = new FolderSelectionDialog( parent, lp, cp );
		dialog.setTitle( "Output directory" );
		dialog.setMessage( "Select output directory" );
		dialog.addFilter( filter );
		dialog.setInput( ResourcesPlugin.getWorkspace().getRoot() );
		if ( dialog.open() == Window.OK ) {
			IContainer c = (IContainer) dialog.getFirstResult();
			return "${workspace}/" + c.getProject().getName() + "/" + c.getProjectRelativePath().toString();
		}
		return null;
	}

	String handleRootclassSelection( Shell parent ) {
		IFileEditorInput i = (IFileEditorInput) getEditorInput();
		IJavaProject project = JavaCore.create( i.getFile().getProject() );
		if ( project == null ) {
			return null;
		}

		IJavaElement[] elements = new IJavaElement[] { project };

		int constraints = IJavaSearchScope.SOURCES;
		constraints |= IJavaSearchScope.APPLICATION_LIBRARIES;

		IJavaSearchScope searchScope = SearchEngine.createJavaSearchScope( elements, constraints );
		BusyIndicatorRunnableContext context = new BusyIndicatorRunnableContext();

		MainTypeSelectionDialog dialog = new MainTypeSelectionDialog( parent, context, searchScope, 0 );
		dialog.setTitle( "Find class" );
		dialog.setMessage( "Find the class used to launch the application" );
		if ( dialog.open() == Window.CANCEL ) {
			return null;
		}
		Object[] results = dialog.getResult();
		IType type = (IType) results[0];
		if ( type != null ) {
			return type.getFullyQualifiedName();
		}

		return null;
	}

	String handlePreloaderclassSelection( Shell parent ) {
		IFileEditorInput i = (IFileEditorInput) getEditorInput();
		IJavaProject project = JavaCore.create( i.getFile().getProject() );
		if ( project == null ) {
			return null;
		}

		try {
			IType superType = project.findType( "javafx.application.Preloader" );

			IJavaSearchScope searchScope = SearchEngine.createStrictHierarchyScope( project, superType, true, false, null );

			SelectionDialog dialog = JavaUI.createTypeDialog( parent, PlatformUI.getWorkbench().getProgressService(), searchScope,
					IJavaElementSearchConstants.CONSIDER_CLASSES, false, "" );
			dialog.setTitle( "Find Preloader" );
			if ( dialog.open() == Window.OK ) {
				IType type = (IType) dialog.getResult()[0];
				return type.getFullyQualifiedName( '$' );
			}
		}
		catch ( JavaModelException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	String handleKeyStoreFilesystemSelection( Shell parent ) {
		FileDialog dialog = new FileDialog( parent, SWT.OPEN );
		String keystore = dialog.open();
		if ( keystore != null ) {
			IStatus s = validateKeyStore( new File( keystore ) );
			if ( s.isOK() ) {
				return keystore;
			}
			else {
				MessageDialog.openError( parent, "Not a keystore", "Looks like the selected file is not a keystore" );
				return handleKeyStoreFilesystemSelection( parent );
			}
		}
		return null;
	}

	String handleKeyStoreWorkspaceSelection( Shell parent ) {
		ILabelProvider lp = new WorkbenchLabelProvider();
		ITreeContentProvider cp = new WorkbenchContentProvider();

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog( parent, lp, cp );
		dialog.setValidator( new ISelectionStatusValidator() {

			@Override
			public IStatus validate( Object[] selection ) {
				if ( selection.length > 1 ) {
					return new Status( IStatus.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui", "Only one file allowed." );
				}
				else if ( selection.length == 1 ) {
					if ( selection[0] instanceof IFile ) {
						IFile f = (IFile) selection[0];
						return validateKeyStore( f.getLocation().toFile() );
					}
				}
				return Status.OK_STATUS;
			}
		} );
		dialog.setInput( ResourcesPlugin.getWorkspace().getRoot() );

		if ( dialog.open() == Window.OK ) {
			IFile f = (IFile) dialog.getFirstResult();
			if ( f != null ) {
				return "${workspace}/" + f.getProject().getName() + "/" + f.getProjectRelativePath().toString();
			}
		}

		return null;
	}

	IStatus validateKeyStore( File f ) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream( f );
			KeyStore ks = KeyStore.getInstance( KeyStore.getDefaultType() );
			ks.load( fis, null );
		}
		catch ( FileNotFoundException e ) {
			return new Status( IStatus.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui", "The keystore file '" + f.getAbsolutePath() + "' is not found.", e );
		}
		catch ( KeyStoreException e ) {
			return new Status( IStatus.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui", "Unable to initialize keystore", e );
		}
		catch ( NoSuchAlgorithmException e ) {
			return new Status( IStatus.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui", "Loading keystore failed. Is this a valid keystore?", e );
		}
		catch ( CertificateException e ) {
			return new Status( IStatus.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui", "Loading keystore failed. Is this a valid keystore?", e );
		}
		catch ( IOException e ) {
			if ( e.getCause() instanceof UnrecoverableKeyException ) {
				return Status.OK_STATUS;
			}
			return new Status( IStatus.ERROR, "at.bestsolution.efxclipse.tooling.jdt.ui", "Loading keystore failed. Is this a valid keystore?", e );
		}
		finally {
			if ( fis != null ) {
				try {
					fis.close();
				}
				catch ( IOException e ) {
				}
			}
		}

		return Status.OK_STATUS;
	}

	protected void pageChange( int newPageIndex ) {
		if ( newPageIndex == 0 ) {
			syncForm();
		}
	}

	void syncForm() {
		// TODO maybe we need to load the stuff here.
		dbc.updateTargets();
	}

	void createPageEditor( AntTask task ) {
		try {
			editor = new PropertyTextEditor();
			int index = addPage( editor, getEditorInput() );
			setPageText( index, editor.getTitle() );
			setPartName( editor.getTitle() );
			editor.getDocumentProvider().getDocument( editor.getEditorInput() ).addDocumentListener( new IDocumentListener() {

				@Override
				public void documentChanged( DocumentEvent event ) {
					syncForm();
				}

				@Override
				public void documentAboutToBeChanged( DocumentEvent event ) {

				}
			} );
		}
		catch ( PartInitException e ) {
			ErrorDialog.openError( getSite().getShell(), "Error creating nested text editor", null, e.getStatus() );
		}
	}

	@Override
	public void resourceChanged( final IResourceChangeEvent event ) {
		if ( event.getType() == IResourceChangeEvent.PRE_CLOSE ) {
			Display.getDefault().asyncExec( new Runnable() {
				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for ( int i = 0; i < pages.length; i++ ) {
						if ( ( (FileEditorInput) editor.getEditorInput() ).getFile().getProject().equals( event.getResource() ) ) {
							IEditorPart editorPart = pages[i].findEditor( editor.getEditorInput() );
							pages[i].closeEditor( editorPart, true );
						}
					}
				}
			} );
		}
	}

	public Object getAdapter( @SuppressWarnings( "rawtypes" ) Class adapter ) {
		if ( adapter == IContentOutlinePage.class ) {
			final PropertyContentOutlinePage contentOutline = new PropertyContentOutlinePage( editor );
			return contentOutline;
		}
		//
		return super.getAdapter( adapter );
	}

	/**
	 * This accesses a cached version of the property sheet.
	 */
	public IPropertySheetPage getPropertySheetPage() {
		if ( propertySheetPage == null ) {
			propertySheetPage = new ExtendedPropertySheetPage( editingDomain ) {
				@Override
				public void setSelectionToViewer( List<?> selection ) {
					JFXEMFBuildConfigurationEditor.this.setSelectionToViewer( selection );
					JFXEMFBuildConfigurationEditor.this.setFocus();
				}

				@Override
				public void setActionBars( IActionBars actionBars ) {
					super.setActionBars( actionBars );
					getActionBarContributor().shareGlobalActions( this, actionBars );
				}
			};
			propertySheetPage.setPropertySourceProvider( new AdapterFactoryContentProvider( adapterFactory ) );
		}

		return propertySheetPage;
	}

	/**
	 * This listens for when the outline becomes active <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IPartListener partListener = new IPartListener() {
		public void partActivated( IWorkbenchPart p ) {
			if ( p instanceof PropertySheet ) {
				if ( ( (PropertySheet) p ).getCurrentPage() == propertySheetPage ) {
					getActionBarContributor().setActiveEditor( JFXEMFBuildConfigurationEditor.this );
					handleActivate();
				}
			}
			else if ( p == JFXEMFBuildConfigurationEditor.this ) {
				handleActivate();
			}
		}

		public void partBroughtToTop( IWorkbenchPart p ) {
			// Ignore.
		}

		public void partClosed( IWorkbenchPart p ) {
			// Ignore.
		}

		public void partDeactivated( IWorkbenchPart p ) {
			// Ignore.
		}

		public void partOpened( IWorkbenchPart p ) {
			// Ignore.
		}
	};

	/**
	 * Handles activation of the editor or it's associated views. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void handleActivate() {
		// Recompute the read only state.
		//
		if ( editingDomain.getResourceToReadOnlyMap() != null ) {
			editingDomain.getResourceToReadOnlyMap().clear();

			// Refresh any actions that may become enabled or disabled.
			//
			// setSelection( getSelection() );
		}

		if ( !removedResources.isEmpty() ) {
			if ( handleDirtyConflict() ) {
				getSite().getPage().closeEditor( JFXEMFBuildConfigurationEditor.this, false );
			}
			else {
				removedResources.clear();
				changedResources.clear();
				savedResources.clear();
			}
		}
		else if ( !changedResources.isEmpty() ) {
			changedResources.removeAll( savedResources );
			handleChangedResources();
			changedResources.clear();
			savedResources.clear();
		}
	}

	/**
	 * Handles what to do with changed resources on activation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void handleChangedResources() {
		if ( !changedResources.isEmpty() && ( !isDirty() || handleDirtyConflict() ) ) {
			if ( isDirty() ) {
				changedResources.addAll( editingDomain.getResourceSet().getResources() );
			}
			editingDomain.getCommandStack().flush();

			updateProblemIndication = false;
			for ( Resource resource : changedResources ) {
				if ( resource.isLoaded() ) {
					resource.unload();
					try {
						resource.load( Collections.EMPTY_MAP );
					}
					catch ( IOException exception ) {
						if ( !resourceToDiagnosticMap.containsKey( resource ) ) {
							resourceToDiagnosticMap.put( resource, analyzeResourceProblems( resource, exception ) );
						}
					}
				}
			}

			// if (AdapterFactoryEditingDomain.isStale(editorSelection)) {
			// setSelection(StructuredSelection.EMPTY);
			// }

			updateProblemIndication = true;
			updateProblemIndication();
		}
	}

	/**
	 * Shows a dialog that asks if conflicting changes should be discarded. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected boolean handleDirtyConflict() {
		return MessageDialog.openQuestion( getSite().getShell(), "File Conflict",
				"There are unsaved changes that conflict with changes made outside the editor.  Do you wish to discard this editor's changes?" );
	}

	/**
	 * If there is more than one page in the multi-page editor part, this shows the tabs at the bottom. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void showTabs() {
		if ( getPageCount() > 1 ) {
			setPageText( 0, "Selection" );
			if ( getContainer() instanceof CTabFolder ) {
				( (CTabFolder) getContainer() ).setTabHeight( SWT.DEFAULT );
				Point point = getContainer().getSize();
				getContainer().setSize( point.x, point.y - 6 );
			}
		}
	}
}
