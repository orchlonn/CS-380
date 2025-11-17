public class CutCommand implements Command {
    private final Document doc;
    private String backup = "";

    public CutCommand(Document doc) {
        this.doc = doc;
    }

    @Override
    public void execute() {
        backup = doc.getText();
        doc.cut();
    }

    @Override
    public void undo() {
        doc.setText(backup);
    }
}
