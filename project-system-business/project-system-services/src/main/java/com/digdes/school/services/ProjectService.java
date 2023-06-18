package com.digdes.school.services;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.project.ProjectFilter;
import com.digdes.school.dto.team.TeamDTO;

import java.util.List;

public interface ProjectService extends Service<ProjectDTO, ProjectDTO> {

    List<ProjectDTO> search(ProjectFilter filter);
    ProjectDTO updateStatus(Long id);
    ProjectDTO setTeam(TeamDTO teamDTO, Long projectId);

}
