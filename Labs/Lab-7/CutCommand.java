/**
 * CutCommand - Concrete command for cut operation
 * Encapsulates the cut functionality and maintains state for undo
 */
public class CutCommand implements Command {
    private DocumentEditor editor;
    private DocumentEditor.EditorState previousState;
    
    public CutCommand(DocumentEditor editor) {
        this.editor = editor;
    }
    
    @Override
    public void execute() {
        previousState = editor.cut();
    }
    
    @Override
    public void undo() {
        if (previousState != null) {
            editor.restoreState(previousState);
            System.out.println("CUT operation undone.");
        }
    }
}

