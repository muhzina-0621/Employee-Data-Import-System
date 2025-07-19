package litmus7.employeemanager.com;

import litmus7.employeemanager.com.model.Employee;
import litmus7.employeemanager.com.dao.EmployeeDAO;
import litmus7.employeemanager.com.dao.IEmployeeDAO;
import litmus7.employeemanager.com.response.Response;
import litmus7.employeemanager.com.validation.EmployeeValidator;

public class Main {
    public static void main(String[] args) {
        // Create a sample employee
        Employee employee = new Employee("Alice Johnson", "alice@example.com", "Engineering", 70000);

        // Validate the employee
        if (EmployeeValidator.isValid(employee)) {
            IEmployeeDAO employeeDAO = new EmployeeDAO();
            Response response = employeeDAO.insertEmployee(employee);
            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Message: " + response.getMessage());
        } else {
            System.out.println("Invalid employee data. Please check the inputs.");
        }
    }
}
