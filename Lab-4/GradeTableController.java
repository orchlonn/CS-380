import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GradeTableController {
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
