package com.digdes.school.repos.impl;

import com.digdes.school.models.Employee;
import com.digdes.school.repos.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public Employee createEmployee(Employee employee) {
        employee.setId(idGenerator.incrementAndGet());
        employees.add(employee);
        return employee;
    }

    @Override
    public List<Employee> getAll() {
        return employees;
    }
}
