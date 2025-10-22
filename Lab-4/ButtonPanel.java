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
        // Initialize buttons
        refreshButton = new JButton("Refresh Data");
        addButton = new JButton("Add");
        removeButton = new JButton("Remove");
        updateButton = new JButton("Update");
        printTableButton = new JButton("Print Table");
        
        // Set button sizes
        Dimension buttonSize = new Dimension(100, 30);
        refreshButton.setPreferredSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        removeButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        printTableButton.setPreferredSize(buttonSize);
        
        statusLabel = new JLabel("Ready to load data...");
        statusLabel.setForeground(Color.BLUE);
    }

    private void setupLayout() {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBorder(BorderFactory.createTitledBorder("Operations"));
        
        add(addButton);
        add(removeButton);
        add(updateButton);
        add(printTableButton);
        
        // Add database info
        JLabel infoLabel = new JLabel("Database: expresso_shop | Table: 302_grades");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.GRAY);
        add(Box.createHorizontalStrut(30));
        add(infoLabel);
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
