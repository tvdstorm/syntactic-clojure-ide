package synclj.lang.editor;


import org.eclipse.imp.editor.UniversalEditor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;

public class SyncljEditor extends UniversalEditor {

	public SyncljEditor() {
		super();
	}

	@Override
	protected void createActions() {
		super.createActions();
		IAction action = new Action() {/* Nothing. */
		};
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.SHOW_INFORMATION);
		setAction(ITextEditorActionConstants.SHOW_INFORMATION, action);
	}
	
}
