package com.digdes.school.services;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.models.statuses.ProjectStatus;

import java.util.List;

public interface ProjectService extends Service<ProjectDTO, ProjectDTO> {

    List<ProjectDTO> search(String name, List<ProjectStatus> statuses);
    ProjectDTO updateStatus(Long id);
    ProjectDTO setTeam(TeamDTO teamDTO, Long projectId);

}
