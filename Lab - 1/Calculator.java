import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Simple Integer Calculator GUI Application
 * CS-380 Lab Assignment #1
 */
public class Calculator extends JFrame implements ActionListener {
    
    // GUI Components
    private JTextField display;
    private JButton[] numberButtons;
    private JButton[] operationButtons;
    private JButton addButton, subButton, mulButton, divButton;
    private JButton equalsButton, clearButton, negateButton;
    
    // Calculator logic variables
    private int num1 = 0, num2 = 0, result = 0;
    private char operator;
    
    public Calculator() {
        // Initialize the frame
        setTitle("Integer Calculator 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize components
        initializeComponents();
        setupLayout();
        
        setVisible(true);
    }
    
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
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            numberButtons[i].setFocusable(false);
        }
        
        // Operation buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("/");
        equalsButton = new JButton("=");
        clearButton = new JButton("Clear");
        negateButton = new JButton("+/-");
        
        operationButtons = new JButton[]{addButton, subButton, mulButton, divButton, 
                                       equalsButton, clearButton, negateButton};
        
        // Set properties for operation buttons
        for (JButton button : operationButtons) {
            button.addActionListener(this);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setFocusable(false);
        }
        
        // Special styling for operation buttons
        addButton.setBackground(new Color(255, 165, 0));
        subButton.setBackground(new Color(255, 165, 0));
        mulButton.setBackground(new Color(255, 165, 0));
        divButton.setBackground(new Color(255, 165, 0));
        equalsButton.setBackground(new Color(0, 128, 255));
        clearButton.setBackground(new Color(255, 69, 69));
        negateButton.setBackground(new Color(128, 128, 128));
    }
    
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
            num1 = Arithmetic.safeParseInt(display.getText());
            operator = '+';
            display.setText("0");
        }
        
        if (e.getSource() == subButton) {
            num1 = Arithmetic.safeParseInt(display.getText());
            operator = '-';
            display.setText("0");
        }
        
        if (e.getSource() == mulButton) {
            num1 = Arithmetic.safeParseInt(display.getText());
            operator = '*';
            display.setText("0");
        }
        
        if (e.getSource() == divButton) {
            num1 = Arithmetic.safeParseInt(display.getText());
            operator = '/';
            display.setText("0");
        }
        
        if (e.getSource() == equalsButton) {
            num2 = Arithmetic.safeParseInt(display.getText());
            
            try {
                switch (operator) {
                    case '+':
                        result = Arithmetic.addition(num1, num2);
                        break;
                    case '-':
                        result = Arithmetic.subtraction(num1, num2);
                        break;
                    case '*':
                        result = Arithmetic.multiplication(num1, num2);
                        break;
                    case '/':
                        result = Arithmetic.division(num1, num2);
                        break;
                }
                
                // Display result as integer (always integer for this calculator)
                display.setText(String.valueOf(result));
                num1 = result;
                
            } catch (ArithmeticException ex) {
                display.setText("Error");
                num1 = 0;
                num2 = 0;
                result = 0;
            }
        }
        
        if (e.getSource() == clearButton) {
            display.setText("0");
            num1 = 0;
            num2 = 0;
            result = 0;
        }
        
        if (e.getSource() == negateButton) {
            int currentValue = Arithmetic.safeParseInt(display.getText());
            int negatedValue = Arithmetic.negate(currentValue);
            display.setText(String.valueOf(negatedValue));
        }
    }
    
    public static void main(String[] args) {
        // Create and display the calculator
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
