# UML Class Diagram - Command Pattern Implementation

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           <<interface>>                                     │
│                             Command                                         │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│ + execute(): void                                                           │
│ + undo(): void                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
                                    △
                                    │
                                    │ implements
                    ┌───────────────┼───────────────┐
                    │               │               │
         ┌──────────┴─────────┐ ┌──┴──────────┐ ┌─┴────────────┐
         │    CutCommand      │ │ CopyCommand │ │ PasteCommand │
         ├────────────────────┤ ├─────────────┤ ├──────────────┤
         │ - editor           │ │ - editor    │ │ - editor     │
         │ - previousState    │ │ - prevState │ │ - prevState  │
         ├────────────────────┤ ├─────────────┤ ├──────────────┤
         │ + execute(): void  │ │ + execute() │ │ + execute()  │
         │ + undo(): void     │ │ + undo()    │ │ + undo()     │
         └────────────────────┘ └─────────────┘ └──────────────┘
                    │               │               │
                    │               │               │
                    │   calls       │   calls       │   calls
                    └───────────────┼───────────────┘
                                    │
                                    ▼
         ┌──────────────────────────────────────────────────────┐
         │              DocumentEditor                          │
         ├──────────────────────────────────────────────────────┤
         │ - content: StringBuilder                             │
         │ - clipboard: String                                  │
         │ - selectedText: String                               │
         │ - selectionStart: int                                │
         │ - selectionEnd: int                                  │
         ├──────────────────────────────────────────────────────┤
         │ + setContent(text: String): void                     │
         │ + getContent(): String                               │
         │ + setSelection(start: int, end: int): void           │
         │ + getSelectedText(): String                          │
         │ + cut(): EditorState                                 │
         │ + copy(): EditorState                                │
         │ + paste(): EditorState                               │
         │ + restoreState(state: EditorState): void             │
         └──────────────────────────────────────────────────────┘
                                    │
                                    │ uses
                                    ▼
         ┌──────────────────────────────────────────────────────┐
         │         DocumentEditor.EditorState                   │
         ├──────────────────────────────────────────────────────┤
         │ - content: String                                    │
         │ - clipboard: String                                  │
         │ - selectedText: String                               │
         │ - selectionStart: int                                │
         │ - selectionEnd: int                                  │
         ├──────────────────────────────────────────────────────┤
         │ + getContent(): String                               │
         │ + getClipboard(): String                             │
         │ + getSelectedText(): String                          │
         │ + getSelectionStart(): int                           │
         │ + getSelectionEnd(): int                             │
         └──────────────────────────────────────────────────────┘


         ┌──────────────────────────────────────────────────────┐
         │              CommandInvoker                          │
         ├──────────────────────────────────────────────────────┤
         │ - commandHistory: Stack<Command>                     │
         ├──────────────────────────────────────────────────────┤
         │ + executeCommand(command: Command): void             │
         │ + undo(): void                                       │
         │ + canUndo(): boolean                                 │
         │ + clearHistory(): void                               │
         │ + getHistorySize(): int                              │
         └──────────────────────────────────────────────────────┘
                                    △
                                    │ uses
                    ┌───────────────┼───────────────┐
                    │               │               │
         ┌──────────┴─────────┐ ┌──┴──────────────┐ ┌──────────┴────────┐
         │    MenuItem        │ │ ToolbarButton   │ │ KeyboardShortcut  │
         ├────────────────────┤ ├─────────────────┤ ├───────────────────┤
         │ - label: String    │ │ - icon: String  │ │ - keyCombo: String│
         │ - command: Command │ │ - command: Cmd  │ │ - command: Command│
         │ - invoker: Invoker │ │ - invoker: Inv  │ │ - invoker: Invoker│
         ├────────────────────┤ ├─────────────────┤ ├───────────────────┤
         │ + click(): void    │ │ + click(): void │ │ + press(): void   │
         │ + getLabel():Str   │ │ + getIcon():Str │ │ + getKeyCombo():S │
         └────────────────────┘ └─────────────────┘ └───────────────────┘
                    │               │               │
                    │               │               │
                    │   share the same Command instances
                    └───────────────┼───────────────┘
                                    │
                                    ▼
                          (Same Command Object)


         ┌──────────────────────────────────────────────────────┐
         │           EditorApplication (Main)                   │
         ├──────────────────────────────────────────────────────┤
         │                                                      │
         ├──────────────────────────────────────────────────────┤
         │ + main(args: String[]): void                         │
         └──────────────────────────────────────────────────────┘
```

## Sequence Diagram: User Cuts Text

```
User        MenuItem    CommandInvoker    CutCommand    DocumentEditor
 │              │              │              │              │
 │─click()────▶│              │              │              │
 │              │              │              │              │
 │              │─executeCmd(cutCommand)────▶│              │
 │              │              │              │              │
 │              │              │─execute()──▶│              │
 │              │              │              │              │
 │              │              │              │──cut()─────▶│
 │              │              │              │              │
 │              │              │              │              │ Performs cut
 │              │              │              │              │ Returns state
 │              │              │              │◀─EditorState─│
 │              │              │              │              │
 │              │              │              │ Stores state │
 │              │              │              │ for undo     │
 │              │              │              │              │
 │              │              │◀─────────────┤              │
 │              │              │              │              │
 │              │              │ Adds to      │              │
 │              │              │ history      │              │
 │              │              │              │              │
 │◀──────────────────────────────────────────────────────────┤
 │              │              │              │              │
```

## Sequence Diagram: User Undoes Last Action

```
User    CommandInvoker    CutCommand    DocumentEditor
 │            │              │              │
 │─undo()───▶│              │              │
 │            │              │              │
 │            │ Pops last    │              │
 │            │ command      │              │
 │            │              │              │
 │            │─undo()─────▶│              │
 │            │              │              │
 │            │              │─restoreState(previousState)─▶│
 │            │              │              │
 │            │              │              │ Restores state
 │            │              │              │
 │            │              │◀─────────────┤
 │            │              │              │
 │            │◀─────────────┤              │
 │            │              │              │
 │◀───────────┤              │              │
 │            │              │              │
```

## Key Relationships

### 1. Command Pattern Structure

- **Command (Interface)**: Defines execute() and undo() contract
- **Concrete Commands**: CutCommand, CopyCommand, PasteCommand implement Command
- **Receiver**: DocumentEditor contains actual business logic
- **Invoker**: CommandInvoker executes commands and manages history
- **Client**: UI elements (MenuItem, ToolbarButton, KeyboardShortcut)

### 2. Object Sharing

- **Single Command Instance**: Each command type has ONE instance shared by all UI elements
- **Example**: The same `CutCommand` object is used by:
  - Edit menu's Cut item
  - Toolbar's cut button (✂️)
  - Keyboard shortcut (Ctrl+X)

### 3. Undo Mechanism

- Commands store previous state (Memento pattern)
- Invoker maintains Stack of executed commands
- Undo pops command and calls its undo() method
- Command restores previous state to DocumentEditor

## Design Advantages

### 1. **Single Responsibility Principle**

- DocumentEditor: Text manipulation logic
- Command classes: Encapsulate requests
- CommandInvoker: Command execution and history
- UI elements: User interaction

### 2. **Open/Closed Principle**

- Open for extension: Add new commands easily
- Closed for modification: Existing code unchanged

### 3. **Dependency Inversion**

- UI depends on Command interface, not concrete implementations
- Easy to swap or add commands

### 4. **No Code Duplication**

- Cut logic exists in ONE place: DocumentEditor.cut()
- All UI elements use the SAME CutCommand instance
- Maintenance is simple: change once, affects all UI elements

## Pattern Collaborations

```
Client (UI)  ──creates──▶  ConcreteCommand  ──references──▶  Receiver
     │                            │                         (DocumentEditor)
     │                            │
     └─────calls────▶  Invoker  ──stores──▶  Command (interface)
                         │                        △
                         │                        │
                         └────executes────────────┘
```

## Notes

- **Memento Pattern**: EditorState acts as a memento, storing document state snapshots
- **Encapsulation**: Commands don't expose how they work internally
- **Flexibility**: Can easily add redo, macro recording, command logging, etc.
- **Testability**: Each component can be tested independently

## Alternative: Without Command Pattern (Anti-pattern)

```
MenuItem ──directly calls──▶ DocumentEditor.cut()
                              (no undo, no history)
ToolbarButton ──directly calls──▶ DocumentEditor.cut()
                                   (duplicated logic in event handlers)
KeyboardShortcut ──directly calls──▶ DocumentEditor.cut()
                                      (tight coupling)
```

**Problems:**

- ❌ No undo capability
- ❌ Logic duplicated in each UI element's event handler
- ❌ Tight coupling between UI and business logic
- ❌ Hard to maintain and extend

## Summary

This UML diagram shows how the Command Pattern:

1. ✅ Provides **one source code location** for each operation
2. ✅ Enables **undo functionality** through state management
3. ✅ Allows **multiple UI elements** to use the same command objects
4. ✅ Maintains **loose coupling** and high cohesion
5. ✅ Follows **SOLID principles**
