package controlcontribution.test.views;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.internal.services.ContextContextService;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.internal.contexts.ContextManagerFactory;
import org.eclipse.ui.internal.contexts.ContextService;
import org.eclipse.ui.internal.keys.model.ContextModel;
import org.eclipse.ui.internal.util.BundleUtility;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Version;

import controlcontribution.test.views.ListenerDemoView.MyControlContribution;

import e4.app.test.common.ExampleView;


public class WrappedListenerDemoView extends ViewPart {

	public static final String ID = "test.views.WrappedSampleView";

	private MyControlContribution cc;

	private ExampleView pojoView;
	
	public WrappedListenerDemoView() {
		pojoView = new ExampleView();
	}

	class MyControlContribution extends ControlContribution {
		protected MyControlContribution(String id) {
			super(id);
		}
		private ListenerList deferredListeners = new ListenerList();
		private Button b;
		@Override
		protected Control createControl(Composite parent) {
			b = new Button(parent, SWT.PUSH);
			GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(b);
			b.setText("Control Contribution");
			for (Object listener : this.deferredListeners.getListeners()) {
				if (listener instanceof MouseMoveListener) {
					b.addMouseMoveListener((MouseMoveListener) listener);
				} else if (listener instanceof SelectionListener) {
					b.addSelectionListener((SelectionListener) listener);
				}
			}
			this.deferredListeners.clear();
			return b;
		}

		public Control getContro() {
			return b;
		}

		public void addMouseTrackListener(MouseTrackListener listener) {
			if (b != null) {
				this.b.addMouseTrackListener(listener);
			} else {
				this.deferredListeners.add(listener);
			}
		}
		public void addSelectionListener(SelectionListener listener) {
			if (b != null) {
				this.b.addSelectionListener(listener);
			} else {
				this.deferredListeners.add(listener);
			}
		}

	};

	@Override
	@PostConstruct
	public void createPartControl(Composite parent) {
		Bundle platformBundle = Platform.getBundle("org.eclipse.platform");
		Version version = platformBundle.getVersion();
		System.err.println(version.toString());
		System.err.println("Major: " + version.getMajor());
		
		pojoView.createPartControl(parent);
		pojoView.setLabel("Hellow World E4—Wrapped!");
	}
	
	@Override
	public void setFocus() {
		this.pojoView.setFocus();
	}
}