# UML Diagram - Adapter Pattern Solution for Design Problem #1

## Overview

This document describes the UML class diagram showing how TextView is adapted to work with the Shape interface using the Adapter Pattern.

## UML Class Diagram (Text Representation)

```
┌─────────────────────────────────────────────────────────────┐
│                          <<abstract>>                        │
│                            Shape                             │
├─────────────────────────────────────────────────────────────┤
│                                                              │
├─────────────────────────────────────────────────────────────┤
│ + getBoundingBox() : double[]                               │
│ + getAnimator() : Animator                                  │
│ + display() : void                                          │
└─────────────────────────────────────────────────────────────┘
                              △
                              │
                              │ extends
          ┌───────────────────┼───────────────────┐
          │                   │                   │
          │                   │                   │
┌─────────┴─────────┐ ┌──────┴────────┐ ┌────────┴─────────┐
│       Line        │ │    Polygon    │ │     TextBox      │
├───────────────────┤ ├───────────────┤ ├──────────────────┤
│ - x1, y1 : double │ │ - xPoints[]   │ │ - textView       │
│ - x2, y2 : double │ │ - yPoints[]   │ │ - animator       │
│ - animator        │ │ - numPoints   │ │                  │
├───────────────────┤ │ - animator    │ ├──────────────────┤
│ + getBoundingBox()│ ├───────────────┤ │ + getBoundingBox()│
│ + getAnimator()   │ │ + getBoundingBox()│ + getAnimator()  │
│ + display()       │ │ + getAnimator()│ │ + display()      │
└───────────────────┘ │ + display()   │ │ + getTextView()  │
                      └───────────────┘ └──────────────────┘
                                                 │
                                                 │ adapts/contains
                                                 │
                                        ┌────────▼─────────┐
                                        │     TextView     │
                                        ├──────────────────┤
                                        │ - originX        │
                                        │ - originY        │
                                        │ - width          │
                                        │ - height         │
                                        │ - text           │
                                        ├──────────────────┤
                                        │ + getOrigin()    │
                                        │ + getWidth()     │
                                        │ + getHeight()    │
                                        │ + isEmpty()      │
                                        │ + getText()      │
                                        │ + setText()      │
                                        │ + renderText()   │
                                        └──────────────────┘


┌─────────────────────────────────────────────────────────────┐
│                        <<abstract>>                          │
│                          Animator                            │
├─────────────────────────────────────────────────────────────┤
│                                                              │
├─────────────────────────────────────────────────────────────┤
│ + handleClick(x: double, y: double) : void                  │
│ + handleDrag(startX, startY, endX, endY: double) : void     │
│ + getType() : String                                        │
└─────────────────────────────────────────────────────────────┘
                              △
                              │
                              │ extends
                              │
                    ┌─────────┴─────────┐
                    │   TextAnimator    │
                    ├───────────────────┤
                    │                   │
                    ├───────────────────┤
                    │ + handleClick()   │
                    │ + handleDrag()    │
                    │ + getType()       │
                    └───────────────────┘
```

## Key Relationships

### 1. **Target Interface: Shape (abstract class)**

- Defines the interface that the drawing editor expects
- Methods: `getBoundingBox()`, `getAnimator()`, `display()`

### 2. **Adaptee: TextView**

- The off-the-shelf class from external UI toolkit
- Has incompatible interface: `getOrigin()`, `getWidth()`, `getHeight()`, `isEmpty()`
- Cannot be modified (no source code access)

### 3. **Adapter: TextBox**

- Extends Shape (Target Interface)
- Contains TextView (Adaptee) via composition
- Translates Shape method calls to TextView method calls
- **This is the key to the Adapter Pattern!**

### 4. **Concrete Implementations: Line, Polygon**

- Directly implement Shape interface
- Show that Shape works well for simple geometric objects

### 5. **Helper: Animator**

- Abstract class for handling user interactions
- Each shape has an associated animator

## How the Adapter Pattern Works

### Problem:

- Drawing editor expects all shapes to implement the `Shape` interface
- `TextView` has a different interface and cannot be modified
- We want to use `TextView` as a `TextBox` shape in our editor

### Solution (Adapter Pattern):

1. **TextBox extends Shape** - Makes TextBox compatible with drawing editor
2. **TextBox contains TextView** - Uses composition to wrap TextView
3. **TextBox translates method calls**:
   - `getBoundingBox()` → calls `textView.getOrigin()`, `getWidth()`, `getHeight()`
   - `display()` → calls `textView.renderText()`
   - `getAnimator()` → returns appropriate animator

### Key Adapter Method Implementation:

```java
// TextBox adapts TextView to Shape interface
public class TextBox extends Shape {
    private TextView textView;  // Adaptee

    @Override
    public double[] getBoundingBox() {
        // Adapt TextView's interface to Shape's interface
        double[] origin = textView.getOrigin();  // TextView method
        double width = textView.getWidth();       // TextView method
        double height = textView.getHeight();     // TextView method

        // Return in Shape's expected format
        return new double[]{origin[0], origin[1], width, height};
    }
}
```

## Benefits of This Design

1. **No modification to TextView** - Source code remains unchanged
2. **Seamless integration** - TextBox works like any other Shape
3. **Polymorphism** - Can store all shapes (Line, Polygon, TextBox) in same collection
4. **Maintainability** - If TextView interface changes, only TextBox needs updating
5. **Reusability** - TextView can still be used independently elsewhere

## Pattern Classification

- **Pattern Type**: Structural Pattern
- **Intent**: Convert the interface of a class into another interface clients expect
- **Participants**:
  - **Target (Shape)**: Defines domain-specific interface
  - **Adaptee (TextView)**: Existing interface needing adaptation
  - **Adapter (TextBox)**: Adapts Adaptee to Target interface
  - **Client (DrawingEditorDemo)**: Uses objects through Target interface
