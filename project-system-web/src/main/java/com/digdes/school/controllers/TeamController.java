package com.digdes.school.controllers;

import com.digdes.school.dto.team.AddMemberDTO;
import com.digdes.school.dto.team.RemoveMemberDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.services.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
@Tag(name = "TeamController", description = "Контроллер команд")
public class TeamController {

    private final TeamService teamService;

    @Operation(summary = "Создание команды",
            description = "Позволяет создать команду")
    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok().body(teamService.create(teamDTO));
    }

    @Operation(summary = "Обновление команды",
            description = "Позволяет обновить команду, назначить существующий проект и создать новую команду")
    @PutMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> update(@RequestBody TeamDTO dto) {
        return ResponseEntity.ok().body(teamService.update(dto));
    }

    @Operation(summary = "Получение команды",
            description = "Позволяет получить команду по идентификатору")
    @GetMapping(value = "/get",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok().body(teamService.get(id));
    }

    @Operation(summary = "Получение всех команды",
            description = "Позволяет получить все существующие команды")
    @GetMapping(value = "/all-teams",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeamDTO>> getAll() {
        return ResponseEntity.ok().body(teamService.getAll());
    }

    @Operation(summary = "Добавить сотрудника в команду",
            description = "Позволяет добавить сотрудника в команду и задать роль в команде")
    @PutMapping(value = "/add-member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> addMember(
            @RequestBody @Valid AddMemberDTO addMemberDTO) {
        return ResponseEntity.ok().body(teamService.addMember(addMemberDTO));
    }

    @Operation(summary = "Удалить сотрудника из команды",
            description = "Позволяет удалить сотрудника из команды")
    @PutMapping(value = "/remove-member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> removeMember(
            @RequestBody @Valid RemoveMemberDTO removeMemberDTO) {
        return ResponseEntity.ok().body(teamService.removeMember(removeMemberDTO));
    }

    @Operation(summary = "Удалить команду",
            description = "Позволяет удалить команду из базы данных по идентификатору")
    @DeleteMapping(value = "/delete-from-storage",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> delete(@RequestParam Long id) {
        return ResponseEntity.ok().body(teamService.deleteFromStorage(id));
    }
}
