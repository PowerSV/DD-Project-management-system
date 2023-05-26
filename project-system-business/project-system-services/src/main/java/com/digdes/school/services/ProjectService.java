package com.digdes.school.services;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.models.statuses.ProjectStatus;

import java.util.List;

public interface ProjectService {

    ProjectDTO create(ProjectDTO dto);

    ProjectDTO getProject(Long id);

    ProjectDTO update(ProjectDTO dto);

    List<ProjectDTO> search(String name, List<ProjectStatus> statuses);

    ProjectDTO updateStatus(ProjectDTO dto);

    ProjectDTO setTeam(TeamDTO teamDTO, Long projectId);
}
