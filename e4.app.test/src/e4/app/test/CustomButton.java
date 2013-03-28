package e4.app.test;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.model.application.ui.menu.impl.ToolControlImpl;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class CustomButton extends ToolControlImpl {

	@PostConstruct
	public void createPartControl(Composite parent) {
		GridLayoutFactory.fillDefaults().applyTo(parent);
		Label label = new Label(parent, SWT.NONE);
		label.setText("Label");
		GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER);
		label.addMouseMoveListener(new MouseMoveListener() {

			@Override
			public void mouseMove(MouseEvent e) {
				System.err.println("MOUSE MOVE");
			}
			
		});
	}
}
