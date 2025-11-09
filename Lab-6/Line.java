/**
 * Line - A simple Shape implementation for drawing lines
 */
public class Line extends Shape {
    
    private double x1, y1; // Start point
    private double x2, y2; // End point
    private Animator animator;
    
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.animator = new LineAnimator();
    }
    
    @Override
    public double[] getBoundingBox() {
        // Calculate bounding box from line endpoints
        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double width = Math.abs(x2 - x1);
        double height = Math.abs(y2 - y1);
        
        return new double[]{minX, minY, width, height};
    }
    
    @Override
    public Animator getAnimator() {
        return animator;
    }
    
    @Override
    public void display() {
        System.out.println("\n--- Displaying Line ---");
        System.out.println("From: (" + x1 + ", " + y1 + ") to (" + x2 + ", " + y2 + ")");
        double[] bbox = getBoundingBox();
        System.out.println("Bounding Box: [x=" + bbox[0] + ", y=" + bbox[1] + 
                         ", width=" + bbox[2] + ", height=" + bbox[3] + "]");
    }
    
    /**
     * LineAnimator - Handles user interaction for lines
     */
    private class LineAnimator extends Animator {
        @Override
        public void handleClick(double x, double y) {
            System.out.println("Line clicked at (" + x + ", " + y + ")");
        }
        
        @Override
        public void handleDrag(double startX, double startY, double endX, double endY) {
            System.out.println("Line dragged from (" + startX + ", " + startY + 
                             ") to (" + endX + ", " + endY + ")");
        }
        
        @Override
        public String getType() {
            return "LineAnimator";
        }
    }
}

