/**
 * PasteCommand - Concrete command for paste operation
 * Encapsulates the paste functionality and maintains state for undo
 */
public class PasteCommand implements Command {
    private DocumentEditor editor;
    private DocumentEditor.EditorState previousState;
    
    public PasteCommand(DocumentEditor editor) {
        this.editor = editor;
    }
    
    @Override
    public void execute() {
        previousState = editor.paste();
    }
    
    @Override
    public void undo() {
        if (previousState != null) {
            editor.restoreState(previousState);
            System.out.println("PASTE operation undone.");
        }
    }
}

