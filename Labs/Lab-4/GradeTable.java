import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradeTable extends JFrame {
    private GradeTableModel model;
    private GradeTablePanel tablePanel;
    private InputFormPanel inputPanel;
    private ButtonPanel buttonPanel;
    private GradeTableController controller;

    public GradeTable() {
        initializeComponents();
        setupLayout();
        initializeController();
    }

    private void initializeComponents() {
        // Initialize the main frame
        setTitle("myCWU Official Grades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setBackground(new Color(240, 240, 240));

        // Initialize components
        model = new GradeTableModel();
        tablePanel = new GradeTablePanel();
        inputPanel = new InputFormPanel();
        buttonPanel = new ButtonPanel();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        // Create main content panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add components to main panel
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        
        // Add main panel and button panel to frame
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeController() {
        controller = new GradeTableController(model, tablePanel, inputPanel, buttonPanel);
    }

    // Keep the original console method for backward compatibility
    public static void connectToDatabase() throws SQLException {
        GradeTableModel.connectToDatabase();
    }

    public static void main(String[] args) {
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

    // Inner class: GradeTableModel
    static class GradeTableModel {
        // Database connection details
        private static final String DB_URL = "jdbc:mysql://localhost:3306/expresso_shop";
        private static final String DB_USER = "root";
        private static final String DB_PASSWORD = "99889699";
        private static final String QUERY = "SELECT * FROM 302_grades";

        public List<Object[]> getAllRecords() throws SQLException {
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
            }
            
            return data;
        }

        public boolean addRecord(String studentId, String firstName, String lastName, String finalGrade) throws SQLException {
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = con.createStatement()) {
                
                String insertQuery = String.format(
                    "INSERT INTO 302_grades (studentID, firstName, lastName, finalGrade) VALUES ('%s', '%s', '%s', '%s')",
                    studentId, firstName, lastName, finalGrade
                );
                
                int rowsAffected = statement.executeUpdate(insertQuery);
                return rowsAffected > 0;
            }
        }

        public boolean removeRecord(String studentId) throws SQLException {
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = con.createStatement()) {
                
                String deleteQuery = "DELETE FROM 302_grades WHERE studentID = '" + studentId + "'";
                int rowsAffected = statement.executeUpdate(deleteQuery);
                return rowsAffected > 0;
            }
        }

        public boolean updateRecord(String oldStudentId, String studentId, String firstName, String lastName, String finalGrade) throws SQLException {
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = con.createStatement()) {
                
                String updateQuery = String.format(
                    "UPDATE 302_grades SET studentID='%s', firstName='%s', lastName='%s', finalGrade='%s' WHERE studentID='%s'",
                    studentId, firstName, lastName, finalGrade, oldStudentId
                );
                
                int rowsAffected = statement.executeUpdate(updateQuery);
                return rowsAffected > 0;
            }
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
    }

    // Inner class: GradeTablePanel
    static class GradeTablePanel extends JPanel {
        private JTextArea gradeDisplayArea;
        private JScrollPane scrollPane;
        private List<Object[]> gradeData;

        public GradeTablePanel() {
            initializeComponents();
            setupLayout();
        }

        private void initializeComponents() {
            // Initialize text area for displaying grades
            gradeDisplayArea = new JTextArea();
            gradeDisplayArea.setEditable(false);
            gradeDisplayArea.setFont(new Font("Arial", Font.PLAIN, 12));
            gradeDisplayArea.setBackground(Color.WHITE);
            gradeDisplayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Initialize scroll pane
            scrollPane = new JScrollPane(gradeDisplayArea);
            scrollPane.setPreferredSize(new Dimension(600, 200));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            
            gradeData = new ArrayList<>();
        }

        private void setupLayout() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder("Student Grades"));
            add(scrollPane, BorderLayout.CENTER);
        }

        // Utility methods
        public void clearGrades() {
            gradeDisplayArea.setText("");
            gradeData.clear();
        }

        public void addGrade(String firstName, String lastName, String grade) {
            Object[] gradeInfo = {firstName, lastName, grade};
            gradeData.add(gradeInfo);
            updateDisplay();
        }

        public void addGrades(List<Object[]> grades) {
            gradeData.clear();
            gradeData.addAll(grades);
            updateDisplay();
        }

        private void updateDisplay() {
            StringBuilder displayText = new StringBuilder();
            for (Object[] grade : gradeData) {
                String firstName = grade[1].toString();
                String lastName = grade[2].toString();
                String finalGrade = grade[3].toString();
                
                // Format: "FirstName LastName Grade" with right-aligned grades
                String name = firstName + " " + lastName;
                displayText.append(String.format("%-30s %s%n", name, finalGrade));
            }
            gradeDisplayArea.setText(displayText.toString());
        }

        public int getGradeCount() {
            return gradeData.size();
        }

        public List<Object[]> getGradeData() {
            return new ArrayList<>(gradeData);
        }

        // Method to get selected grade info (for compatibility with existing code)
        public Object[] getSelectedGradeData() {
            // Since this is now a text display, we'll return null for selection
            return null;
        }

        public String getSelectedStudentId() {
            // Since this is now a text display, we'll return null for selection
            return null;
        }

        public int getSelectedRow() {
            // Since this is now a text display, we'll return -1 for no selection
            return -1;
        }

        // Compatibility method for existing controller
        public void setSelectionListener(ListSelectionListener listener) {
            // No selection listener needed for text display
        }
    }

    // Inner class: InputFormPanel
    static class InputFormPanel extends JPanel {
        private final JTextField studentIdField;
        private final JTextField firstNameField;
        private final JTextField lastNameField;
        private final JTextField finalGradeField;

        public InputFormPanel() {
            // create fields with column width (keeps a reasonable minimum width)
            studentIdField = new JTextField(20);
            firstNameField = new JTextField(20);
            lastNameField = new JTextField(20);
            finalGradeField = new JTextField(20);

            initializeStyling();
            setupLayout();
        }

        private void initializeStyling() {
            // Optional: explicit preferred height while allowing horizontal expansion
            Dimension minSize = new Dimension(200, 26);
            studentIdField.setMinimumSize(minSize);
            firstNameField.setMinimumSize(minSize);
            lastNameField.setMinimumSize(minSize);
            finalGradeField.setMinimumSize(minSize);

            // consistent font
            Font fieldFont = new Font("Arial", Font.PLAIN, 12);
            studentIdField.setFont(fieldFont);
            firstNameField.setFont(fieldFont);
            lastNameField.setFont(fieldFont);
            finalGradeField.setFont(fieldFont);
        }

        private void setupLayout() {
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            // the panel preferred size is optional; fields will expand with container
            setPreferredSize(new Dimension(560, 180));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.NONE; // default; we'll change for input column
            gbc.weightx = 0;

            // Add rows using helper method
            addLabeledField(gbc, 0, "Student ID", studentIdField);
            addLabeledField(gbc, 1, "First Name", firstNameField);
            addLabeledField(gbc, 2, "Last Name", lastNameField);
            addLabeledField(gbc, 3, "Final Grade", finalGradeField);
        }

        /**
         * Helper to add a label (column 0) and a field (column 1) for the given row.
         * The field is configured to expand horizontally (weightx = 1.0, fill = HORIZONTAL).
         */
        private void addLabeledField(GridBagConstraints gbc, int row, String labelText, JTextField field) {
            // Label (col 0)
            gbc.gridx = 0;
            gbc.gridy = row;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            JLabel label = new JLabel(labelText);
            label.setLabelFor(field);
            add(label, gbc);

            // Field (col 1) - make it expand horizontally
            gbc.gridx = 1;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(field, gbc);

            // reset weightx/fill to safe defaults for next call (not strictly necessary here)
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
        }

        // Getters for the input fields
        public JTextField getStudentIdField() { return studentIdField; }
        public JTextField getFirstNameField() { return firstNameField; }
        public JTextField getLastNameField() { return lastNameField; }
        public JTextField getFinalGradeField() { return finalGradeField; }

        // Utility methods
        public void clearFields() {
            studentIdField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            finalGradeField.setText("");
        }

        public void populateFields(String studentId, String firstName, String lastName, String finalGrade) {
            studentIdField.setText(studentId);
            firstNameField.setText(firstName);
            lastNameField.setText(lastName);
            finalGradeField.setText(finalGrade);
        }

        public boolean validateInput() {
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

        public String getStudentId() { return studentIdField.getText().trim(); }
        public String getFirstName() { return firstNameField.getText().trim(); }
        public String getLastName() { return lastNameField.getText().trim(); }
        public String getFinalGrade() { return finalGradeField.getText().trim(); }
    }

    // Inner class: ButtonPanel
    static class ButtonPanel extends JPanel {
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

    // Inner class: GradeTableController
    static class GradeTableController {
        private GradeTableModel model;
        private GradeTablePanel tablePanel;
        private InputFormPanel inputPanel;
        private ButtonPanel buttonPanel;

        public GradeTableController(GradeTableModel model, GradeTablePanel tablePanel, 
                                   InputFormPanel inputPanel, ButtonPanel buttonPanel) {
            this.model = model;
            this.tablePanel = tablePanel;
            this.inputPanel = inputPanel;
            this.buttonPanel = buttonPanel;
            
            setupEventHandlers();
            loadDataFromDatabase();
        }

        private void setupEventHandlers() {
            // Button event handlers
            buttonPanel.getRefreshButton().addActionListener(e -> loadDataFromDatabase());
            buttonPanel.getAddButton().addActionListener(e -> addRecord());
            buttonPanel.getRemoveButton().addActionListener(e -> removeRecord());
            buttonPanel.getUpdateButton().addActionListener(e -> updateRecord());
            buttonPanel.getPrintTableButton().addActionListener(e -> printTable());
            
            // Table selection listener to populate fields
            tablePanel.setSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    populateFieldsFromSelectedRow();
                }
            });
        }

        private void loadDataFromDatabase() {
            buttonPanel.setLoadingStatus();

            SwingWorker<List<Object[]>, Void> worker = new SwingWorker<List<Object[]>, Void>() {
                @Override
                protected List<Object[]> doInBackground() throws Exception {
                    return model.getAllRecords();
                }

                @Override
                protected void done() {
                    try {
                        List<Object[]> data = get();
                        
                        // Clear and populate table
                        tablePanel.clearGrades();
                        tablePanel.addGrades(data);
                        
                        // Update status
                        int rowCount = tablePanel.getGradeCount();
                        buttonPanel.setStatus("Data loaded successfully - " + rowCount + " records found", Color.GREEN);
                        
                    } catch (Exception e) {
                        buttonPanel.setStatus("Error: " + e.getMessage(), Color.RED);
                        
                        // Show error dialog
                        JOptionPane.showMessageDialog(
                            null,
                            "Failed to load data from database:\n" + e.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    } finally {
                        buttonPanel.setReadyStatus();
                    }
                }
            };

            worker.execute();
        }

        private void populateFieldsFromSelectedRow() {
            // Since we're using text display now, we'll disable auto-population
            // Users will need to manually enter data for updates
        }

        private void addRecord() {
            if (inputPanel.validateInput()) {
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        return model.addRecord(
                            inputPanel.getStudentId(),
                            inputPanel.getFirstName(),
                            inputPanel.getLastName(),
                            inputPanel.getFinalGrade()
                        );
                    }

                    @Override
                    protected void done() {
                        try {
                            Boolean success = get();
                            if (success) {
                                buttonPanel.setStatus("Record added successfully", Color.GREEN);
                                inputPanel.clearFields();
                                loadDataFromDatabase(); // Refresh the table
                            }
                        } catch (Exception e) {
                            buttonPanel.setStatus("Error: " + e.getMessage(), Color.RED);
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Add Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
                worker.execute();
            }
        }

        private void removeRecord() {
            String studentId = inputPanel.getStudentId();
            if (studentId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a Student ID to remove", "No Student ID", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to remove student " + studentId + "?", 
                "Confirm Removal", 
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        return model.removeRecord(studentId);
                    }

                    @Override
                    protected void done() {
                        try {
                            Boolean success = get();
                            if (success) {
                                buttonPanel.setStatus("Record removed successfully", Color.GREEN);
                                inputPanel.clearFields();
                                loadDataFromDatabase(); // Refresh the table
                            }
                        } catch (Exception e) {
                            buttonPanel.setStatus("Error: " + e.getMessage(), Color.RED);
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Remove Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
                worker.execute();
            }
        }

        private void updateRecord() {
            if (inputPanel.validateInput()) {
                String studentId = inputPanel.getStudentId();
                String firstName = inputPanel.getFirstName();
                String lastName = inputPanel.getLastName();
                String finalGrade = inputPanel.getFinalGrade();
                
                // For update, we'll use the student ID as both old and new ID
                // In a real application, you might want to add a separate field for the old ID
                SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        return model.updateRecord(studentId, studentId, firstName, lastName, finalGrade);
                    }

                    @Override
                    protected void done() {
                        try {
                            Boolean success = get();
                            if (success) {
                                buttonPanel.setStatus("Record updated successfully", Color.GREEN);
                                inputPanel.clearFields();
                                loadDataFromDatabase(); // Refresh the table
                            }
                        } catch (Exception e) {
                            buttonPanel.setStatus("Error: " + e.getMessage(), Color.RED);
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
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

            List<Object[]> gradeData = tablePanel.getGradeData();
            for (Object[] grade : gradeData) {
                tableContent.append(String.format("%-12s %-15s %-15s %-12s\n", 
                    grade[0], grade[1], grade[2], grade[3]));
            }

            tableContent.append("\nTotal Records: ").append(tablePanel.getGradeCount());

            // Display in a scrollable text area
            JTextArea textArea = new JTextArea(tableContent.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            textArea.setEditable(false);
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(null, scrollPane, "Grade Table Report", JOptionPane.INFORMATION_MESSAGE);
            
            buttonPanel.setStatus("Table printed successfully", Color.BLUE);
        }
    }
}
