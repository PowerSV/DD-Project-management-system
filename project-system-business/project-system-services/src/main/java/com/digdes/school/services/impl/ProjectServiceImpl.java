package com.digdes.school.services.impl;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.mapping.ProjectMapper;
import com.digdes.school.mapping.TeamMapper;
import com.digdes.school.models.Project;
import com.digdes.school.models.Team;
import com.digdes.school.models.statuses.ProjectStatus;
import com.digdes.school.repos.JpaRepos.ProjectJpaRepository;
import com.digdes.school.repos.JpaRepos.TeamJpaRepository;
import com.digdes.school.services.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectJpaRepository projectRepository;
    private final TeamJpaRepository teamRepository;
    private final ProjectMapper projectMapper;
    private final TeamMapper teamMapper;

    public ProjectServiceImpl(ProjectJpaRepository projectRepository, TeamJpaRepository teamRepository,
                              ProjectMapper projectMapper, TeamMapper teamMapper) {
        this.projectRepository = projectRepository;
        this.teamRepository = teamRepository;
        this.projectMapper = projectMapper;
        this.teamMapper = teamMapper;
    }

    @Override
    public ProjectDTO create(ProjectDTO dto) {
        Project project = projectMapper.create(dto);
        project = projectRepository.save(project);

        TeamDTO teamDTO = dto.getTeamDTO();
        if (teamDTO != null) {
            if (teamDTO.getId() == null) {
                Team team = teamMapper.create(teamDTO);
                List<Project> projects = team.getProjects();
                projects.add(project);
                team.setProjects(projects);
                project.setTeam(team);
                teamRepository.save(team);
            } else {
                project.setTeam(teamRepository.findById(teamDTO.getId()).orElse(teamMapper.create(teamDTO)));
            }
            project = projectRepository.save(project);
        }
        return projectMapper.map(project);
    }

    @Override
    public ProjectDTO getProject(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::map)
                .orElse(new ProjectDTO());
    }

    @Override
    public ProjectDTO update(ProjectDTO dto) {
        Project project = projectRepository.findById(dto.getId()).orElseThrow();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project = projectRepository.save(project);
        return projectMapper.map(project);
    }

    // todo: search by status, name and id
    @Override
    public List<ProjectDTO> search(String name, List<ProjectStatus> statuses) {
        return null;
    }

    @Override
    public ProjectDTO setTeam(TeamDTO teamDTO, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        Team team = teamRepository.findById(teamDTO.getId()).orElse(teamMapper.create(teamDTO));
        project.setTeam(team);
        List<Project> projects = team.getProjects();
        projects.add(project);
        team.setProjects(projects);
        projectRepository.save(project);
        teamRepository.save(team);
        return projectMapper.map(project);
    }

    @Override
    public ProjectDTO updateStatus(ProjectDTO dto) {
        Project project = projectRepository.findById(dto.getId()).orElseThrow();
        ProjectStatus currentStatus = project.getProjectStatus();
        project.setProjectStatus(getNextStatus(currentStatus));
        project = projectRepository.save(project);
        return projectMapper.map(project);
    }

    private ProjectStatus getNextStatus(ProjectStatus status) {
        return switch (status) {
            case DRAFT -> ProjectStatus.IN_DEVELOPMENT;
            case IN_DEVELOPMENT -> ProjectStatus.IN_TESTING;
            case IN_TESTING, COMPLETED -> ProjectStatus.COMPLETED;
        };
    }
}
