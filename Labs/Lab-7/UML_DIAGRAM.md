# Lab 7 - Command Pattern UML Diagram

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                          <<interface>>                          │
│                            Command                              │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
├─────────────────────────────────────────────────────────────────┤
│ + execute() : void                                              │
│ + undo() : void                                                 │
└─────────────────────────────────────────────────────────────────┘
                                   △
                                   │
                                   │ implements
          ┌────────────────────────┼────────────────────────┐
          │                        │                        │
          │                        │                        │
┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
│   CutCommand        │  │   CopyCommand       │  │   PasteCommand      │
├─────────────────────┤  ├─────────────────────┤  ├─────────────────────┤
│ - doc: Document     │  │ - doc: Document     │  │ - doc: Document     │
│ - backup: String    │  │                     │  │ - backup: String    │
├─────────────────────┤  ├─────────────────────┤  ├─────────────────────┤
│ + execute(): void   │  │ + execute(): void   │  │ + execute(): void   │
│ + undo(): void      │  │ + undo(): void      │  │ + undo(): void      │
└─────────────────────┘  └─────────────────────┘  └─────────────────────┘
          │                        │                        │
          └────────────────────────┼────────────────────────┘
                                   │ uses
                                   ▼
                        ┌─────────────────────┐
                        │      Document       │
                        ├─────────────────────┤
                        │ - text: StringBuilder│
                        │ - clipboard: String │
                        ├─────────────────────┤
                        │ + addText(String)   │
                        │ + cut(): String     │
                        │ + copy(): String    │
                        │ + paste(): void     │
                        │ + getText(): String │
                        │ + setText(String)   │
                        │ + getClipboard()    │
                        └─────────────────────┘
                                   △
                                   │ operates on
                                   │
                        ┌─────────────────────┐
                        │  CommandInvoker     │
                        ├─────────────────────┤
                        │ - history: Stack    │
                        ├─────────────────────┤
                        │ + execute(Command)  │
                        │ + undo(): void      │
                        └─────────────────────┘
                                   △
                                   │ used by
          ┌────────────────────────┼────────────────────────┐
          │                        │                        │
┌─────────────────────┐  ┌─────────────────────┐  ┌─────────────────────┐
│       Menu          │  │      Toolbar        │  │  KeyboardShortcut   │
├─────────────────────┤  ├─────────────────────┤  ├─────────────────────┤
│ - invoker           │  │ - invoker           │  │ - invoker           │
├─────────────────────┤  ├─────────────────────┤  ├─────────────────────┤
│ + onCutClick()      │  │ + onCutButton()     │  │ + onCtrlX()         │
│ + onCopyClick()     │  │ + onCopyButton()    │  │ + onCtrlC()         │
│ + onPasteClick()    │  │ + onPasteButton()   │  │ + onCtrlV()         │
└─────────────────────┘  └─────────────────────┘  └─────────────────────┘
```

## Key Design Points

### ✅ Single Source Code Location

Each functionality has **ONE implementation location**:

1. **Cut Functionality**:

   - **Single Location**: `CutCommand.execute()` method
   - Used by: Menu, Toolbar, KeyboardShortcut (all call `invoker.execute(new CutCommand(doc))`)

2. **Copy Functionality**:

   - **Single Location**: `CopyCommand.execute()` method
   - Used by: Menu, Toolbar, KeyboardShortcut (all call `invoker.execute(new CopyCommand(doc))`)

3. **Paste Functionality**:
   - **Single Location**: `PasteCommand.execute()` method
   - Used by: Menu, Toolbar, KeyboardShortcut (all call `invoker.execute(new PasteCommand(doc))`)

### ✅ Undo Functionality

- **Single Location**: `CommandInvoker.undo()` method
- Maintains a history stack of executed commands
- Each command knows how to undo itself through its `undo()` method

## Pattern Benefits

1. **No Code Duplication**: Menu items, toolbar buttons, and keyboard shortcuts all use the same command objects
2. **Easy Maintenance**: Changes to cut/copy/paste logic only need to be made in one place
3. **Undo Support**: Centralized undo mechanism that works for all commands
4. **Extensibility**: New commands can be added without modifying existing code

## Sequence Diagram Example

### Cut Operation from Menu:

```
Menu          CommandInvoker      CutCommand         Document
  │                  │                 │                 │
  │──onCutClick()──▶ │                 │                 │
  │                  │                 │                 │
  │          execute(new CutCommand)   │                 │
  │                  │─────create────▶ │                 │
  │                  │                 │                 │
  │                  │───execute()───▶ │                 │
  │                  │                 │───getText()───▶ │
  │                  │                 │◀──"Hello"─────  │
  │                  │                 │                 │
  │                  │                 │────cut()──────▶ │
  │                  │                 │                 │
  │                  │◀────done────────│                 │
  │                  │                 │                 │
  │                  │─push to stack─▶ │                 │
  │◀────done─────────│                 │                 │
```

### Same Cut Operation from Toolbar:

```
Toolbar       CommandInvoker      CutCommand         Document
  │                  │                 │                 │
  │─onCutButton()──▶ │                 │                 │
  │                  │                 │                 │
  │          execute(new CutCommand)   │                 │
  │                  │─────create────▶ │                 │
  │                  │                 │                 │
  │                  │───execute()───▶ │                 │
  │                  │                 │───getText()───▶ │
  │                  │                 │◀──"Hello"─────  │
  │                  │                 │                 │
  │                  │                 │────cut()──────▶ │
  │                  │                 │                 │
  │                  │◀────done────────│                 │
  │                  │                 │                 │
  │                  │─push to stack─▶ │                 │
  │◀────done─────────│                 │                 │
```

**Notice**: Both sequences use the **exact same CutCommand implementation**!

## Implementation Notes

- All three UI elements (Menu, Toolbar, KeyboardShortcut) delegate to the CommandInvoker
- The CommandInvoker executes commands and maintains history for undo
- Each concrete command encapsulates one operation and knows how to undo it
- The Document class contains the actual business logic for text operations
- No duplication of cut/copy/paste logic across different UI components
