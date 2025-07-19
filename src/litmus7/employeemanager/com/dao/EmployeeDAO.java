package litmus7.employeemanager.com.dao;

import litmus7.employeemanager.com.model.Employee;
import litmus7.employeemanager.com.util.DBConnectionUtil;
import litmus7.employeemanager.com.response.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDAO implements IEmployeeDAO {

    @Override
    public Response insertEmployee(Employee employee) {
        String query = "INSERT INTO employees (name, email, department, salary) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getEmail());
            stmt.setString(3, employee.getDepartment());
            stmt.setDouble(4, employee.getSalary());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                return new Response(200, "Employee inserted successfully");
            } else {
                return new Response(500, "Failed to insert employee");
            }

        } catch (SQLException e) {
            return new Response(500, "SQL Exception: " + e.getMessage());
        }
    }
}
