import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:employee.db";

    public static Connection connect() {

        try {
            Class.forName("org.sqlite.JDBC");

            Connection connection = DriverManager.getConnection(URL);

            System.out.println("Database connected successfully!");

            return connection;

        } catch (ClassNotFoundException e) {

            System.out.println("SQLite JDBC Driver not found!");

        } catch (SQLException e) {

            System.out.println("Connection failed: " + e.getMessage());
        }

        return null;
    }
}