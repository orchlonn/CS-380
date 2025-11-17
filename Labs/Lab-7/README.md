# Design Problem #2 - Command Pattern Solution

## Overview

This solution implements the **Command Pattern** to solve a document editing system design problem. The system provides cut, copy, and paste functionality with:

- **Single source of truth** for each operation (no code duplication)
- **Undo capability** for all operations
- **Multiple UI interfaces** (menu, toolbar, keyboard shortcuts) using the same command objects

## Problem Statement

Design a document editing program where:

1. Cut, copy, and paste functionality exists in ONE code location (not duplicated across UI elements)
2. Users can undo their actions
3. Multiple UI elements (menu items, toolbar buttons, keyboard shortcuts) can trigger the same operations

## Solution Architecture

### Command Pattern Implementation

The Command Pattern encapsulates requests as objects, allowing:

- Parameterization of clients with different requests
- Support for undoable operations
- Decoupling of the object that invokes the operation from the one that knows how to perform it

### Components

1. **Command Interface** (`Command.java`)

   - Defines `execute()` and `undo()` methods

2. **Concrete Commands**

   - `CutCommand.java` - Encapsulates cut operation
   - `CopyCommand.java` - Encapsulates copy operation
   - `PasteCommand.java` - Encapsulates paste operation

3. **Receiver** (`DocumentEditor.java`)

   - Contains actual text manipulation logic
   - Manages document state and clipboard
   - Returns state snapshots for undo

4. **Invoker** (`CommandInvoker.java`)

   - Executes commands
   - Maintains command history stack
   - Handles undo operations

5. **UI Elements**

   - `MenuItem.java` - Menu item that triggers commands
   - `ToolbarButton.java` - Toolbar button that triggers commands
   - `KeyboardShortcut.java` - Keyboard shortcut that triggers commands

6. **Application** (`EditorApplication.java`)
   - Demo program showing the system in action

## File Structure

```
Lab-7/
├── Command.java                 # Command interface
├── CutCommand.java             # Cut command implementation
├── CopyCommand.java            # Copy command implementation
├── PasteCommand.java           # Paste command implementation
├── DocumentEditor.java         # Receiver with business logic
├── CommandInvoker.java         # Invoker for command execution
├── MenuItem.java               # Menu UI element
├── ToolbarButton.java          # Toolbar UI element
├── KeyboardShortcut.java       # Keyboard shortcut UI element
├── EditorApplication.java      # Demo application
├── SOLUTION_DESIGN.md          # Detailed design explanation
├── UML_DIAGRAM.md              # UML class and sequence diagrams
└── README.md                   # This file
```

## How to Run

### Compile

```bash
cd Lab-7
javac *.java
```

### Run Demo

```bash
java EditorApplication
```

### Expected Output

The demo will show:

1. Multiple UI elements using the same command objects
2. Cut, copy, and paste operations being performed
3. Undo operations reversing the changes
4. Command history management

## Key Features

### 1. Single Source of Functionality ✅

Each operation (cut, copy, paste) has **only ONE implementation**:

- Cut logic is in `DocumentEditor.cut()` method
- Encapsulated by `CutCommand` class
- All UI elements use the **same** `CutCommand` instance

### 2. Undo Capability ✅

- Commands store previous state before execution
- `CommandInvoker` maintains a stack of executed commands
- `undo()` method reverses the last operation
- Can undo multiple operations in sequence

### 3. Multiple UI Interfaces ✅

Three different UI elements can trigger the same operation:

- Menu: "Edit > Cut"
- Toolbar: ✂️ button
- Keyboard: Ctrl+X

All three use the **same** command object - no code duplication!

## Design Benefits

### Maintainability

- Change functionality in ONE place
- All UI elements automatically get the update
- No risk of inconsistent behavior

### Extensibility

- Add new commands easily (SelectAll, Find, Replace)
- Add new UI elements without modifying existing code
- Add redo functionality by maintaining a second stack

### Testability

- Each component can be tested independently
- Commands can be tested without UI
- Easy to mock dependencies

### Flexibility

- Commands can be queued, logged, or scheduled
- Macro recording capability
- Transaction support

## SOLID Principles

### Single Responsibility

- DocumentEditor: Text manipulation
- Commands: Encapsulate requests
- Invoker: Command execution and history
- UI: User interaction

### Open/Closed

- Open for extension (new commands)
- Closed for modification (existing code)

### Liskov Substitution

- All commands implement Command interface
- Can be used interchangeably

### Interface Segregation

- Command interface is minimal and focused

### Dependency Inversion

- UI depends on Command abstraction
- Not tied to concrete implementations

## Example Usage

```java
// Create editor and invoker
DocumentEditor editor = new DocumentEditor();
CommandInvoker invoker = new CommandInvoker();

// Create command (ONE instance)
CutCommand cutCommand = new CutCommand(editor);

// Create UI elements using the SAME command
MenuItem cutMenu = new MenuItem("Edit > Cut", cutCommand, invoker);
ToolbarButton cutBtn = new ToolbarButton("✂️", cutCommand, invoker);
KeyboardShortcut cutKey = new KeyboardShortcut("Ctrl+X", cutCommand, invoker);

// All three do the same thing - no code duplication
cutMenu.click();     // or
cutBtn.click();      // or
cutKey.press();      // All execute the same command

// Undo
invoker.undo();      // Reverses the last operation
```

## Extending the System

### Add a New Command

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

### Add Redo Functionality

```java
class CommandInvoker {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public void executeCommand(Command cmd) {
        cmd.execute();
        undoStack.push(cmd);
        redoStack.clear(); // Clear redo stack on new action
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pop();
            cmd.execute();
            undoStack.push(cmd);
        }
    }
}
```

## Documentation

- **SOLUTION_DESIGN.md** - Comprehensive design explanation, benefits, and comparisons
- **UML_DIAGRAM.md** - UML class diagrams and sequence diagrams with detailed annotations

## Comparison: With vs Without Command Pattern

### Without Command Pattern ❌

```java
class MenuItem {
    void onClick() {
        // Direct implementation - duplicated everywhere!
        String selected = editor.getSelected();
        editor.setClipboard(selected);
        editor.delete(selected);
        // No easy way to undo
    }
}
```

### With Command Pattern ✅

```java
class MenuItem {
    void onClick() {
        invoker.executeCommand(cutCommand); // Clean, reusable, undoable!
    }
}
```

## Conclusion

This implementation demonstrates how the Command Pattern elegantly solves the design problem by:

1. ✅ Centralizing functionality (single source of truth)
2. ✅ Enabling undo/redo operations
3. ✅ Allowing multiple UI elements to share the same command objects
4. ✅ Following SOLID principles and best practices
5. ✅ Creating maintainable, testable, and extensible code

## Author

Solution for CS-380 Design Problem #2

## References

- "Design Patterns: Elements of Reusable Object-Oriented Software" by Gang of Four
- Command Pattern: https://refactoring.guru/design-patterns/command
