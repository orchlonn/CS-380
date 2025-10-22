import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GradeTableModel {
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
