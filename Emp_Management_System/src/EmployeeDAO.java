import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ADD EMPLOYEE
    public void addEmployee(Employee employee) {

        String sql = "INSERT INTO employees " +
                "(name, email, phone, department, salary) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getEmail());
            pstmt.setString(3, employee.getPhone());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getSalary());

            pstmt.executeUpdate();

            System.out.println("Employee added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }


    // GET ALL EMPLOYEES
    public List<Employee> getAllEmployees() {

        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employees";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );

                employees.add(employee);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching employees: " + e.getMessage());
        }

        return employees;
    }


    // UPDATE EMPLOYEE
    public void updateEmployee(Employee employee) {

        String sql = "UPDATE employees SET " +
                "name=?, email=?, phone=?, department=?, salary=? " +
                "WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getEmail());
            pstmt.setString(3, employee.getPhone());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getSalary());
            pstmt.setInt(6, employee.getId());

            pstmt.executeUpdate();

            System.out.println("Employee updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }


    // DELETE EMPLOYEE
    public void deleteEmployee(int id) {

        String sql = "DELETE FROM employees WHERE id=?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            System.out.println("Employee deleted successfully!");

        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }
    // SEARCH EMPLOYEE
    public List<Employee> searchEmployees(String keyword) {

        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employees " +
                    "WHERE name LIKE ? OR email LIKE ? OR department LIKE ?";

        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String search = "%" + keyword + "%";

            pstmt.setString(1, search);
            pstmt.setString(2, search);
            pstmt.setString(3, search);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("department"),
                        rs.getDouble("salary")
                );

                employees.add(employee);
            }

        } catch (SQLException e) {
            System.out.println("Error searching employees: " + e.getMessage());
        }

        return employees;
    }
}