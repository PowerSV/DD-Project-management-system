package com.digdes.school.services;

import com.digdes.school.models.statuses.ProjectStatus;
import com.digdes.school.dto.project.CreateProjectDTO;
import com.digdes.school.dto.project.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO create(CreateProjectDTO dto);

    ProjectDTO update(ProjectDTO dto);

    List<ProjectDTO> search(String name);

    ProjectDTO updateStatus(ProjectDTO dto, ProjectStatus status);
}
