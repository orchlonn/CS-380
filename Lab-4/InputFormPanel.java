import java.awt.*;
import javax.swing.*;

public class InputFormPanel extends JPanel {
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
