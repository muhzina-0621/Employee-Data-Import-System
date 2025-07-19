package com.employee;
import java.io.*;
import java.sql.*;
import java.util.*;

public class EmployeeManagerController {
    private static final String CSV_FILE = "employees.csv";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeedb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "425402Th#";

    public List<Employee> readCSV() {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length < 8) {
                    System.out.println("⚠️ Skipped invalid row: " + line);
                    continue;
                }

                try {
                    int empId = Integer.parseInt(tokens[0].trim());
                    String firstName = tokens[1].trim();
                    String lastName = tokens[2].trim();
                    String email = tokens[3].trim();
                    String phone = tokens[4].trim();
                    String department = tokens[5].trim();
                    double salary = Double.parseDouble(tokens[6].trim());
                    String joinDate = tokens[7].trim();

                    Employee emp = new Employee(empId, firstName, lastName, email, phone, department, salary, joinDate);
                    employees.add(emp);

                } catch (NumberFormatException e) {
                    System.out.println("⚠️ Skipped row due to invalid number format: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error reading CSV: " + e.getMessage());
        }

        return employees;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public void writeToDB(Employee emp, Connection conn) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM employee WHERE emp_id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, emp.getEmpId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("⚠️ Skipping existing emp_id " + emp.getEmpId());
                return;
            }
        }

        String insertQuery = "INSERT INTO employee (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            insertStmt.setInt(1, emp.getEmpId());
            insertStmt.setString(2, emp.getFirstName());
            insertStmt.setString(3, emp.getLastName());
            insertStmt.setString(4, emp.getEmail());
            insertStmt.setString(5, emp.getPhone());
            insertStmt.setString(6, emp.getDepartment());
            insertStmt.setDouble(7, emp.getSalary());
            insertStmt.setString(8, emp.getJoinDate());

            insertStmt.executeUpdate();
            System.out.println("✅ Inserted employee: " + emp.getEmpId());
        }
    }

    public void writeDataToDB() {
        List<Employee> employees = readCSV();

        try (Connection conn = getConnection()) {
            for (Employee emp : employees) {
                try {
                    writeToDB(emp, conn);
                } catch (SQLException e) {
                    System.out.println("❌ Failed to insert emp_id " + emp.getEmpId() + ": " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
        }
    }
}
