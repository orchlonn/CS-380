
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
        setTitle("Grade Table - Database Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Initialize components
        model = new GradeTableModel();
        tablePanel = new GradeTablePanel();
        inputPanel = new InputFormPanel();
        buttonPanel = new ButtonPanel();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Create top panel for refresh and status
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(buttonPanel.getRefreshButton());
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(buttonPanel.getStatusLabel());

        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(tablePanel, BorderLayout.CENTER);
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