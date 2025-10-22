
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradeTable {

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

    public static void main(String[] args) {
        try {
            connectToDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}