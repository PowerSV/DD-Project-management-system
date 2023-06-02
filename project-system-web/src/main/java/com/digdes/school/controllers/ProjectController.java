package com.digdes.school.controllers;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/project")
@Tag(name = "ProjectController", description = "Контроллер проектов")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Operation(summary = "Создание Проекта",
            description = "позволяет создать проект")
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(projectDTO));
    }

    @PutMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> update(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok().body(projectService.update(projectDTO));
    }

    @GetMapping(value = "/get",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok().body(projectService.getProject(id));
    }

    // TODO
    @GetMapping("/search")
    public List<ProjectDTO> searchProjects() {
        return null;
    }

    @PutMapping(value = "/update-status",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> updateStatus(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok().body(projectService.updateStatus(projectDTO));
    }

    @PutMapping(value = "/{id}/set-team",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> setTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok().body(projectService.setTeam(teamDTO, id));
    }
}
