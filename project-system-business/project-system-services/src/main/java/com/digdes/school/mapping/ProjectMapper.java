package com.digdes.school.mapping;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.models.Project;
import com.digdes.school.models.statuses.ProjectStatus;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    private final TeamMapper teamMapper;

    public ProjectMapper(TeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    public Project create(ProjectDTO dto) {
        Project newProject = new Project();
        newProject.setName(dto.getName());
        newProject.setDescription(dto.getDescription());
        newProject.setProjectStatus(ProjectStatus.DRAFT);
        return newProject;
    }

    //todo: teamDTO
    public ProjectDTO map(Project project) {
        return new ProjectDTO(project.getId(),
                project.getName(),
                project.getDescription(),
                project.getProjectStatus().toString(),
                teamMapper.map(project.getTeam()));
    }
}
