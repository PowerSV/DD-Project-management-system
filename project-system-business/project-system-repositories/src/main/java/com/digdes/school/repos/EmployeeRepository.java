package com.digdes.school.repos;

import com.digdes.school.models.Employee;

import java.util.List;

public interface EmployeeRepository {

    Employee createEmployee(Employee employee);

    List<Employee> getAll();
}
