import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradeTableGUI extends JFrame {
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JLabel statusLabel;
    private JScrollPane scrollPane;
    
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/expresso_shop";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "99889699";
    private static final String QUERY = "SELECT * FROM 302_grades";

    public GradeTableGUI() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        loadDataFromDatabase();
    }

    private void initializeComponents() {
        // Initialize the main frame
        setTitle("Grade Table - Database Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
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
        scrollPane.setPreferredSize(new Dimension(750, 400));

        // Initialize buttons and labels
        refreshButton = new JButton("Refresh Data");
        refreshButton.setPreferredSize(new Dimension(120, 30));
        
        statusLabel = new JLabel("Ready to load data...");
        statusLabel.setForeground(Color.BLUE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Create top panel for controls
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(refreshButton);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(statusLabel);

        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Create bottom panel for additional info
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel infoLabel = new JLabel("Database: expresso_shop | Table: 302_grades");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        infoLabel.setForeground(Color.GRAY);
        bottomPanel.add(infoLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadDataFromDatabase();
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
                        GradeTableGUI.this,
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

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GradeTableGUI().setVisible(true);
            }
        });
    }
}
