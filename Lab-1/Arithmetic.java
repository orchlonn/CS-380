import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Arithmetic class to handle basic mathematical operations for integers
 * CS-380 Lab Assignment #1 - Integer Calculator
 * Also serves as the driver class with GUI components
 * @author Orchlon Chinbat (50291063)
 */
public class Arithmetic extends JFrame implements ActionListener {
    
    // GUI Components
    private JTextField display;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton addButton, subButton, mulButton, divButton;
    private JButton equalsButton, clearButton, negateButton;
    
    // Calculator logic variables
    private int num1 = 0, num2 = 0, result = 0;
    private char operator;
    
    /**
     * Constructor for the Arithmetic calculator GUI
     */
    public Arithmetic() {
        // Initialize the frame
        setTitle("Integer Calculator 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize components
        initializeComponents();
        setupLayout();
        
        // Set the frame to visible
        setVisible(true);
    }
    
    /**
     * Initialize all GUI components
     */
    private void initializeComponents() {
        // Display field
        display = new JTextField();
        display.setPreferredSize(new Dimension(350, 50));
        display.setFont(new Font("Arial", Font.BOLD, 20));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setText("0");
        
        // Number buttons (0-9)
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            // Create a new button for each number
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            numberButtons[i].setFocusable(false);
        }
        
        // Create a new button for each operation
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("/");
        equalsButton = new JButton("=");
        clearButton = new JButton("Clear");
        negateButton = new JButton("+/-");
        
        // Create a new button for each operation
        operationButtons = new JButton[]{addButton, subButton, mulButton, divButton, 
                                       equalsButton, clearButton, negateButton};
        
        // Set properties for operation buttons
        for (JButton button : operationButtons) {
            button.addActionListener(this);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setFocusable(false);
        }
        
        // Set the background color for each operation button
        addButton.setBackground(new Color(255, 165, 0));
        subButton.setBackground(new Color(255, 165, 0));
        mulButton.setBackground(new Color(255, 165, 0));
        divButton.setBackground(new Color(255, 165, 0));
        equalsButton.setBackground(new Color(0, 128, 255));
        clearButton.setBackground(new Color(255, 69, 69));
        negateButton.setBackground(new Color(128, 128, 128));
    }
    
    /**
     * Set up the layout of the calculator GUI
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel for display
        JPanel displayPanel = new JPanel();
        displayPanel.add(display);
        add(displayPanel, BorderLayout.NORTH);
        
        // Main panel for buttons using BorderLayout
        JPanel mainButtonPanel = new JPanel(new BorderLayout(5, 5));
        mainButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Left panel for numbers (3x4 grid)
        JPanel numberPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        
        // Add number buttons in calculator layout
        // Row 1: 7, 8, 9
        numberPanel.add(numberButtons[7]);
        numberPanel.add(numberButtons[8]);
        numberPanel.add(numberButtons[9]);
        
        // Row 2: 4, 5, 6
        numberPanel.add(numberButtons[4]);
        numberPanel.add(numberButtons[5]);
        numberPanel.add(numberButtons[6]);
        
        // Row 3: 1, 2, 3
        numberPanel.add(numberButtons[1]);
        numberPanel.add(numberButtons[2]);
        numberPanel.add(numberButtons[3]);
        
        // Row 4: empty, 0, empty
        numberPanel.add(numberButtons[0]);
        numberPanel.add(new JLabel()); // Empty space
        numberPanel.add(new JLabel()); // Empty space
        
        // Right panel for operations (vertical layout)
        JPanel operationPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        
        // Add operation buttons vertically
        operationPanel.add(clearButton);
        operationPanel.add(negateButton);
        operationPanel.add(divButton);
        operationPanel.add(mulButton);
        operationPanel.add(subButton);
        operationPanel.add(addButton);
        operationPanel.add(equalsButton);
        
        // Add panels to main panel
        mainButtonPanel.add(numberPanel, BorderLayout.CENTER);
        mainButtonPanel.add(operationPanel, BorderLayout.EAST);
        
        add(mainButtonPanel, BorderLayout.CENTER);
    }
    
    /**
     * Handle all button click events
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle number button clicks
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (display.getText().equals("0")) {
                    display.setText(String.valueOf(i));
                } else {
                    display.setText(display.getText() + i);
                }
            }
        }
        
        // Handle operation buttons
        if (e.getSource() == addButton) {
            num1 = safeParseInt(display.getText());
            operator = '+';
            display.setText("0");
        }
        
        // Handle subtraction button clicks
        if (e.getSource() == subButton) {
            num1 = safeParseInt(display.getText());
            operator = '-';
            display.setText("0");
        }
        
        // Handle multiplication button clicks
        if (e.getSource() == mulButton) {
            num1 = safeParseInt(display.getText());
            operator = '*';
            display.setText("0");
        }
        
        // Handle division button clicks
        if (e.getSource() == divButton) {
            num1 = safeParseInt(display.getText());
            operator = '/';
            display.setText("0");
        }
        
        // Handle equals button clicks
        if (e.getSource() == equalsButton) {
            num2 = safeParseInt(display.getText());
            
            try {
                switch (operator) {
                    case '+':
                        result = addition(num1, num2);
                        break;
                    case '-':
                        result = subtraction(num1, num2);
                        break;
                    case '*':
                        result = multiplication(num1, num2);
                        break;
                    case '/':
                        result = division(num1, num2);
                        break;
                }
                
                display.setText(String.valueOf(result));
                num1 = result;
                
            } catch (ArithmeticException ex) {
                display.setText("Error");
                num1 = 0;
                num2 = 0;
                result = 0;
            }
        }
        
        // Handle clear button clicks
        if (e.getSource() == clearButton) {
            display.setText("0");
            num1 = 0;
            num2 = 0;
            result = 0;
        }
        
        // Handle negate button clicks
        if (e.getSource() == negateButton) {
            int currentValue = safeParseInt(display.getText());
            int negatedValue = negate(currentValue);
            display.setText(String.valueOf(negatedValue));
        }
    }
    
    /**
     * Performs integer addition
     * @param a first integer operand
     * @param b second integer operand
     * @return sum of a and b
     */
    public int addition(int a, int b) {
        return a + b;
    }
    
    /**
     * Performs integer subtraction
     * @param a first integer operand (minuend)
     * @param b second integer operand (subtrahend)
     * @return difference of a and b
     */
    public int subtraction(int a, int b) {
        return a - b;
    }
    
    /**
     * Performs integer multiplication
     * @param a first integer operand
     * @param b second integer operand
     * @return product of a and b
     */
    public int multiplication(int a, int b) {
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
    public int division(int a, int b) {
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
    public int negate(int number) {
        return -number;
    }
    
    /**
     * Helper method to check if a string represents a valid integer
     * @param str the string to validate
     * @return true if the string is a valid integer, false otherwise
     */
    public boolean isValidInteger(String str) {
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
    public int safeParseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Main method to start the calculator application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create and display the calculator
        SwingUtilities.invokeLater(() -> new Arithmetic());
    }
}
