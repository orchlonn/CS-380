# Design Problem #1 - Solution Summary

## Problem Statement

A drawing editor needs to integrate an external `TextView` class into its `Shape` hierarchy, but `TextView` has an incompatible interface and cannot be modified.

---

## Requirement 1: UML Diagram

### Class Diagram Structure

```
┌─────────────────────────────────────────────────┐
│         <<abstract>> Shape                      │
├─────────────────────────────────────────────────┤
│ + getBoundingBox() : double[]                   │
│ + getAnimator() : Animator                      │
│ + display() : void                              │
└─────────────────────────────────────────────────┘
                    △
                    │ (inheritance)
        ┌───────────┼───────────┐
        │           │           │
   ┌────┴────┐ ┌───┴────┐ ┌────┴────────┐
   │  Line   │ │Polygon │ │  TextBox    │
   └─────────┘ └────────┘ └─────────────┘
                                 │
                                 │ (composition)
                                 │ contains
                                 ▼
                          ┌─────────────┐
                          │  TextView   │
                          ├─────────────┤
                          │ + getOrigin()    : double[] │
                          │ + getWidth()     : double   │
                          │ + getHeight()    : double   │
                          │ + isEmpty()      : boolean  │
                          │ + renderText()   : void     │
                          └─────────────────────────────┘
```

### Key Relationships:

1. **Shape** (Target Interface)

   - Abstract class defining the interface for all shapes
   - Methods: `getBoundingBox()`, `getAnimator()`, `display()`

2. **TextBox** (Adapter)

   - Extends `Shape` to fulfill the Target Interface
   - Contains `TextView` instance via composition
   - Implements Shape methods by delegating to TextView

3. **TextView** (Adaptee)

   - External UI toolkit class with different interface
   - Cannot be modified (no source code)
   - Methods: `getOrigin()`, `getWidth()`, `getHeight()`, `isEmpty()`, `renderText()`

4. **Line and Polygon**
   - Direct implementations of Shape
   - Show that Shape works well for simple geometric objects

### Pattern: Object Adapter

- **Why Object Adapter?** Uses composition (TextBox contains TextView) rather than multiple inheritance
- **Advantage**: More flexible, TextView can be swapped at runtime if needed

---

## Requirement 2: Java Implementation

### Implementation of Shape Operations Using TextView

#### 1. getBoundingBox() Implementation

```java
/**
 * Shape expects: double[] {x, y, width, height}
 * TextView provides: getOrigin() -> {x, y}, getWidth(), getHeight()
 */
@Override
public double[] getBoundingBox() {
    // Get the origin (lower-left corner) from TextView
    double[] origin = textView.getOrigin();

    // Get width and height from TextView
    double width = textView.getWidth();
    double height = textView.getHeight();

    // Combine into Shape's expected bounding box format
    return new double[]{origin[0], origin[1], width, height};
}
```

**Mapping**:

- TextView's `getOrigin()[0]` → x coordinate of bounding box
- TextView's `getOrigin()[1]` → y coordinate of bounding box
- TextView's `getWidth()` → width of bounding box
- TextView's `getHeight()` → height of bounding box

#### 2. getAnimator() Implementation

```java
/**
 * Shape expects: Animator object for handling user interaction
 * TextView doesn't provide this, so TextBox creates and manages it
 */
@Override
public Animator getAnimator() {
    return animator;  // TextAnimator instance created in constructor
}
```

**Mapping**:

- TextView doesn't have animator functionality
- TextBox creates a `TextAnimator` instance to handle text-specific interactions
- Returns this animator when Shape interface requires it

#### 3. display() Implementation

```java
/**
 * Shape expects: display() method to render the shape
 * TextView provides: renderText() method
 */
@Override
public void display() {
    System.out.println("\n--- Displaying TextBox (via TextView) ---");

    // Check if TextView is empty before displaying
    if (textView.isEmpty()) {
        System.out.println("TextBox is empty (no text to display)");
    } else {
        // Delegate to TextView's rendering method
        textView.renderText();
    }

    // Also display bounding box information
    double[] bbox = getBoundingBox();
    System.out.println("Bounding Box: [x=" + bbox[0] + ", y=" + bbox[1] +
                     ", width=" + bbox[2] + ", height=" + bbox[3] + "]");
}
```

**Mapping**:

- Shape's `display()` → TextView's `renderText()`
- Uses TextView's `isEmpty()` to check status
- Uses adapted `getBoundingBox()` to show shape information

---

## Complete TextBox Adapter Class

```java
public class TextBox extends Shape {

    // The TextView instance being adapted
    private TextView textView;

    // The animator for handling user interaction
    private Animator animator;

    /**
     * Constructor - creates a TextBox by wrapping a TextView
     */
    public TextBox(TextView textView) {
        this.textView = textView;
        this.animator = new TextAnimator();
    }

    /**
     * Adapts TextView's getOrigin(), getWidth(), getHeight()
     * to Shape's getBoundingBox()
     */
    @Override
    public double[] getBoundingBox() {
        double[] origin = textView.getOrigin();
        double width = textView.getWidth();
        double height = textView.getHeight();
        return new double[]{origin[0], origin[1], width, height};
    }

    /**
     * Provides animator as required by Shape interface
     */
    @Override
    public Animator getAnimator() {
        return animator;
    }

    /**
     * Adapts TextView's renderText() to Shape's display()
     */
    @Override
    public void display() {
        System.out.println("\n--- Displaying TextBox (via TextView) ---");
        if (textView.isEmpty()) {
            System.out.println("TextBox is empty (no text to display)");
        } else {
            textView.renderText();
        }
        double[] bbox = getBoundingBox();
        System.out.println("Bounding Box: [x=" + bbox[0] + ", y=" + bbox[1] +
                         ", width=" + bbox[2] + ", height=" + bbox[3] + "]");
    }

    /**
     * Helper method to access underlying TextView
     */
    public TextView getTextView() {
        return textView;
    }
}
```

---

## How This Solves the Problem

### ✅ Without Changing TextView Interface

- TextView source code is never modified
- All adaptation happens in TextBox
- TextView remains usable in its original context

### ✅ TextView and Shape Objects Used Interchangeably

```java
List<Shape> shapes = new ArrayList<>();

// Add regular shapes
shapes.add(new Line(10, 10, 100, 50));
shapes.add(new Polygon(xPoints, yPoints));

// Add adapted TextView as TextBox
TextView textView = new TextView(50, 100, 200, 50, "Hello!");
shapes.add(new TextBox(textView));

// All shapes work uniformly through Shape interface!
for (Shape shape : shapes) {
    shape.display();              // Works for all
    shape.getBoundingBox();       // Works for all
    shape.getAnimator();          // Works for all
}
```

### ✅ Design Benefits

1. **Single Responsibility**: TextBox only handles adaptation
2. **Open/Closed**: Open for extension, closed for modification
3. **Liskov Substitution**: TextBox can replace Shape anywhere
4. **Dependency Inversion**: Depends on Shape abstraction

---

## Files Implemented

1. **Shape.java** - Abstract target interface
2. **TextView.java** - Simulated external toolkit class (adaptee)
3. **TextBox.java** - Adapter implementation ⭐
4. **Animator.java** - Abstract animator class
5. **TextAnimator.java** - Concrete animator for text
6. **Line.java** - Example concrete Shape
7. **Polygon.java** - Example concrete Shape
8. **DrawingEditorDemo.java** - Demonstration program

---

## Conclusion

The **Adapter Pattern** successfully allows TextView to be used in the drawing editor without:

- Modifying TextView's source code
- Breaking existing code
- Sacrificing type safety or polymorphism

The TextBox adapter seamlessly translates between TextView's interface (`getOrigin()`, `getWidth()`, etc.) and Shape's interface (`getBoundingBox()`, `getAnimator()`, etc.), enabling TextView to work alongside other shapes in the drawing editor.
