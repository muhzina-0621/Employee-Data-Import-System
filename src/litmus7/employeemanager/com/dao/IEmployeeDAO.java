package litmus7.employeemanager.com.dao;

import litmus7.employeemanager.com.model.Employee;
import litmus7.employeemanager.com.response.Response;

public interface IEmployeeDAO {
    Response insertEmployee(Employee employee);
}
