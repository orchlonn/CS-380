/**
 * TextView - Off-the-shelf UI toolkit class for displaying and editing text
 * This is the Adaptee class - we cannot modify this interface
 * It comes from an external toolkit
 */
public class TextView {
    
    private double originX;
    private double originY;
    private double width;
    private double height;
    private String text;
    
    /**
     * Constructor for TextView
     */
    public TextView(double x, double y, double width, double height, String text) {
        this.originX = x;
        this.originY = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }
    
    /**
     * Gets the origin (lower-left corner) of the text view
     * Returns an array [x, y]
     */
    public double[] getOrigin() {
        return new double[]{originX, originY};
    }
    
    /**
     * Gets the width of the text view
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Gets the height of the text view
     */
    public double getHeight() {
        return height;
    }
    
    /**
     * Checks if the text widget is empty (has no text)
     */
    public boolean isEmpty() {
        return text == null || text.trim().isEmpty();
    }
    
    /**
     * Gets the text content
     */
    public String getText() {
        return text;
    }
    
    /**
     * Sets the text content
     */
    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * Displays the text view (simulated)
     */
    public void renderText() {
        System.out.println("Rendering TextView:");
        System.out.println("  Origin: (" + originX + ", " + originY + ")");
        System.out.println("  Width: " + width + ", Height: " + height);
        System.out.println("  Text: \"" + text + "\"");
        System.out.println("  Empty: " + isEmpty());
    }
}

