/**
 * Shape - Abstract class representing a graphical object in the drawing editor
 * This is the Target Interface that all shapes must implement
 */
public abstract class Shape {
    
    /**
     * Gets the bounding box of the shape
     * Returns an array: [x, y, width, height]
     * where (x, y) is the lower-left corner
     */
    public abstract double[] getBoundingBox();
    
    /**
     * Gets the animator for this shape to handle user interaction
     */
    public abstract Animator getAnimator();
    
    /**
     * Displays the shape on screen
     */
    public abstract void display();
}

