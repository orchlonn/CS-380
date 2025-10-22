
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GradeTable extends JFrame {
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JLabel statusLabel;
    private JScrollPane scrollPane;
    
    // Input form fields
    private JTextField studentIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField finalGradeField;
    
    // CRUD operation buttons
    private JButton addButton;
    private JButton removeButton;
    private JButton updateButton;
    private JButton printTableButton;
    
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/expresso_shop";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "99889699";
    private static final String QUERY = "SELECT * FROM 302_grades";

    public GradeTable() {
        initializeGUI();
        loadDataFromDatabase();
    }

    private void initializeGUI() {
        // Initialize the main frame
        setTitle("Grade Table - Database Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Initialize table model with column names
        String[] columnNames = {"Student ID", "First Name", "Last Name", "Final Grade"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        // Initialize table
        gradeTable = new JTable(tableModel);
        gradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gradeTable.setRowHeight(25);
        gradeTable.getTableHeader().setReorderingAllowed(false);

        // Initialize scroll pane
        scrollPane = new JScrollPane(gradeTable);
        scrollPane.setPreferredSize(new Dimension(850, 300));

        // Initialize input fields
        studentIdField = new JTextField(15);
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        finalGradeField = new JTextField(15);

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

        // Setup layout
        setLayout(new BorderLayout());

        // Create top panel for refresh and status
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(refreshButton);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(statusLabel);

        // Create input form panel
        JPanel inputPanel = createInputPanel();

        // Create button panel
        JPanel buttonPanel = createButtonPanel();

        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Setup event handlers
        setupEventHandlers();
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        inputPanel.setPreferredSize(new Dimension(200, 300));

        // Add input fields with labels
        inputPanel.add(createInputRow("Student ID:", studentIdField));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(createInputRow("First Name:", firstNameField));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(createInputRow("Last Name:", lastNameField));
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(createInputRow("Final Grade:", finalGradeField));
        inputPanel.add(Box.createVerticalGlue());

        return inputPanel;
    }

    private JPanel createInputRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, 25));
        row.add(label);
        row.add(textField);
        return row;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(printTableButton);
        
        // Add database info
        JLabel infoLabel = new JLabel("Database: expresso_shop | Table: 302_grades");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.GRAY);
        buttonPanel.add(Box.createHorizontalStrut(30));
        buttonPanel.add(infoLabel);

        return buttonPanel;
    }

    private void setupEventHandlers() {
        refreshButton.addActionListener(e -> loadDataFromDatabase());
        addButton.addActionListener(e -> addRecord());
        removeButton.addActionListener(e -> removeRecord());
        updateButton.addActionListener(e -> updateRecord());
        printTableButton.addActionListener(e -> printTable());
        
        // Add table selection listener to populate fields
        gradeTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                populateFieldsFromSelectedRow();
            }
        });
    }

    private void loadDataFromDatabase() {
        statusLabel.setText("Loading data...");
        statusLabel.setForeground(Color.ORANGE);
        refreshButton.setEnabled(false);

        // Clear existing data
        tableModel.setRowCount(0);

        // Execute database query in a separate thread to prevent GUI freezing
        SwingWorker<List<Object[]>, Void> worker = new SwingWorker<List<Object[]>, Void>() {
            @Override
            protected List<Object[]> doInBackground() throws Exception {
                List<Object[]> data = new ArrayList<>();
                
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                     Statement statement = con.createStatement();
                     ResultSet resultSet = statement.executeQuery(QUERY)) {
                    
                    while (resultSet.next()) {
                        String studentID = resultSet.getString("studentID");
                        String firstName = resultSet.getString("firstName");
                        String lastName = resultSet.getString("lastName");
                        String finalGrade = resultSet.getString("finalGrade");
                        
                        Object[] row = {studentID, firstName, lastName, finalGrade};
                        data.add(row);
                    }
                    
                } catch (SQLException e) {
                    throw new Exception("Database connection failed: " + e.getMessage());
                }
                
                return data;
            }

            @Override
            protected void done() {
                try {
                    List<Object[]> data = get();
                    
                    // Add data to table model
                    for (Object[] row : data) {
                        tableModel.addRow(row);
                    }
                    
                    // Update status
                    int rowCount = tableModel.getRowCount();
                    statusLabel.setText("Data loaded successfully - " + rowCount + " records found");
                    statusLabel.setForeground(Color.GREEN);
                    
                } catch (Exception e) {
                    statusLabel.setText("Error: " + e.getMessage());
                    statusLabel.setForeground(Color.RED);
                    
                    // Show error dialog
                    JOptionPane.showMessageDialog(
                        GradeTable.this,
                        "Failed to load data from database:\n" + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } finally {
                    refreshButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private void populateFieldsFromSelectedRow() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow >= 0) {
            studentIdField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            firstNameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            lastNameField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            finalGradeField.setText(tableModel.getValueAt(selectedRow, 3).toString());
        }
    }

    private void clearFields() {
        studentIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        finalGradeField.setText("");
    }

    private void addRecord() {
        if (validateInput()) {
            String studentId = studentIdField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String finalGrade = finalGradeField.getText().trim();

            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         Statement statement = con.createStatement()) {
                        
                        String insertQuery = String.format(
                            "INSERT INTO 302_grades (studentID, firstName, lastName, finalGrade) VALUES ('%s', '%s', '%s', '%s')",
                            studentId, firstName, lastName, finalGrade
                        );
                        
                        int rowsAffected = statement.executeUpdate(insertQuery);
                        return rowsAffected > 0;
                        
                    } catch (SQLException e) {
                        throw new Exception("Failed to add record: " + e.getMessage());
                    }
                }

                @Override
                protected void done() {
                    try {
                        Boolean success = get();
                        if (success) {
                            statusLabel.setText("Record added successfully");
                            statusLabel.setForeground(Color.GREEN);
                            clearFields();
                            loadDataFromDatabase(); // Refresh the table
                        }
                    } catch (Exception e) {
                        statusLabel.setText("Error: " + e.getMessage());
                        statusLabel.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(GradeTable.this, e.getMessage(), "Add Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }

    private void removeRecord() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentId = tableModel.getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to remove student " + studentId + "?", 
            "Confirm Removal", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         Statement statement = con.createStatement()) {
                        
                        String deleteQuery = "DELETE FROM 302_grades WHERE studentID = '" + studentId + "'";
                        int rowsAffected = statement.executeUpdate(deleteQuery);
                        return rowsAffected > 0;
                        
                    } catch (SQLException e) {
                        throw new Exception("Failed to remove record: " + e.getMessage());
                    }
                }

                @Override
                protected void done() {
                    try {
                        Boolean success = get();
                        if (success) {
                            statusLabel.setText("Record removed successfully");
                            statusLabel.setForeground(Color.GREEN);
                            clearFields();
                            loadDataFromDatabase(); // Refresh the table
                        }
                    } catch (Exception e) {
                        statusLabel.setText("Error: " + e.getMessage());
                        statusLabel.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(GradeTable.this, e.getMessage(), "Remove Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }

    private void updateRecord() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record to update", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validateInput()) {
            String oldStudentId = tableModel.getValueAt(selectedRow, 0).toString();
            String studentId = studentIdField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String finalGrade = finalGradeField.getText().trim();

            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                         Statement statement = con.createStatement()) {
                        
                        String updateQuery = String.format(
                            "UPDATE 302_grades SET studentID='%s', firstName='%s', lastName='%s', finalGrade='%s' WHERE studentID='%s'",
                            studentId, firstName, lastName, finalGrade, oldStudentId
                        );
                        
                        int rowsAffected = statement.executeUpdate(updateQuery);
                        return rowsAffected > 0;
                        
                    } catch (SQLException e) {
                        throw new Exception("Failed to update record: " + e.getMessage());
                    }
                }

                @Override
                protected void done() {
                    try {
                        Boolean success = get();
                        if (success) {
                            statusLabel.setText("Record updated successfully");
                            statusLabel.setForeground(Color.GREEN);
                            clearFields();
                            loadDataFromDatabase(); // Refresh the table
                        }
                    } catch (Exception e) {
                        statusLabel.setText("Error: " + e.getMessage());
                        statusLabel.setForeground(Color.RED);
                        JOptionPane.showMessageDialog(GradeTable.this, e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            };
            worker.execute();
        }
    }

    private void printTable() {
        StringBuilder tableContent = new StringBuilder();
        tableContent.append("Grade Table Report\n");
        tableContent.append("==================\n\n");
        tableContent.append(String.format("%-12s %-15s %-15s %-12s\n", "Student ID", "First Name", "Last Name", "Final Grade"));
        tableContent.append("--------------------------------------------------------\n");

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String studentId = tableModel.getValueAt(i, 0).toString();
            String firstName = tableModel.getValueAt(i, 1).toString();
            String lastName = tableModel.getValueAt(i, 2).toString();
            String finalGrade = tableModel.getValueAt(i, 3).toString();
            
            tableContent.append(String.format("%-12s %-15s %-15s %-12s\n", studentId, firstName, lastName, finalGrade));
        }

        tableContent.append("\nTotal Records: ").append(tableModel.getRowCount());

        // Display in a scrollable text area
        JTextArea textArea = new JTextArea(tableContent.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(this, scrollPane, "Grade Table Report", JOptionPane.INFORMATION_MESSAGE);
        
        statusLabel.setText("Table printed successfully");
        statusLabel.setForeground(Color.BLUE);
    }

    private boolean validateInput() {
        if (studentIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Student ID is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            studentIdField.requestFocus();
            return false;
        }
        if (firstNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "First Name is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            firstNameField.requestFocus();
            return false;
        }
        if (lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Last Name is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            lastNameField.requestFocus();
            return false;
        }
        if (finalGradeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Final Grade is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            finalGradeField.requestFocus();
            return false;
        }
        return true;
    }

    // Keep the original console method for backward compatibility
    public static void connectToDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/expresso_shop";
        String query = "SELECT * FROM 302_grades";

        try (Connection con = DriverManager.getConnection(url, "root", "99889699");
             Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            System.out.println("Connected to database");

            while (resultSet.next()) {
                String studentID = resultSet.getString("studentID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String finalGrade = resultSet.getString("finalGrade");
                System.out.println(studentID + " " + firstName + " " + lastName + " " + finalGrade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error connecting to database");
            throw e;
        }
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeTable().setVisible(true);
            }
        });
    }
}