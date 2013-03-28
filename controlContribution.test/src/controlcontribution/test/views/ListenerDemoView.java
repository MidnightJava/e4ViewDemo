package controlcontribution.test.views;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.services.IServiceConstants;
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
import org.eclipse.ui.part.ViewPart;


public class ListenerDemoView extends ViewPart {
	
	@SuppressWarnings("restriction")
	@Inject
	@Named(IServiceConstants.ACTIVE_PART)
	IViewPart activePart;

	public static final String ID = "test.views.SampleView";

	private MyControlContribution cc;

	private Label label;

	private IViewSite site;

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
		GridLayoutFactory.fillDefaults().applyTo(parent);
		label = new Label(parent, SWT.NONE);
		label.setText("Hello Worl E3!");
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(label);
		IActionBars bars = activePart.getViewSite().getActionBars();
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
		label.setFocus();
		this.site = getViewSite();
	}
}