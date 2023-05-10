package com.digdes.school.services.impl;

import com.digdes.school.models.statuses.ProjectStatus;
import com.digdes.school.dto.project.CreateProjectDTO;
import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.services.ProjectService;

import java.util.List;

public class ProjectServiceImpl implements ProjectService {
    @Override
    public ProjectDTO create(CreateProjectDTO dto) {
        return null;
    }

    @Override
    public ProjectDTO update(ProjectDTO dto) {
        return null;
    }

    @Override
    public List<ProjectDTO> search(String name) {
        return null;
    }

    @Override
    public ProjectDTO updateStatus(ProjectDTO dto, ProjectStatus status) {
        return null;
    }
}
