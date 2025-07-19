package com.employee;
public class EmployeeManagerApp {
    public static void main(String[] args) {
        EmployeeManagerController controller = new EmployeeManagerController();
        controller.writeDataToDB();
    }
}
