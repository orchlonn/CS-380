/**
 * DocumentEditor - Receiver class that contains the actual implementation
 * of text editing operations (cut, copy, paste)
 */
public class DocumentEditor {
    private StringBuilder content;
    private String clipboard;
    private String selectedText;
    private int selectionStart;
    private int selectionEnd;
    
    public DocumentEditor() {
        this.content = new StringBuilder();
        this.clipboard = "";
        this.selectedText = "";
        this.selectionStart = 0;
        this.selectionEnd = 0;
    }
    
    /**
     * Sets the content of the document
     */
    public void setContent(String text) {
        this.content = new StringBuilder(text);
    }
    
    /**
     * Gets the current content of the document
     */
    public String getContent() {
        return content.toString();
    }
    
    /**
     * Sets the selected text range
     */
    public void setSelection(int start, int end) {
        this.selectionStart = start;
        this.selectionEnd = end;
        if (start >= 0 && end <= content.length() && start < end) {
            this.selectedText = content.substring(start, end);
        } else {
            this.selectedText = "";
        }
    }
    
    /**
     * Gets the currently selected text
     */
    public String getSelectedText() {
        return selectedText;
    }
    
    /**
     * Gets the clipboard content
     */
    public String getClipboard() {
        return clipboard;
    }
    
    /**
     * Sets the clipboard content
     */
    public void setClipboard(String text) {
        this.clipboard = text;
    }
    
    /**
     * CUT operation - removes selected text and puts it in clipboard
     * Returns the state before the operation for undo
     */
    public EditorState cut() {
        EditorState previousState = new EditorState(
            content.toString(), 
            clipboard, 
            selectedText, 
            selectionStart, 
            selectionEnd
        );
        
        if (selectedText != null && !selectedText.isEmpty()) {
            clipboard = selectedText;
            content.delete(selectionStart, selectionEnd);
            selectedText = "";
        }
        
        System.out.println("CUT operation performed. Clipboard: " + clipboard);
        return previousState;
    }
    
    /**
     * COPY operation - copies selected text to clipboard
     * Returns the state before the operation for undo
     */
    public EditorState copy() {
        EditorState previousState = new EditorState(
            content.toString(), 
            clipboard, 
            selectedText, 
            selectionStart, 
            selectionEnd
        );
        
        if (selectedText != null && !selectedText.isEmpty()) {
            clipboard = selectedText;
        }
        
        System.out.println("COPY operation performed. Clipboard: " + clipboard);
        return previousState;
    }
    
    /**
     * PASTE operation - inserts clipboard content at selection start
     * Returns the state before the operation for undo
     */
    public EditorState paste() {
        EditorState previousState = new EditorState(
            content.toString(), 
            clipboard, 
            selectedText, 
            selectionStart, 
            selectionEnd
        );
        
        if (clipboard != null && !clipboard.isEmpty()) {
            if (selectedText != null && !selectedText.isEmpty()) {
                // Replace selected text with clipboard content
                content.replace(selectionStart, selectionEnd, clipboard);
            } else {
                // Insert at current position
                content.insert(selectionStart, clipboard);
            }
            selectedText = "";
        }
        
        System.out.println("PASTE operation performed. Content: " + content);
        return previousState;
    }
    
    /**
     * Restores the editor to a previous state (for undo)
     */
    public void restoreState(EditorState state) {
        this.content = new StringBuilder(state.getContent());
        this.clipboard = state.getClipboard();
        this.selectedText = state.getSelectedText();
        this.selectionStart = state.getSelectionStart();
        this.selectionEnd = state.getSelectionEnd();
    }
    
    /**
     * Inner class to store editor state for undo operations
     */
    public static class EditorState {
        private final String content;
        private final String clipboard;
        private final String selectedText;
        private final int selectionStart;
        private final int selectionEnd;
        
        public EditorState(String content, String clipboard, String selectedText, 
                          int selectionStart, int selectionEnd) {
            this.content = content;
            this.clipboard = clipboard;
            this.selectedText = selectedText;
            this.selectionStart = selectionStart;
            this.selectionEnd = selectionEnd;
        }
        
        public String getContent() { return content; }
        public String getClipboard() { return clipboard; }
        public String getSelectedText() { return selectedText; }
        public int getSelectionStart() { return selectionStart; }
        public int getSelectionEnd() { return selectionEnd; }
    }
}

