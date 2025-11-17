public class Document {
    private final StringBuilder text = new StringBuilder();
    private String clipboard = "";

    public void addText(String newText) {
        text.append(newText);
    }

    public String cut() {
        clipboard = text.toString();
        text.setLength(0);
        return clipboard;
    }

    public String copy() {
        clipboard = text.toString();
        return clipboard;
    }

    public void paste() {
        text.append(clipboard);
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String newText) {
        text.setLength(0);
        text.append(newText);
    }

    public String getClipboard() {
        return clipboard;
    }
}
