package litmus7.employeemanager.com;

import litmus7.employeemanager.com.controller.EmployeeController;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("employees.csv");
        EmployeeController controller = new EmployeeController();
        controller.uploadEmployeeData(file);
    }
}
