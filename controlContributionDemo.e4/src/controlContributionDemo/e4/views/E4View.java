package controlContributionDemo.e4.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;
import org.eclipse.e4.ui.model.application.ui.menu.MToolItem;
import org.eclipse.e4.ui.model.application.ui.menu.impl.MenuPackageImpl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.ToolBarImpl;
import org.eclipse.e4.ui.model.application.ui.menu.impl.ToolControlImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import controlContributionDemo.e4.CustomButton1;

/**
 * 
 * This view can only be rendered in an E4 runtime. It is instantiated by the workbench model
 * provided in Plug-in application.e4, which was generated using the E4 Tools project.
 *
 */
public class E4View {

	private static final String TOOL_ITEM_ID = "application.e4.toolcontrol.1";
	private Label label;
	private MApplication application;

	@Inject
	public void setApplication(MApplication application) {
		this.application = application;
	}

	@PostConstruct
	public void createPartControl(Composite parent) {
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(parent);
		label = new Label(parent, SWT.NONE);
		label.setText("Hello World E4!");
		GridDataFactory.fillDefaults().span(3, 1).applyTo(label);

		Label l2 = new Label(parent, SWT.NONE);
		l2.setText("Enter new label text");
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER).applyTo(l2);
		final Text text = new Text(parent, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(text);
		final Button b = new Button(parent, SWT.PUSH);
		b.setText("Change Label");
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).applyTo(b);
		b.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ToolControlImpl toolControl = getToolControl();
				if (toolControl != null) {
					ToolBar bar = (ToolBar) toolControl.getParent().getWidget();
					ToolItem[] items = bar.getItems();
					Control[] controls = bar.getChildren();

					Label l = (Label) toolControl.getObject();
				}

			}
		});
		b.setEnabled(false);

		text.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				b.setEnabled(text.getText().length() > 0);
			}

		});
	}

	public ToolControlImpl getToolControl() {
		EModelService modelService = (EModelService) application.getContext().get(EModelService.class.getName());
		@SuppressWarnings("restriction")
		List<String> tags = new ArrayList<String>();
		tags.add("custom1");
		@SuppressWarnings("restriction")
		List<ToolControlImpl> elements = modelService.findElements(application, null,ToolControlImpl.class, null);
		System.err.println(elements.size() + " elements found");
		return null;
	}

	public void setLabel(String text) {
		label.setText(text);
	}

	@Focus
	public void setFocus() {
		this.label.setFocus();
	}
}
