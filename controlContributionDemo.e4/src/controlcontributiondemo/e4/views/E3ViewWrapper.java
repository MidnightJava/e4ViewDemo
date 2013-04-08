package controlcontributiondemo.e4.views;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * 
 * This view can only be rendered in an E4 runtime. The view is contributed via the views extension
 * as an E3 view running in the E4 compatibility layer. However, it wraps a pure E4 view implemented
 * as a POJO with the requisite injection annotations. It is instantiated by the workbench model
 * provided in Plug-in application.e4, which was generated using the E4 Tools project.
 *
 */


public class E3ViewWrapper extends ViewPart {

	public static final String ID = "test.views.WrappedSampleView";

	private MyControlContribution cc;

	private E4View pojoView;

	public E3ViewWrapper() {
		pojoView = new E4View();
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
	
	@Inject
	public void setApplication(MApplication application, MWindow window) {
		pojoView.setApplication(application, window);
	}

	@Override
	@PostConstruct
	public void createPartControl(Composite parent) {
		Bundle platformBundle = Platform.getBundle("org.eclipse.platform");
		Version version = platformBundle.getVersion();
		if (version.getMajor() < 4) {
			addToolbarItem();
		} 
		//in e4 runtime, toolbar and tool items are added through the application model
		pojoView.createPartControl(parent);
		pojoView.setLabel("Hellow World E4 Wrapped!");
		pojoView.setId("application.e4.inputpart.1");
	}

	private void addToolbarItem() {
		IActionBars bars = getViewSite().getActionBars();
		cc = new MyControlContribution("ControlContribution");
		bars.getToolBarManager().add(cc);
		cc.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseEnter(MouseEvent arg0) {
				System.err.println("MOUSE ENTER");
			}
		});
		cc.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.err.println("BUTTON CLICKED");
			}
		});
		bars.updateActionBars();
	}

	@Override
	public void setFocus() {
		this.pojoView.setFocus();
	}
}