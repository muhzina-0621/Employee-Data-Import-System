package litmus7.employeemanager.com.validation;

import litmus7.employeemanager.com.model.Employee;

public final class EmployeeValidator {

    private EmployeeValidator() {}

    public static boolean isValid(Employee employee) {
        return isNotEmpty(employee.getName())
                && isNotEmpty(employee.getEmail())
                && isNotEmpty(employee.getDepartment())
                && employee.getSalary() > 0;
    }

    private static boolean isNotEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
