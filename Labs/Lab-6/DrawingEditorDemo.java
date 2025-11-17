import java.util.*;

/**
 * Author: Orchlon Chinbat
 * Student ID: 50291063
 * Description: This program demonstrates the Adapter Pattern by creating a collection of shapes that includes a Line, a Polygon, and three TextBoxes.
 * The TextBoxes are adapted from TextView objects, which have an incompatible interface with the Shape interface. The program demonstrates how the Adapter Pattern allows objects with incompatible interfaces to work together by creating an adapter that converts the incompatible interface into a compatible one.
 */
public class DrawingEditorDemo {
    
    public static void main(String[] args) {
        System.out.println("Drawing Editor - Adapter Pattern Demo \n"); 
        // Create a collection of shapes
        List<Shape> shapes = new ArrayList<>();
        
        // Add a Line shape (implements Shape directly)
        Line line = new Line(10.0, 10.0, 100.0, 50.0);
        shapes.add(line);
        
        // Add a Polygon shape (implements Shape directly)
        double[] xPoints = {20.0, 80.0, 100.0, 50.0};
        double[] yPoints = {20.0, 20.0, 80.0, 100.0};
        Polygon polygon = new Polygon(xPoints, yPoints);
        shapes.add(polygon);
        
        // Create TextView objects (from external toolkit - incompatible interface)
        TextView textView1 = new TextView(50.0, 100.0, 200.0, 50.0, "Hello, World!");
        TextView textView2 = new TextView(150.0, 200.0, 150.0, 30.0, "Adapter Pattern");
        TextView emptyTextView = new TextView(300.0, 50.0, 100.0, 40.0, "");
        
        // Adapt TextView objects to Shape interface using TextBox adapter
        TextBox textBox1 = new TextBox(textView1);
        TextBox textBox2 = new TextBox(textView2);
        TextBox textBox3 = new TextBox(emptyTextView);
        
        // Add adapted TextBox shapes to the collection
        shapes.add(textBox1);
        shapes.add(textBox2);
        shapes.add(textBox3);
        
        System.out.println("Drawing all shapes in the editor:\n");
        
        // Display all shapes using the common Shape interface
        // This works because TextBox adapts TextView to the Shape interface!
        for (Shape shape : shapes) {
            shape.display();
        }
        
        System.out.println("\nTesting User Interaction (Animators)\n");
        
        // Demonstrate that all shapes can handle user interaction
        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            System.out.println("Shape " + (i + 1) + " (" + shape.getClass().getSimpleName() + "):");
            
            Animator animator = shape.getAnimator();
            System.out.println("  Animator Type: " + animator.getType());
            
            // Simulate click
            animator.handleClick(50.0, 50.0);
            System.out.println();
        }
        
        System.out.println("Key Points of the Adapter Pattern:\n");
        System.out.println("1. TextView has incompatible interface (getOrigin, getWidth, etc.)");
        System.out.println("2. Shape expects different interface (getBoundingBox, getAnimator)");
        System.out.println("3. TextBox ADAPTS TextView to work with Shape interface");
        System.out.println("4. No modification to TextView source code needed!");
        System.out.println("5. All shapes can now be used interchangeably in collections");
    }
}

