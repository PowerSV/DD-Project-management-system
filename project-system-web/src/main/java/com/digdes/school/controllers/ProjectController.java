package com.digdes.school.controllers;

import com.digdes.school.dto.project.ProjectDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.services.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@Tag(name = "ProjectController", description = "Контроллер проектов")
public class ProjectController {

    private final ProjectService projectService;

    @Operation(summary = "Создание проекта",
            description = "Позволяет создать проект")
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> create(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.create(projectDTO));
    }

    @Operation(summary = "Обновление проекта",
            description = "Позволяет обновить имя и описание проекта")
    @PutMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> update(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok().body(projectService.update(projectDTO));
    }

    @Operation(summary = "Получить проект",
            description = "Позволяет получить проект по идентификатору")
    @GetMapping(value = "/get",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok().body(projectService.get(id));
    }

    @Operation(summary = "Получить все проекты",
            description = "Позволяет получить все существующие проекты")
    @GetMapping(value = "/all-projects",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectDTO>> getAll() {
        return ResponseEntity.ok().body(projectService.getAll());
    }

    // TODO: search for project
    @Operation(summary = "Поиск проекта",
            description = "Позволяет получить проект по фильтру")
    @GetMapping("/search")
    public List<ProjectDTO> searchProjects() {
        return null;
    }


    @Operation(summary = "Обновить статус проекта",
            description = "Позволяет перевести проект в следующий статус")
    @PutMapping(value = "/update-status",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> updateStatus(@RequestParam Long id) {
        return ResponseEntity.ok().body(projectService.updateStatus(id));
    }

    @Operation(summary = "Установить команду проекту",
            description = "Позволяет задать уже существующую команду проекту или создать команду для проекта")
    @PutMapping(value = "/{id}/set-team",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> setTeam(@PathVariable Long id, @RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok().body(projectService.setTeam(teamDTO, id));
    }

    @Operation(summary = "Удалить проект",
            description = "Позволяет удалить проект по идентификатору из базы данных")
    @DeleteMapping(value = "/delete-from-storage",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectDTO> delete(@RequestParam Long id) {
        return ResponseEntity.ok().body(projectService.deleteFromStorage(id));
    }
}
