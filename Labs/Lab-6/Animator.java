/**
 * Animator - Handles user interaction with shapes
 */
public abstract class Animator {
    
    /**
     * Handles user click events
     */
    public abstract void handleClick(double x, double y);
    
    /**
     * Handles user drag events
     */
    public abstract void handleDrag(double startX, double startY, double endX, double endY);
    
    /**
     * Gets the type of animator
     */
    public abstract String getType();
}

