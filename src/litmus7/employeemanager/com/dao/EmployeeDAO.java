package litmus7.employeemanager.com.dao;

import litmus7.employeemanager.com.model.Employee;
import litmus7.employeemanager.com.response.Response;
import litmus7.employeemanager.com.constant.Messages;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

public class EmployeeDAO {

    private Connection getConnection() throws Exception {
        Properties props = new Properties();

        // Load from resources folder
        InputStream in = getClass().getClassLoader().getResourceAsStream("litmus7/employeemanager/com/resource/db.properties");

        if (in == null) {
            throw new Exception("db.properties not found in classpath (resources folder).");
        }

        props.load(in);

        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        String driver = props.getProperty("db.driver");

        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    public Response insertEmployees(List<Employee> employees) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO employees (id, first_name, last_name, email, phone, department, salary, join_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            for (Employee emp : employees) {
                ps.setInt(1, emp.getEmpId());
                ps.setString(2, emp.getFirstName());
                ps.setString(3, emp.getLastName());
                ps.setString(4, emp.getEmail());
                ps.setString(5, emp.getPhone());
                ps.setString(6, emp.getDepartment());
                ps.setDouble(7, emp.getSalary());
                ps.setString(8, emp.getJoinDate());
                ps.addBatch();
            }

            ps.executeBatch();
            return new Response("Success", Messages.UPLOAD_SUCCESS);

        } catch (Exception e) {
            return new Response("Failed", Messages.UPLOAD_FAILED + " Error: " + e.getMessage());
        }
    }
}
