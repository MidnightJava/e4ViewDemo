package controlContributionDemo.e4.views;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

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
