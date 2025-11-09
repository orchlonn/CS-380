/**
 * TextBox - Adapter class that adapts TextView to work with the Shape interface
 * 
 * This is the Adapter in the Adapter Design Pattern.
 * It extends Shape (Target Interface) and contains a TextView (Adaptee).
 * 
 * The TextBox delegates Shape operations to the appropriate TextView operations,
 * translating between the two incompatible interfaces.
 */
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
     * Implements getBoundingBox() from Shape using TextView's methods
     * 
     * Shape expects: [x, y, width, height]
     * TextView provides: getOrigin() -> [x, y], getWidth(), getHeight()
     * 
     * This method adapts TextView's interface to Shape's expected interface
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
    
    /**
     * Implements getAnimator() from Shape
     * Returns the animator that handles user interaction with this text box
     */
    @Override
    public Animator getAnimator() {
        return animator;
    }
    
    /**
     * Implements display() from Shape using TextView's renderText()
     * This delegates the display operation to the TextView
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
        
        // Also display bounding box information from Shape's perspective
        double[] bbox = getBoundingBox();
        System.out.println("Bounding Box: [x=" + bbox[0] + ", y=" + bbox[1] + 
                         ", width=" + bbox[2] + ", height=" + bbox[3] + "]");
    }
    
    /**
     * Additional helper method to access the underlying TextView
     * (Not required by Shape, but useful for TextBox-specific operations)
     */
    public TextView getTextView() {
        return textView;
    }
}

