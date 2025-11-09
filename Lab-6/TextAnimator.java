/**
 * TextAnimator - Handles user interaction specifically for text elements
 */
public class TextAnimator extends Animator {
    
    @Override
    public void handleClick(double x, double y) {
        System.out.println("Text clicked at (" + x + ", " + y + ")");
        System.out.println("Opening text editor...");
    }
    
    @Override
    public void handleDrag(double startX, double startY, double endX, double endY) {
        System.out.println("Text selection from (" + startX + ", " + startY + 
                         ") to (" + endX + ", " + endY + ")");
    }
    
    @Override
    public String getType() {
        return "TextAnimator";
    }
}

