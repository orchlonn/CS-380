import java.awt.*;
import javax.swing.*;

public class ButtonPanel extends JPanel {
    private JButton refreshButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton updateButton;
    private JButton printTableButton;
    private JLabel statusLabel;

    public ButtonPanel() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Initialize buttons with modern styling
        refreshButton = new JButton("Refresh Data");
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        updateButton = new JButton("Update");
        printTableButton = new JButton("Print Table");
        
        // Set button sizes and styling
        Dimension buttonSize = new Dimension(120, 35);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        
        refreshButton.setPreferredSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        removeButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        printTableButton.setPreferredSize(buttonSize);
        
        // Apply font to all buttons
        refreshButton.setFont(buttonFont);
        addButton.setFont(buttonFont);
        removeButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        printTableButton.setFont(buttonFont);
        
        // Set button colors to match sample
        Color buttonColor = new Color(240, 240, 240);
        addButton.setBackground(buttonColor);
        removeButton.setBackground(buttonColor);
        updateButton.setBackground(buttonColor);
        printTableButton.setBackground(buttonColor);
        
        statusLabel = new JLabel("Ready to load data...");
        statusLabel.setForeground(Color.BLUE);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top panel for refresh button and status
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(refreshButton);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(statusLabel);
        
        // Bottom panel for operation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(printTableButton);
        
        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    // Getters for buttons
    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getPrintTableButton() {
        return printTableButton;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    // Utility methods for status updates
    public void setStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    public void setLoadingStatus() {
        statusLabel.setText("Loading data...");
        statusLabel.setForeground(Color.ORANGE);
        refreshButton.setEnabled(false);
    }

    public void setReadyStatus() {
        refreshButton.setEnabled(true);
    }
}
