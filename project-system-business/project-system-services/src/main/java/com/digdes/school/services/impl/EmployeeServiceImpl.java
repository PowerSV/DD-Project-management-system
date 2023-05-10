package com.digdes.school.services.impl;

import com.digdes.school.models.Employee;
import com.digdes.school.dto.employee.CreateEmployeeDTO;
import com.digdes.school.dto.employee.EmployeeDTO;
import com.digdes.school.repos.impl.EmployeeRepositoryImpl;
import com.digdes.school.mapping.EmployeeMapper;
import com.digdes.school.services.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeRepositoryImpl employeeRepository = new EmployeeRepositoryImpl();

    @Override
    public EmployeeDTO create(CreateEmployeeDTO newEmployee) {
        Employee employee = employeeMapper.create(newEmployee);
        employee = employeeRepository.createEmployee(employee);
        return employeeMapper.map(employee);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO dto) {
        return null;
    }

    @Override
    public EmployeeDTO delete(Long id) {
        return null;
    }

    @Override
    public List<EmployeeDTO> search() {
        return null;
    }

    @Override
    public EmployeeDTO getEmployee(Long id) {
        return null;
    }

    @Override
    public EmployeeDTO getEmployee(String uz) {
        return null;
    }

    @Override
    public List<EmployeeDTO> getAll() {
        List<Employee> members = employeeRepository.getAll();
        return members.stream().map(employeeMapper::map).collect(Collectors.toList());
    }
}
