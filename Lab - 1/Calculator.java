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
    private JButton equalsButton, clearButton;
    
    // Calculator logic variables
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;
    
    public Calculator() {
        // Initialize the frame
        setTitle("Integer Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
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
        mulButton = new JButton("*");
        divButton = new JButton("/");
        equalsButton = new JButton("=");
        clearButton = new JButton("Clear");
        
        operationButtons = new JButton[]{addButton, subButton, mulButton, divButton, 
                                       equalsButton, clearButton};
        
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
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top panel for display
        JPanel displayPanel = new JPanel();
        displayPanel.add(display);
        add(displayPanel, BorderLayout.NORTH);
        
        // Main panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add buttons in calculator layout
        // Row 1: Clear, empty, /
        buttonPanel.add(clearButton);
        buttonPanel.add(new JLabel()); // Empty space
        buttonPanel.add(divButton);
        
        // Row 2: 7, 8, 9, *
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(mulButton);
        
        // Row 3: 4, 5, 6, -
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subButton);
        
        // Row 4: 1, 2, 3, +
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);
        
        // Row 5: empty, 0, empty, =
        buttonPanel.add(new JLabel()); // Empty space
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(new JLabel()); // Empty space
        buttonPanel.add(equalsButton);
        
        add(buttonPanel, BorderLayout.CENTER);
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
            num1 = Double.parseDouble(display.getText());
            operator = '+';
            display.setText("0");
        }
        
        if (e.getSource() == subButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '-';
            display.setText("0");
        }
        
        if (e.getSource() == mulButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '*';
            display.setText("0");
        }
        
        if (e.getSource() == divButton) {
            num1 = Double.parseDouble(display.getText());
            operator = '/';
            display.setText("0");
        }
        
        if (e.getSource() == equalsButton) {
            num2 = Double.parseDouble(display.getText());
            
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }
            
            // Display result as integer if it's a whole number
            if (result == (int) result) {
                display.setText(String.valueOf((int) result));
            } else {
                display.setText(String.valueOf(result));
            }
            
            num1 = result;
        }
        
        if (e.getSource() == clearButton) {
            display.setText("0");
            num1 = 0;
            num2 = 0;
            result = 0;
        }
    }
    
    public static void main(String[] args) {
        // Create and display the calculator
        SwingUtilities.invokeLater(() -> new Calculator());
    }
}
