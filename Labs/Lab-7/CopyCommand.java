/**
 * CopyCommand - Concrete command for copy operation
 * Encapsulates the copy functionality and maintains state for undo
 */
public class CopyCommand implements Command {
    private DocumentEditor editor;
    private DocumentEditor.EditorState previousState;
    
    public CopyCommand(DocumentEditor editor) {
        this.editor = editor;
    }
    
    @Override
    public void execute() {
        previousState = editor.copy();
    }
    
    @Override
    public void undo() {
        if (previousState != null) {
            editor.restoreState(previousState);
            System.out.println("COPY operation undone.");
        }
    }
}

