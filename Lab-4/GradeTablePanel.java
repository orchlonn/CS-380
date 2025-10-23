import java.awt.*;
import javax.swing.*;

public class GradeTablePanel extends JPanel {
    private JTextArea gradeDisplayArea;
    private JScrollPane scrollPane;
    private java.util.List<Object[]> gradeData;

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
        
        gradeData = new java.util.ArrayList<>();
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

    public void addGrades(java.util.List<Object[]> grades) {
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

    public java.util.List<Object[]> getGradeData() {
        return new java.util.ArrayList<>(gradeData);
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
    public void setSelectionListener(javax.swing.event.ListSelectionListener listener) {
        // No selection listener needed for text display
    }
}