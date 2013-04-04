package controlContributionDemo.e3;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import controlContributionDemo.e3.views.ListenerDemoView;

public class PerspectiveFactory implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {
		final String editorArea = layout.getEditorArea();
		
		final IFolderLayout main = layout.createFolder("main", IPageLayout.LEFT, 0.9f, editorArea);
		main.addView(ListenerDemoView.ID);
	}

}
