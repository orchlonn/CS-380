/**
 * Polygon - A simple Shape implementation for drawing polygons
 */
public class Polygon extends Shape {
    
    private double[] xPoints;
    private double[] yPoints;
    private int numPoints;
    private Animator animator;
    
    public Polygon(double[] xPoints, double[] yPoints) {
        if (xPoints.length != yPoints.length) {
            throw new IllegalArgumentException("X and Y arrays must have same length");
        }
        this.xPoints = xPoints.clone();
        this.yPoints = yPoints.clone();
        this.numPoints = xPoints.length;
        this.animator = new PolygonAnimator();
    }
    
    @Override
    public double[] getBoundingBox() {
        // Find min/max coordinates
        double minX = xPoints[0];
        double maxX = xPoints[0];
        double minY = yPoints[0];
        double maxY = yPoints[0];
        
        for (int i = 1; i < numPoints; i++) {
            minX = Math.min(minX, xPoints[i]);
            maxX = Math.max(maxX, xPoints[i]);
            minY = Math.min(minY, yPoints[i]);
            maxY = Math.max(maxY, yPoints[i]);
        }
        
        double width = maxX - minX;
        double height = maxY - minY;
        
        return new double[]{minX, minY, width, height};
    }
    
    @Override
    public Animator getAnimator() {
        return animator;
    }
    
    @Override
    public void display() {
        System.out.println("\n--- Displaying Polygon ---");
        System.out.print("Vertices: ");
        for (int i = 0; i < numPoints; i++) {
            System.out.print("(" + xPoints[i] + ", " + yPoints[i] + ")");
            if (i < numPoints - 1) System.out.print(", ");
        }
        System.out.println();
        double[] bbox = getBoundingBox();
        System.out.println("Bounding Box: [x=" + bbox[0] + ", y=" + bbox[1] + 
                         ", width=" + bbox[2] + ", height=" + bbox[3] + "]");
    }
    
    /**
     * PolygonAnimator - Handles user interaction for polygons
     */
    private class PolygonAnimator extends Animator {
        @Override
        public void handleClick(double x, double y) {
            System.out.println("Polygon clicked at (" + x + ", " + y + ")");
        }
        
        @Override
        public void handleDrag(double startX, double startY, double endX, double endY) {
            System.out.println("Polygon dragged from (" + startX + ", " + startY + 
                             ") to (" + endX + ", " + endY + ")");
        }
        
        @Override
        public String getType() {
            return "PolygonAnimator";
        }
    }
}

