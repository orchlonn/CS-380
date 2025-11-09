# Design Problem #1 - Adapter Pattern Implementation

## Problem Summary

We need to integrate a `TextView` class from an off-the-shelf UI toolkit into a drawing editor that expects all graphical objects to implement the `Shape` interface. The challenge is that:

1. `TextView` has an incompatible interface
2. We cannot modify `TextView` source code (it's from external toolkit)
3. We need `TextView` to work seamlessly with other `Shape` objects

## Solution: Adapter Design Pattern

The **Adapter Pattern** is used to convert the interface of `TextView` into the interface expected by the drawing editor (`Shape`). This is accomplished through the `TextBox` class, which acts as an adapter.

## Project Structure

```
Lab-6/
├── Shape.java                  # Target Interface (abstract class)
├── TextView.java               # Adaptee (external toolkit class)
├── TextBox.java                # Adapter (adapts TextView to Shape)
├── Animator.java               # Abstract class for user interaction
├── TextAnimator.java           # Concrete animator for text
├── Line.java                   # Concrete Shape implementation
├── Polygon.java                # Concrete Shape implementation
├── DrawingEditorDemo.java      # Demo/Test program
├── UML_DIAGRAM.md              # UML diagram documentation
└── README.md                   # This file
```

## Key Classes

### 1. Shape (Target Interface)

**Purpose**: Abstract class defining the interface all shapes must implement

**Methods**:

- `getBoundingBox()` - Returns [x, y, width, height] of shape
- `getAnimator()` - Returns animator for user interaction
- `display()` - Displays the shape

### 2. TextView (Adaptee)

**Purpose**: External UI toolkit class with incompatible interface

**Methods**:

- `getOrigin()` - Returns [x, y] of lower-left corner
- `getWidth()` - Returns width
- `getHeight()` - Returns height
- `isEmpty()` - Checks if text is empty
- `renderText()` - Displays the text

**Note**: This class cannot be modified!

### 3. TextBox (Adapter) ⭐

**Purpose**: Adapts TextView to work with Shape interface

**How it works**:

```java
public class TextBox extends Shape {
    private TextView textView;  // Contains the adaptee

    // Adapts TextView methods to Shape interface
    public double[] getBoundingBox() {
        double[] origin = textView.getOrigin();    // TextView method
        double width = textView.getWidth();         // TextView method
        double height = textView.getHeight();       // TextView method

        // Return in Shape's expected format
        return new double[]{origin[0], origin[1], width, height};
    }

    public Animator getAnimator() {
        return animator;  // Provides animator as Shape expects
    }

    public void display() {
        textView.renderText();  // Delegates to TextView
    }
}
```

## How to Compile and Run

### Compilation

```bash
cd /Users/chinbatorchlon/Documents/school/CS-380/Lab-6
javac *.java
```

### Running the Demo

```bash
java DrawingEditorDemo
```

## Expected Output

The demo program will:

1. Create various shapes (Line, Polygon, TextBox)
2. Display all shapes using the common Shape interface
3. Demonstrate that TextBox (adapted TextView) works seamlessly with other shapes
4. Show user interaction through animators

## Key Concepts Demonstrated

### 1. Interface Adaptation

TextView's methods are adapted to Shape's interface:

- `getOrigin()` + `getWidth()` + `getHeight()` → `getBoundingBox()`
- `renderText()` → `display()`

### 2. Composition over Inheritance

TextBox contains a TextView instance rather than trying to inherit from both Shape and TextView.

### 3. Polymorphism

All shapes (Line, Polygon, TextBox) can be treated uniformly through the Shape interface:

```java
List<Shape> shapes = new ArrayList<>();
shapes.add(new Line(...));
shapes.add(new Polygon(...));
shapes.add(new TextBox(new TextView(...))); // Adapted!

for (Shape shape : shapes) {
    shape.display();  // Works for all shapes!
}
```

### 4. Open/Closed Principle

- Open for extension: New shapes can be added
- Closed for modification: TextView doesn't need to change

## Benefits of This Design

1. **No modification to TextView**: External toolkit remains unchanged
2. **Seamless integration**: TextBox works exactly like any other Shape
3. **Type safety**: Compile-time checking ensures correct usage
4. **Maintainability**: Changes to TextView interface only affect TextBox
5. **Flexibility**: TextView can still be used independently elsewhere

## UML Diagram Summary

```
Shape (abstract)
   ↑
   |--- Line (implements directly)
   |--- Polygon (implements directly)
   |--- TextBox (adapter)
           |
           |--- contains/adapts
           |
        TextView (adaptee)
```

## Design Pattern Details

- **Pattern Name**: Adapter Pattern (Object Adapter)
- **Category**: Structural Pattern
- **Intent**: Convert interface of a class into another interface clients expect
- **Also Known As**: Wrapper

### Pattern Participants

1. **Target (Shape)**: Interface expected by client
2. **Adaptee (TextView)**: Existing incompatible interface
3. **Adapter (TextBox)**: Adapts Adaptee to Target
4. **Client (DrawingEditorDemo)**: Uses Target interface

## Answer to Design Problem Requirements

### Requirement 1: UML Diagram

See `UML_DIAGRAM.md` for complete UML class diagram showing:

- Shape as abstract target interface
- TextView as adaptee
- TextBox as adapter connecting them
- Relationships between all classes

### Requirement 2: Java Implementation

The Java code demonstrates:

1. **TextBox.getBoundingBox()** implementation:

   ```java
   public double[] getBoundingBox() {
       double[] origin = textView.getOrigin();
       return new double[]{origin[0], origin[1],
                          textView.getWidth(), textView.getHeight()};
   }
   ```

2. **TextBox.getAnimator()** implementation:

   ```java
   public Animator getAnimator() {
       return animator;  // TextAnimator instance
   }
   ```

3. **TextBox.display()** implementation:
   ```java
   public void display() {
       textView.renderText();  // Delegates to TextView
   }
   ```

## Testing

Run `DrawingEditorDemo` to see:

- Multiple shapes in a collection
- TextBox working alongside Line and Polygon
- All shapes displayed through common interface
- User interaction handling

## References

- Gang of Four: Design Patterns: Elements of Reusable Object-Oriented Software
- Head First Design Patterns
- Course material: Lab Assignment #6
