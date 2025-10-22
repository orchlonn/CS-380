import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GradeTablePanel extends JPanel {
    private JTable gradeTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;

    public GradeTablePanel() {
        initializeComponents();
        setupLayout();
    }

    private void initializeComponents() {
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
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters
    public JTable getGradeTable() {
        return gradeTable;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    // Utility methods
    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void addRows(java.util.List<Object[]> rowsData) {
        for (Object[] row : rowsData) {
            tableModel.addRow(row);
        }
    }

    public int getSelectedRow() {
        return gradeTable.getSelectedRow();
    }

    public Object[] getSelectedRowData() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow >= 0) {
            Object[] rowData = new Object[4];
            for (int i = 0; i < 4; i++) {
                rowData[i] = tableModel.getValueAt(selectedRow, i);
            }
            return rowData;
        }
        return null;
    }

    public String getSelectedStudentId() {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow >= 0) {
            return tableModel.getValueAt(selectedRow, 0).toString();
        }
        return null;
    }

    public int getRowCount() {
        return tableModel.getRowCount();
    }

    public void setSelectionListener(javax.swing.event.ListSelectionListener listener) {
        gradeTable.getSelectionModel().addListSelectionListener(listener);
    }
}
