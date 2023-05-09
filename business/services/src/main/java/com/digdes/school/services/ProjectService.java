package com.digdes.school.services;

import com.digdes.school.ProjectStatus;
import com.digdes.school.employee.EmployeeDTO;
import com.digdes.school.project.CreateProjectDTO;
import com.digdes.school.project.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO create(CreateProjectDTO dto);

    ProjectDTO update(ProjectDTO dto);

    List<ProjectDTO> search(String name);

    ProjectDTO updateStatus(ProjectDTO dto, ProjectStatus status);
}
