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
                    tablePanel.clearTable();
                    tablePanel.addRows(data);
                    
                    // Update status
                    int rowCount = tablePanel.getRowCount();
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
        Object[] rowData = tablePanel.getSelectedRowData();
        if (rowData != null) {
            inputPanel.populateFields(
                rowData[0].toString(),
                rowData[1].toString(),
                rowData[2].toString(),
                rowData[3].toString()
            );
        }
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
        int selectedRow = tablePanel.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a record to remove", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentId = tablePanel.getSelectedStudentId();
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
        int selectedRow = tablePanel.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(null, "Please select a record to update", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (inputPanel.validateInput()) {
            String oldStudentId = tablePanel.getSelectedStudentId();
            
            SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
                @Override
                protected Boolean doInBackground() throws Exception {
                    return model.updateRecord(
                        oldStudentId,
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

        for (int i = 0; i < tablePanel.getRowCount(); i++) {
            Object[] rowData = new Object[4];
            for (int j = 0; j < 4; j++) {
                rowData[j] = tablePanel.getTableModel().getValueAt(i, j);
            }
            
            tableContent.append(String.format("%-12s %-15s %-15s %-12s\n", 
                rowData[0], rowData[1], rowData[2], rowData[3]));
        }

        tableContent.append("\nTotal Records: ").append(tablePanel.getRowCount());

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
