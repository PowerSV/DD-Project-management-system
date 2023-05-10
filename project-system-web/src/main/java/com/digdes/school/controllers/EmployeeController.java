package com.digdes.school.controllers;

import com.digdes.school.dto.employee.CreateEmployeeDTO;
import com.digdes.school.dto.employee.EmployeeDTO;
import com.digdes.school.services.EmployeeService;
import com.digdes.school.services.impl.EmployeeServiceImpl;

import java.util.List;

public class EmployeeController {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    public EmployeeDTO create(CreateEmployeeDTO request){
        return employeeService.create(request);
    }

    public List<EmployeeDTO> getAll(){
        return employeeService.getAll();
    }
}
