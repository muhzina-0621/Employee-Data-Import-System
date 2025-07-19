package litmus7.employeemanager.com.controller;

import litmus7.employeemanager.com.service.EmployeeService;
import litmus7.employeemanager.com.response.Response;
import litmus7.employeemanager.com.constant.Messages;

import java.io.File;

public class EmployeeController {
    private final EmployeeService service = new EmployeeService();

    public void uploadEmployeeData(File file) {
        if (file == null || file.length() == 0) {
            System.out.println(Messages.FILE_EMPTY);
            return;
        }

        Response response = service.processEmployeeFile(file);
        System.out.println("Status: " + response.getStatus());
        System.out.println("Message: " + response.getMessage());
    }
}
