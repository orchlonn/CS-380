# Design Problem #2 - Solution

## Problem Summary

Create a document editing program with cut, copy, and paste functionality that:

1. Has **one source code location** for each operation (no code duplication)
2. Provides **undo capability**
3. Allows multiple UI elements (menu, toolbar, keyboard shortcuts) to trigger the same actions

## Solution: Command Pattern

The **Command Pattern** is the ideal solution for this problem. It encapsulates a request as an object, allowing:

- Parameterization of clients with different requests
- Queuing or logging of requests
- Support for undoable operations

## Architecture Overview

### Core Components

1. **Command (Interface)**

   - Defines the contract for all command objects
   - Methods: `execute()` and `undo()`

2. **Concrete Commands** (CutCommand, CopyCommand, PasteCommand)

   - Implement the Command interface
   - Each encapsulates a specific operation
   - Maintains state needed for undo operations

3. **Receiver** (DocumentEditor)

   - Contains the actual business logic for text operations
   - Methods: `cut()`, `copy()`, `paste()`
   - Manages document state and clipboard

4. **Invoker** (CommandInvoker)

   - Executes commands
   - Maintains command history for undo
   - Decouples UI elements from command execution

5. **Client/UI Elements** (MenuItem, ToolbarButton, KeyboardShortcut)
   - Trigger commands through the invoker
   - Multiple UI elements can use the same command instance

## Key Benefits

### 1. Single Source of Truth

Each operation (cut, copy, paste) has **only ONE implementation** in the corresponding command class. No code duplication across UI elements.

### 2. Easy Undo/Redo

Commands store their previous state and can reverse their actions. The invoker maintains a stack of executed commands.

### 3. Decoupling

UI elements don't need to know how operations work. They just execute commands through the invoker.

### 4. Extensibility

- New commands can be added easily (e.g., SelectAll, Find, Replace)
- New UI elements can be added without modifying existing code
- Redo functionality could be added by maintaining a second stack

### 5. Testability

Commands can be tested independently of the UI

## How It Works

### Example: User clicks "Cut" from menu

1. User clicks the Cut menu item
2. MenuItem calls `invoker.executeCommand(cutCommand)`
3. Invoker calls `cutCommand.execute()`
4. CutCommand calls `editor.cut()` and stores previous state
5. DocumentEditor performs the actual cut operation
6. Invoker adds the command to history stack

### Undo Operation

1. User triggers undo
2. Invoker pops the last command from history
3. Invoker calls `command.undo()`
4. Command restores the previous state in DocumentEditor

## Class Relationships

```
Command <<interface>>
   ↑
   |implements
   |
   +------- CutCommand
   +------- CopyCommand
   +------- PasteCommand

CommandInvoker ----uses----> Command
   ↑
   |uses
   |
UI Elements (MenuItem, ToolbarButton, KeyboardShortcut)

DocumentEditor <----modifies---- ConcreteCommands
```

## Design Patterns Used

1. **Command Pattern** (Primary)

   - Encapsulates requests as objects
   - Supports undo/redo
   - Decouples sender from receiver

2. **Memento Pattern** (Implicit)
   - EditorState class stores snapshots for undo
   - Maintains encapsulation boundaries

## Comparison with Alternative Approaches

### Without Command Pattern (❌ Poor Design)

```java
class MenuItem {
    void onClick() {
        // Direct implementation - code duplication
        String selected = editor.getSelected();
        editor.setClipboard(selected);
        editor.delete(selected);
    }
}

class ToolbarButton {
    void onClick() {
        // Same code duplicated - maintenance nightmare
        String selected = editor.getSelected();
        editor.setClipboard(selected);
        editor.delete(selected);
    }
}
// No easy way to implement undo
```

### With Command Pattern (✅ Good Design)

```java
class MenuItem {
    void onClick() {
        invoker.executeCommand(cutCommand); // Single line, reusable
    }
}

class ToolbarButton {
    void onClick() {
        invoker.executeCommand(cutCommand); // Same command object
    }
}
// Undo: invoker.undo();
```

## Files in This Solution

1. **Command.java** - Command interface
2. **CutCommand.java** - Cut operation command
3. **CopyCommand.java** - Copy operation command
4. **PasteCommand.java** - Paste operation command
5. **DocumentEditor.java** - Receiver with actual functionality
6. **CommandInvoker.java** - Command executor and history manager
7. **MenuItem.java** - Menu UI element
8. **ToolbarButton.java** - Toolbar UI element
9. **KeyboardShortcut.java** - Keyboard shortcut UI element
10. **EditorApplication.java** - Demo application
11. **UML_DIAGRAM.md** - UML class diagram

## Running the Demo

```bash
# Compile all files
javac *.java

# Run the demo
java EditorApplication
```

The demo shows:

- Multiple UI elements triggering the same commands
- Operations being executed
- Undo functionality reversing operations
- State management and history tracking

## Extending the System

### Adding a New Command (e.g., SelectAll)

```java
public class SelectAllCommand implements Command {
    private DocumentEditor editor;
    private EditorState previousState;

    public void execute() {
        previousState = editor.selectAll();
    }

    public void undo() {
        editor.restoreState(previousState);
    }
}
```

### Adding Redo Functionality

Add a redo stack to CommandInvoker:

```java
class CommandInvoker {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }
}
```

## Conclusion

The Command Pattern elegantly solves both requirements:

1. ✅ **Single source of functionality** - Each operation implemented once in its command class
2. ✅ **Undo capability** - Commands store state and can reverse operations

This design is maintainable, extensible, and follows SOLID principles, particularly:

- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Open for extension (new commands), closed for modification
- **Dependency Inversion**: UI depends on Command abstraction, not concrete implementations
