package com.digdes.school.services;


import com.digdes.school.dto.employee.CreateEmployeeDTO;
import com.digdes.school.dto.employee.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO create(CreateEmployeeDTO dto);

    EmployeeDTO update(EmployeeDTO dto);

    EmployeeDTO delete(Long id);

    List<EmployeeDTO> search();

    EmployeeDTO getEmployee(Long id);

    EmployeeDTO getEmployee(String uz);

    List<EmployeeDTO> getAll();

}
