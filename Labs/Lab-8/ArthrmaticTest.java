import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ArthrmaticTest {
    
    private Arthmatic arthmatic;
    
    @BeforeEach
    public void setUp() {
        arthmatic = new Arthmatic();
    }
    
    // Tests for multiply() 
    
    @Test
    public void testMultiply_PositiveNumbers() {
        assertEquals(20, arthmatic.multiply(4, 5));
    }
    
    @Test
    public void testMultiply_NegativeNumbers() {
        assertEquals(20, arthmatic.multiply(-4, -5));
    }
    
    @Test
    public void testMultiply_PositiveAndNegative() {
        assertEquals(-20, arthmatic.multiply(4, -5));
    }
    
    @Test
    public void testMultiply_WithZero() {
        assertEquals(0, arthmatic.multiply(5, 0));
    }
    
    @Test
    public void testMultiply_MaxValue() {
        assertEquals(-2147483648, arthmatic.multiply(Integer.MAX_VALUE, 2));
    }
    
    // Tests for subtract() 
    
    @Test
    public void testSubtract_PositiveNumbers() {
        assertEquals(3, arthmatic.subtract(10, 7));
    }
    
    @Test
    public void testSubtract_NegativeResult() {
        assertEquals(-5, arthmatic.subtract(5, 10));
    }
    
    @Test
    public void testSubtract_NegativeNumbers() {
        assertEquals(-5, arthmatic.subtract(-10, -5));
    }
    
    @Test
    public void testSubtract_WithZero() {
        assertEquals(10, arthmatic.subtract(10, 0));
    }
    
    @Test
    public void testSubtract_SameNumbers() {
        assertEquals(0, arthmatic.subtract(15, 15));
    }
    
    // Tests for add() 
    
    @Test
    public void testAdd_PositiveNumbers() {
        assertEquals(15, arthmatic.add(7, 8));
    }
    
    @Test
    public void testAdd_NegativeNumbers() {
        assertEquals(-15, arthmatic.add(-7, -8));
    }
    
    @Test
    public void testAdd_PositiveAndNegative() {
        assertEquals(3, arthmatic.add(10, -7));
    }
    
    @Test
    public void testAdd_WithZero() {
        assertEquals(10, arthmatic.add(10, 0));
    }
    
    @Test
    public void testAdd_LargeNumbers() {
        assertEquals(2147483646, arthmatic.add(Integer.MAX_VALUE, -1));
    }
    
    // Tests for divide() 
    
    @Test
    public void testDivide_PositiveNumbers() {
        assertEquals(5, arthmatic.divide(20, 4));
    }
    
    @Test
    public void testDivide_NegativeNumbers() {
        assertEquals(5, arthmatic.divide(-20, -4));
    }
    
    @Test
    public void testDivide_PositiveAndNegative() {
        assertEquals(-5, arthmatic.divide(20, -4));
    }
    
    @Test
    public void testDivide_ByZero() {
        assertEquals(Integer.MAX_VALUE, arthmatic.divide(10, 0));
    }
    
    @Test
    public void testDivide_ResultWithRemainder() {
        assertEquals(3, arthmatic.divide(10, 3));
    }
    
    // Tests for divideByZero() 
    
    @Test
    public void testDivideByZero_Zero() {
        assertTrue(arthmatic.divideByZero(0));
    }
    
    @Test
    public void testDivideByZero_PositiveNumber() {
        assertFalse(arthmatic.divideByZero(5));
    }
    
    @Test
    public void testDivideByZero_NegativeNumber() {
        assertFalse(arthmatic.divideByZero(-5));
    }
    
    @Test
    public void testDivideByZero_MaxValue() {
        assertFalse(arthmatic.divideByZero(Integer.MAX_VALUE));
    }
    
    @Test
    public void testDivideByZero_MinValue() {
        assertFalse(arthmatic.divideByZero(Integer.MIN_VALUE));
    }
}

