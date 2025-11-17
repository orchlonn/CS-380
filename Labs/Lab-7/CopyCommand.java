public class CopyCommand implements Command {
    private final Document doc;

    public CopyCommand(Document doc) {
        this.doc = doc;
    }

    @Override
    public void execute() {
        doc.copy();
    }

    @Override
    public void undo() {
        // Copy does not modify the document text, only the clipboard
        // In a full implementation, you might want to restore clipboard state
    }
}
