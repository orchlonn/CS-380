import java.awt.*;
import javax.swing.*;

public class InputFormPanel extends JPanel {
    private JTextField studentIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField finalGradeField;

    public InputFormPanel() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
        // Initialize input fields
        studentIdField = new JTextField(15);
        firstNameField = new JTextField(15);
        lastNameField = new JTextField(15);
        finalGradeField = new JTextField(15);
    }

    private void setupLayout() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Student Information"));
        setPreferredSize(new Dimension(200, 300));

        // Add input fields with labels
        add(createInputRow("Student ID:", studentIdField));
        add(Box.createVerticalStrut(10));
        add(createInputRow("First Name:", firstNameField));
        add(Box.createVerticalStrut(10));
        add(createInputRow("Last Name:", lastNameField));
        add(Box.createVerticalStrut(10));
        add(createInputRow("Final Grade:", finalGradeField));
        add(Box.createVerticalGlue());
    }

    private JPanel createInputRow(String labelText, JTextField textField) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(80, 25));
        row.add(label);
        row.add(textField);
        return row;
    }

    // Getters for the input fields
    public JTextField getStudentIdField() {
        return studentIdField;
    }

    public JTextField getFirstNameField() {
        return firstNameField;
    }

    public JTextField getLastNameField() {
        return lastNameField;
    }

    public JTextField getFinalGradeField() {
        return finalGradeField;
    }

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

    public String getStudentId() {
        return studentIdField.getText().trim();
    }

    public String getFirstName() {
        return firstNameField.getText().trim();
    }

    public String getLastName() {
        return lastNameField.getText().trim();
    }

    public String getFinalGrade() {
        return finalGradeField.getText().trim();
    }
}
