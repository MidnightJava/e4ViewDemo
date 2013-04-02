package controlContributionDemo.e4.views;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * 
 * This view can only be rendered in an E4 runtime. It is instantiated by the workbench model
 * provided in Plug-in application.e4, which was generated using the E4 Tools project.
 *
 */
public class E4View {

	private static final String TOOL_ITEM_ID = "e4.app.test.toolcontrol";
	private Label label;

	@PostConstruct
	public void createPartControl(Composite parent) {
		label = new Label(parent, SWT.NONE);
		label.setText("Hello World E4!");
	}

	public void setLabel(String text) {
		label.setText(text);
	}

	@Focus
	public void setFocus() {
		this.label.setFocus();
	}
}
