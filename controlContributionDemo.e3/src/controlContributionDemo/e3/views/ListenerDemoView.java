package controlContributionDemo.e3.views;


import javax.annotation.PostConstruct;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.ControlContribution;
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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;

/**
 * This view will render in an E3 runtime, and it will place a Control with
 * a mouse listener on the view toolbar. This view will not render in an
 * E4 runtime, even if you assign it as an Input Part in the E4 model,
 * because the E4 runtime will not invoke {@link #createPartControl(Composite)}
 * without the @PostConstruct annotation.
 *
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
	/** Uncomment this annotation to use this view in an E4 runtime */
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
		//in e4 runtime, toolbar and tool items are added through the application model
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