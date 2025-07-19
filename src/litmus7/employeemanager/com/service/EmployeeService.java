package litmus7.employeemanager.com.service;

import litmus7.employeemanager.com.model.Employee;
import litmus7.employeemanager.com.dao.EmployeeDAO;
import litmus7.employeemanager.com.response.Response;
import litmus7.employeemanager.com.util.ValidatorUtil;
import litmus7.employeemanager.com.constant.Messages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    public Response processEmployeeFile(File file) {
        List<Employee> validEmployees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1) continue; // skip header

                String[] data = line.split(","); // CSV is comma-separated

                if (data.length < 8) {
                    System.out.println("Skipping line " + lineNumber + ": Insufficient columns");
                    continue;
                }

                try {
                    int empId = Integer.parseInt(data[0].trim());
                    String firstName = data[1].trim();
                    String lastName = data[2].trim();
                    String email = data[3].trim();
                    String phone = data[4].trim();
                    String department = data[5].trim();
                    double salary = Double.parseDouble(data[6].trim());
                    String joinDate = data[7].trim();

                    if (!ValidatorUtil.isValidName(firstName) || !ValidatorUtil.isValidName(lastName)
                            || !ValidatorUtil.isValidEmail(email)
                            || !ValidatorUtil.isValidPhone(phone)) {
                        System.out.println("Skipping line " + lineNumber + ": Validation failed");
                        continue;
                    }

                    Employee emp = new Employee(empId, firstName, lastName, email, phone, department, salary, joinDate);
                    validEmployees.add(emp);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line " + lineNumber + ": Invalid number format - " + e.getMessage());
                }
            }

            if (validEmployees.isEmpty()) {
                return new Response("Failed", Messages.NO_VALID_RECORDS);
            }

            EmployeeDAO dao = new EmployeeDAO();
            return dao.insertEmployees(validEmployees);

        } catch (Exception e) {
            return new Response("Failed", Messages.UPLOAD_FAILED + " Error: " + e.getMessage());
        }
    }
}
