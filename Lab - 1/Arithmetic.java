/**
 * Arithmetic class to handle basic mathematical operations for integers
 * CS-380 Lab Assignment #1 - Integer Calculator
 */
public class Arithmetic {
    
    /**
     * Performs integer addition
     * @param a first integer operand
     * @param b second integer operand
     * @return sum of a and b
     */
    public static int addition(int a, int b) {
        return a + b;
    }
    
    /**
     * Performs integer subtraction
     * @param a first integer operand (minuend)
     * @param b second integer operand (subtrahend)
     * @return difference of a and b
     */
    public static int subtraction(int a, int b) {
        return a - b;
    }
    
    /**
     * Performs integer multiplication
     * @param a first integer operand
     * @param b second integer operand
     * @return product of a and b
     */
    public static int multiplication(int a, int b) {
        return a * b;
    }
    
    /**
     * Performs integer division with truncation
     * Decimal results are truncated (not rounded)
     * For example: 5/2 = 2, -7/3 = -2
     * @param a dividend (integer to be divided)
     * @param b divisor (integer to divide by)
     * @return quotient of a divided by b (truncated to integer)
     * @throws ArithmeticException if divisor is zero
     */
    public static int division(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b; // Java integer division automatically truncates
    }
    
    /**
     * Helper method to negate a number
     * @param number the integer to negate
     * @return the negated value of the input number
     */
    public static int negate(int number) {
        return -number;
    }
    
    /**
     * Helper method to check if a string represents a valid integer
     * @param str the string to validate
     * @return true if the string is a valid integer, false otherwise
     */
    public static boolean isValidInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Helper method to safely parse a string to integer
     * @param str the string to parse
     * @return the integer value, or 0 if parsing fails
     */
    public static int safeParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
