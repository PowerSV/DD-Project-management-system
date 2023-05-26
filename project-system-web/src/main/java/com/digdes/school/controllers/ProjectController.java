package com.digdes.school.controllers;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.services.ProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDTO create(@RequestBody ProjectDTO projectDTO) {
        return projectService.create(projectDTO);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDTO update(@RequestBody ProjectDTO projectDTO) {
        return projectService.update(projectDTO);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDTO getById(@RequestParam Long id) {
        return projectService.getProject(id);
    }

    // TODO
    @GetMapping("/search")
    public List<ProjectDTO> searchProjects() {
        return null;
    }

    @PutMapping(value = "/update-status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ProjectDTO updateStatus(@RequestBody ProjectDTO projectDTO) {
        return projectService.updateStatus(projectDTO);
    }
}
