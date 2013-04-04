package controlContributionDemo.e3;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	private Shell shell;

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}
	
	@Override
	public void preWindowOpen() {
		final IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(600, 400));
		configurer.setTitle("Control Contribution Demo");
	}
	
	@Override
	public void createWindowContents(Shell shell) {
		this.shell = shell;
		super.createWindowContents(shell);
	}
	
	@Override
	public void postWindowCreate() {
		shell.setLocation(300, 200);
	}

}
