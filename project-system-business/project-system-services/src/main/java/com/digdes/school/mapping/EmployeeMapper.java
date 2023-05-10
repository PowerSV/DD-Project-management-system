package com.digdes.school.mapping;

import com.digdes.school.models.Employee;
import com.digdes.school.dto.employee.CreateEmployeeDTO;
import com.digdes.school.dto.employee.EmployeeDTO;

import java.util.StringJoiner;

public class EmployeeMapper {

    public Employee create(CreateEmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        return employee;
    }

    public EmployeeDTO map(Employee entity) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(entity.getId());
        StringJoiner displayNameJoiner = new StringJoiner(" ");
        if (entity.getLastName() != null)
            displayNameJoiner.add(entity.getLastName());
        if (entity.getFirstName() != null)
            displayNameJoiner.add(entity.getFirstName());
        dto.setDisplayName(displayNameJoiner.toString());
        return dto;
    }

}
