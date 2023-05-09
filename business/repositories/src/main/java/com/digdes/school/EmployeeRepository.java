package com.digdes.school;

import java.util.List;

public interface EmployeeRepository {

    Employee createEmployee(Employee employee);

    List<Employee> getAll();
}
