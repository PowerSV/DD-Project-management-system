package com.digdes.school.services.impl;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.project.ProjectFilter;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.mapping.ProjectMapper;
import com.digdes.school.mapping.TeamMapper;
import com.digdes.school.models.Project;
import com.digdes.school.models.Team;
import com.digdes.school.models.statuses.ProjectStatus;
import com.digdes.school.repos.JpaRepos.ProjectJpaRepository;
import com.digdes.school.repos.JpaRepos.TeamJpaRepository;
import com.digdes.school.repos.specifications.ProjectSpecification;
import com.digdes.school.services.ProjectService;
import com.digdes.school.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectJpaRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TeamJpaRepository teamRepository;
    private final TeamMapper teamMapper;
    private final TeamService teamService;

    @Override
    public List<ProjectDTO> getAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::map)
                .toList();
    }

    @Override
    public ProjectDTO deleteFromStorage(Long id) {
        Project deletedProject = projectRepository.findById(id).orElseThrow();
        if (deletedProject.getTeam() != null) {
            teamService.deleteFromStorage(deletedProject.getTeam().getId());
        }
        projectRepository.deleteProjectById(id);
        return projectMapper.map(deletedProject);
    }

    @Override
    public ProjectDTO create(ProjectDTO dto) {
        Project project = projectMapper.create(dto);
        project = projectRepository.save(project);

        TeamDTO teamDTO = dto.getTeamDTO();
        if (teamDTO != null) {
            if (teamDTO.getId() == null) {
                Team team = teamMapper.create(teamDTO);
                team.setProject(project);
                project.setTeam(team);
                teamRepository.save(team);
            } else {
                project.setTeam(teamRepository.findById(teamDTO.getId())
                        .orElseThrow());
            }
            project = projectRepository.save(project);
        }
        return projectMapper.map(project);
    }

    @Override
    public ProjectDTO get(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::map)
                .orElse(new ProjectDTO());
    }

    @Override
    public ProjectDTO update(ProjectDTO dto) {
        Project project = projectRepository.findById(dto.getId()).orElseThrow();
        if (!dto.getName().isBlank()) {
            project.setName(dto.getName());
        }
        if (!dto.getDescription().isBlank()) {
            project.setDescription(dto.getDescription());
        }
        project = projectRepository.save(project);
        return projectMapper.map(project);
    }

    @Override
    public List<ProjectDTO> search(ProjectFilter filter) {
        Specification<Project> spec = ProjectSpecification.searchByFilter(filter);
        return projectRepository.findAll(spec)
                .stream()
                .map(projectMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectDTO setTeam(TeamDTO teamDTO, Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        Team team = teamRepository.findById(teamDTO.getId()).orElse(teamMapper.create(teamDTO));
        project.setTeam(team);
        team.setProject(project);
        projectRepository.save(project);
        teamRepository.save(team);
        return projectMapper.map(project);
    }

    @Override
    public ProjectDTO updateStatus(Long id) {
        Project project = projectRepository.findById(id).orElseThrow();
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
