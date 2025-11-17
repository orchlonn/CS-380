/**
 * EditorApplication - Demo application that demonstrates the Command Pattern
 * 
 * This application shows how the Command Pattern solves the design problem by:
 * 1. Providing ONE source code location for each operation (cut, copy, paste)
 * 2. Enabling undo functionality for all operations
 * 3. Allowing multiple UI elements to use the same command objects
 */
public class EditorApplication {
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║     COMMAND PATTERN - Document Editor Demo               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // ========================================
        // SETUP: Create the core components
        // ========================================
        
        // Receiver: Contains the ACTUAL implementation of operations
        DocumentEditor editor = new DocumentEditor();
        editor.setContent("Hello World! This is a test document.");
        System.out.println("📄 Initial content: \"" + editor.getContent() + "\"\n");
        
        // Invoker: Executes commands and maintains undo history
        CommandInvoker invoker = new CommandInvoker();
        
        // ========================================
        // KEY POINT: ONE command instance per operation
        // ========================================
        System.out.println("🔑 KEY DESIGN FEATURE:");
        System.out.println("   Creating ONE instance of each command...\n");
        
        CutCommand cutCommand = new CutCommand(editor);
        CopyCommand copyCommand = new CopyCommand(editor);
        PasteCommand pasteCommand = new PasteCommand(editor);
        
        // ========================================
        // Multiple UI elements share the SAME command objects
        // This eliminates code duplication!
        // ========================================
        System.out.println("✨ REQUIREMENT #1: Single Source of Functionality");
        System.out.println("   All UI elements use the SAME command instances:\n");
        
        // Menu items
        MenuItem cutMenuItem = new MenuItem("Edit > Cut", cutCommand, invoker);
        MenuItem copyMenuItem = new MenuItem("Edit > Copy", copyCommand, invoker);
        MenuItem pasteMenuItem = new MenuItem("Edit > Paste", pasteCommand, invoker);
        
        // Toolbar buttons
        ToolbarButton cutButton = new ToolbarButton("✂️", cutCommand, invoker);
        ToolbarButton copyButton = new ToolbarButton("📋", copyCommand, invoker);
        ToolbarButton pasteButton = new ToolbarButton("📄", pasteCommand, invoker);
        
        // Keyboard shortcuts
        KeyboardShortcut cutShortcut = new KeyboardShortcut("Ctrl+X", cutCommand, invoker);
        KeyboardShortcut copyShortcut = new KeyboardShortcut("Ctrl+C", copyCommand, invoker);
        KeyboardShortcut pasteShortcut = new KeyboardShortcut("Ctrl+V", pasteCommand, invoker);
        
        System.out.println("   ✅ Menu, Toolbar, and Keyboard all use cutCommand (same object)");
        System.out.println("   ✅ Menu, Toolbar, and Keyboard all use copyCommand (same object)");
        System.out.println("   ✅ Menu, Toolbar, and Keyboard all use pasteCommand (same object)");
        System.out.println("   ✅ NO CODE DUPLICATION - each operation implemented ONCE!\n");
        
        printSeparator();
        
        // ========================================
        // DEMONSTRATION: Multiple UI elements doing the same thing
        // ========================================
        System.out.println("📋 SCENARIO 1: COPY operation (via Menu)");
        printSeparator();
        editor.setSelection(0, 5); // Select "Hello"
        System.out.println("   Selected: \"" + editor.getSelectedText() + "\"");
        copyMenuItem.click(); // User uses menu
        System.out.println("   📄 Content: \"" + editor.getContent() + "\"");
        System.out.println("   📋 Clipboard: \"" + editor.getClipboard() + "\"\n");
        
        printSeparator();
        System.out.println("📄 SCENARIO 2: PASTE operation (via Toolbar)");
        printSeparator();
        editor.setSelection(38, 38); // End of document
        pasteButton.click(); // User uses toolbar button
        System.out.println("   📄 Content: \"" + editor.getContent() + "\"\n");
        
        printSeparator();
        System.out.println("✂️ SCENARIO 3: CUT operation (via Keyboard)");
        printSeparator();
        editor.setSelection(6, 12); // Select "World!"
        System.out.println("   Selected: \"" + editor.getSelectedText() + "\"");
        cutShortcut.press(); // User uses keyboard shortcut
        System.out.println("   📄 Content: \"" + editor.getContent() + "\"");
        System.out.println("   📋 Clipboard: \"" + editor.getClipboard() + "\"\n");
        
        printSeparator();
        System.out.println("📄 SCENARIO 4: PASTE again (via Menu)");
        printSeparator();
        editor.setSelection(0, 0); // Beginning of document
        pasteMenuItem.click(); // Back to menu
        System.out.println("   📄 Content: \"" + editor.getContent() + "\"\n");
        
        // ========================================
        // UNDO DEMONSTRATION
        // ========================================
        printSeparator();
        System.out.println("⏪ REQUIREMENT #2: Undo Functionality");
        printSeparator();
        System.out.println("   Command history size: " + invoker.getHistorySize() + " operations");
        System.out.println("   Now undoing all operations...\n");
        
        System.out.println("   [1] Undo PASTE:");
        invoker.undo();
        System.out.println("       Content: \"" + editor.getContent() + "\"\n");
        
        System.out.println("   [2] Undo CUT:");
        invoker.undo();
        System.out.println("       Content: \"" + editor.getContent() + "\"\n");
        
        System.out.println("   [3] Undo PASTE:");
        invoker.undo();
        System.out.println("       Content: \"" + editor.getContent() + "\"\n");
        
        System.out.println("   [4] Undo COPY:");
        invoker.undo();
        System.out.println("       Content: \"" + editor.getContent() + "\"");
        System.out.println("       Clipboard: \"" + editor.getClipboard() + "\"\n");
        
        System.out.println("   [5] Try to undo with empty history:");
        invoker.undo();
        System.out.println();
        
        // ========================================
        // SUMMARY
        // ========================================
        printSeparator();
        System.out.println("✅ SOLUTION SUMMARY");
        printSeparator();
        System.out.println();
        System.out.println("📌 REQUIREMENT #1: Single Source Code Location");
        System.out.println("   ✅ Cut functionality:   DocumentEditor.cut() + CutCommand");
        System.out.println("   ✅ Copy functionality:  DocumentEditor.copy() + CopyCommand");
        System.out.println("   ✅ Paste functionality: DocumentEditor.paste() + PasteCommand");
        System.out.println("   ✅ All UI elements share the SAME command instances");
        System.out.println("   ✅ NO code duplication across menu, toolbar, keyboard\n");
        
        System.out.println("📌 REQUIREMENT #2: Undo Capability");
        System.out.println("   ✅ Commands store previous state before execution");
        System.out.println("   ✅ CommandInvoker maintains history stack");
        System.out.println("   ✅ Undo reverses operations by restoring state");
        System.out.println("   ✅ Can undo multiple operations in sequence\n");
        
        System.out.println("🎯 DESIGN PATTERN BENEFITS:");
        System.out.println("   • Maintainability: Change once, affects all UI elements");
        System.out.println("   • Extensibility: Easy to add new commands or UI elements");
        System.out.println("   • Testability: Components can be tested independently");
        System.out.println("   • Flexibility: Commands can be logged, queued, or macro'd\n");
        
        printSeparator();
        System.out.println("✨ COMMAND PATTERN SUCCESSFULLY SOLVES BOTH REQUIREMENTS! ✨");
        printSeparator();
    }
    
    private static void printSeparator() {
        System.out.println("───────────────────────────────────────────────────────────");
    }
}

