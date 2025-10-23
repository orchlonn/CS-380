
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

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
}