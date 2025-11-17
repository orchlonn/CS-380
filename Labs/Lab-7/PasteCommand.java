public class PasteCommand implements Command {
    private final Document doc;
    private String backup = "";

    public PasteCommand(Document doc) {
        this.doc = doc;
    }

    @Override
    public void execute() {
        backup = doc.getText();
        doc.paste();
    }

    @Override
    public void undo() {
        doc.setText(backup);
    }
}
