package e4.app.test.common;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MApplicationFactory;
import org.eclipse.e4.ui.model.application.ui.menu.impl.ToolControlImpl;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public class ExampleView {

	private static final String TOOL_ITEM_ID = "e4.app.test.toolcontrol";
	private Label label;
	private MApplication application;

	@PostConstruct
	public void createPartControl(Composite parent, MApplication application) {
		this.application = application;
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
