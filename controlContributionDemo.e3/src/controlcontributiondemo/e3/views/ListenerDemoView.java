package controlcontributiondemo.e3.views;


import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * This view will render in an E3 or E4 runtime. In an E3 runtime, it will place a ControlContribution
 * with a mouse listener on the view toolbar obtained from the ViewSite. In an E4 runtime, the workbench
 * model instantiates this view and places a ToolControlImpl instance on its toolbar. The ViewSite is not
 * available in an E4 runtime.
 * 
 * The view is defined via a views extension in plugin.xml. In an E3 runtime, the view is instantiated
 * as a normal E3 view. In an E4 runtime, the application workbench model references and instantiates
 * the view using its plugin.xml contribution. The workbench model is provided in Plug-in application.e4,
 * which was generated using the E4 Tools project.
 */

public class ListenerDemoView extends ViewPart {

	public static final String ID = "test.views.SampleView";

	private MyControlContribution cc;

	private Label label;

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
				if (listener instanceof MouseTrackListener) {
					b.addMouseTrackListener((MouseTrackListener) listener);
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
		GridLayoutFactory.fillDefaults().applyTo(parent);
		label = new Label(parent, SWT.NONE);
		label.setText("Hello World E3!");
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(label);
		Bundle platformBundle = Platform.getBundle("org.eclipse.platform");
		Version version = platformBundle.getVersion();
		if (version.getMajor() < 4) {
			addToolbarItem();
		} 
		//in e4 runtime, toolbar and tool items are added through the application model provided in application.e4
	}

	private void addToolbarItem() {
		IActionBars bars = getViewSite().getActionBars();
		cc = new MyControlContribution("ControlContribution");
		bars.getToolBarManager().add(cc);
		cc.addMouseTrackListener(new MouseTrackAdapter() {

			@Override
			public void mouseEnter(MouseEvent e) {
				System.err.println("MOUSE ENTER");
			}

			@Override
			public void mouseExit(MouseEvent e) {
				System.err.println("MOUSE EXIT");
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
		label.setFocus();
	}
}