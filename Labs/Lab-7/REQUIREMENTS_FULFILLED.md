# Design Problem #2 - Requirements Fulfillment

## Problem Requirements

### ✅ Requirement #1: One source code location for each of the cut, copy and paste functionality

### ✅ Requirement #2: The ability to undo actions

---

## How the Solution Meets Each Requirement

## ✅ REQUIREMENT #1: Single Source Code Location

### The Problem

Without proper design, the cut, copy, and paste logic would be **duplicated** in:

- Menu item click handlers
- Toolbar button click handlers
- Keyboard shortcut handlers

This leads to:

- ❌ Code duplication (same logic in 3+ places)
- ❌ Maintenance nightmare (change in 3+ places)
- ❌ Risk of inconsistent behavior

### The Solution: Command Pattern

**Each operation has exactly ONE implementation:**

#### Cut Operation

```
Core Implementation:
└── DocumentEditor.cut()      ← SINGLE source of cut logic

Encapsulation:
└── CutCommand.execute()      ← Wraps the cut logic
    └── calls editor.cut()

Multiple UI Triggers (ALL use the SAME CutCommand instance):
├── MenuItem ("Edit > Cut")        ─┐
├── ToolbarButton (✂️)             ├─→ Share cutCommand object
└── KeyboardShortcut ("Ctrl+X")   ─┘
```

#### Copy Operation

```
Core Implementation:
└── DocumentEditor.copy()     ← SINGLE source of copy logic

Encapsulation:
└── CopyCommand.execute()     ← Wraps the copy logic

Multiple UI Triggers (ALL use the SAME CopyCommand instance):
├── MenuItem ("Edit > Copy")       ─┐
├── ToolbarButton (📋)             ├─→ Share copyCommand object
└── KeyboardShortcut ("Ctrl+C")   ─┘
```

#### Paste Operation

```
Core Implementation:
└── DocumentEditor.paste()    ← SINGLE source of paste logic

Encapsulation:
└── PasteCommand.execute()    ← Wraps the paste logic

Multiple UI Triggers (ALL use the SAME PasteCommand instance):
├── MenuItem ("Edit > Paste")      ─┐
├── ToolbarButton (📄)             ├─→ Share pasteCommand object
└── KeyboardShortcut ("Ctrl+V")   ─┘
```

### Code Proof

**In EditorApplication.java:**

```java
// Create ONE instance of each command
CutCommand cutCommand = new CutCommand(editor);      // Single instance
CopyCommand copyCommand = new CopyCommand(editor);   // Single instance
PasteCommand pasteCommand = new PasteCommand(editor); // Single instance

// ALL UI elements use the SAME command objects
MenuItem cutMenuItem = new MenuItem("Edit > Cut", cutCommand, invoker);
ToolbarButton cutButton = new ToolbarButton("✂️", cutCommand, invoker);
KeyboardShortcut cutShortcut = new KeyboardShortcut("Ctrl+X", cutCommand, invoker);
// ↑ All three use cutCommand - the SAME object instance!

// When any UI element is triggered:
cutMenuItem.click();    // Executes cutCommand
cutButton.click();      // Executes the SAME cutCommand
cutShortcut.press();    // Executes the SAME cutCommand
```

### Benefits

✅ **No code duplication** - Each operation implemented once  
✅ **Single source of truth** - Change once, affects all UI elements  
✅ **Consistent behavior** - All UI elements do exactly the same thing  
✅ **Easy maintenance** - Update in one place

---

## ✅ REQUIREMENT #2: Undo Capability

### The Solution: Command History + State Management

The Command Pattern naturally supports undo through:

1. **State Storage**: Commands save previous state before execution
2. **Command History**: Invoker maintains a stack of executed commands
3. **Undo Method**: Commands can reverse their operations

### How Undo Works

#### Step 1: Command Execution

```
User Action:
├── UI element triggers command
└── Invoker executes command

Command Execution:
├── Save current state (EditorState)
├── Perform operation (cut/copy/paste)
└── Invoker pushes command to history stack

History Stack:
[Command1, Command2, Command3] ← newest on top
```

#### Step 2: Undo Operation

```
User requests undo:
├── Invoker pops last command from stack
├── Calls command.undo()
└── Command restores previous state

State Restoration:
├── Command has saved EditorState
├── Restores content, clipboard, selection
└── Document returns to previous state
```

### Code Implementation

**Commands Store State:**

```java
public class CutCommand implements Command {
    private DocumentEditor editor;
    private EditorState previousState;  // ← Stores state for undo

    public void execute() {
        previousState = editor.cut();   // ← Save state before operation
    }

    public void undo() {
        if (previousState != null) {
            editor.restoreState(previousState);  // ← Restore saved state
        }
    }
}
```

**Invoker Maintains History:**

```java
public class CommandInvoker {
    private Stack<Command> commandHistory;  // ← Stack of executed commands

    public void executeCommand(Command command) {
        command.execute();
        commandHistory.push(command);       // ← Add to history
    }

    public void undo() {
        if (!commandHistory.isEmpty()) {
            Command command = commandHistory.pop();  // ← Get last command
            command.undo();                          // ← Reverse it
        }
    }
}
```

**Editor Provides State Snapshots:**

```java
public class DocumentEditor {
    public EditorState cut() {
        // Save current state
        EditorState previousState = new EditorState(
            content.toString(),
            clipboard,
            selectedText,
            selectionStart,
            selectionEnd
        );

        // Perform cut operation
        clipboard = selectedText;
        content.delete(selectionStart, selectionEnd);

        return previousState;  // ← Return state for undo
    }

    public void restoreState(EditorState state) {
        // Restore all fields from saved state
        this.content = new StringBuilder(state.getContent());
        this.clipboard = state.getClipboard();
        this.selectedText = state.getSelectedText();
        // ... restore other fields
    }
}
```

### Undo Demonstration

```
Operations:
1. Copy "Hello" → history: [CopyCommand]
2. Paste       → history: [CopyCommand, PasteCommand]
3. Cut "World" → history: [CopyCommand, PasteCommand, CutCommand]

Undo sequence:
1. undo() → CutCommand.undo()   → "World" restored
2. undo() → PasteCommand.undo() → Paste reversed
3. undo() → CopyCommand.undo()  → Clipboard restored
```

### Benefits

✅ **Complete undo support** - All operations can be reversed  
✅ **Multiple undo levels** - Can undo many operations  
✅ **State integrity** - Full document state preserved  
✅ **Easy to extend** - Can add redo with second stack

---

## Summary

### Requirement #1: Single Source Code Location ✅

| Operation | Implementation File                       | Lines of Code | UI Elements Using It                 |
| --------- | ----------------------------------------- | ------------- | ------------------------------------ |
| Cut       | `DocumentEditor.cut()` + `CutCommand`     | ~25 lines     | Menu, Toolbar, Keyboard (1 instance) |
| Copy      | `DocumentEditor.copy()` + `CopyCommand`   | ~25 lines     | Menu, Toolbar, Keyboard (1 instance) |
| Paste     | `DocumentEditor.paste()` + `PasteCommand` | ~30 lines     | Menu, Toolbar, Keyboard (1 instance) |

**Result**: Each operation = ONE implementation, used by ALL UI elements

### Requirement #2: Undo Capability ✅

| Component | Responsibility   | Implementation                  |
| --------- | ---------------- | ------------------------------- |
| Command   | Store state      | `EditorState previousState`     |
| Command   | Undo method      | `command.undo()` restores state |
| Invoker   | History tracking | `Stack<Command> commandHistory` |
| Invoker   | Undo execution   | `undo()` pops and reverses      |

**Result**: Full undo capability with state management

---

## Design Pattern: Command Pattern

**Why Command Pattern solves both requirements:**

1. **Encapsulation**: Wraps operations as objects
2. **Single Instance**: One command object shared by multiple clients
3. **History Support**: Commands can be stored and reversed
4. **Decoupling**: UI elements don't know how operations work

**Class Structure:**

```
Command (interface)
  ├── execute(): void
  └── undo(): void

ConcreteCommand (CutCommand, CopyCommand, PasteCommand)
  ├── Stores: reference to receiver + previous state
  ├── execute(): calls receiver method, saves state
  └── undo(): restores previous state

Receiver (DocumentEditor)
  ├── Contains: actual business logic
  ├── Methods: cut(), copy(), paste()
  └── Returns: state snapshots for undo

Invoker (CommandInvoker)
  ├── executeCommand(): runs command + adds to history
  └── undo(): pops history + reverses command

Client (UI elements)
  └── Trigger commands through invoker
```

---

## Verification

Run `EditorApplication.java` to see:

1. ✅ Multiple UI elements using the same command objects
2. ✅ No code duplication across UI elements
3. ✅ Undo operations reversing all changes
4. ✅ State properly restored after undo

---

## Conclusion

Both requirements are **fully satisfied** through the elegant application of the **Command Pattern**:

✅ **Requirement #1**: ONE source code location per operation  
✅ **Requirement #2**: Complete undo capability

The solution is maintainable, extensible, testable, and follows SOLID principles.
