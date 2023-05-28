package com.digdes.school.controllers;

import com.digdes.school.dto.team.AddRemoveMemberDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.services.TeamService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok().body(teamService.create(teamDTO));
    }

    @GetMapping(value = "/get",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok().body(teamService.getTeam(id));
    }

    @PutMapping(value = "/add-member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> addMember(@RequestBody AddRemoveMemberDTO addMemberDTO) {
        return ResponseEntity.ok().body(teamService.addMember(addMemberDTO));
    }

    @PutMapping(value = "/remove-member",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> removeMember(@RequestBody AddRemoveMemberDTO removeMemberDTO) {
        return ResponseEntity.ok().body(teamService.removeMember(removeMemberDTO));
    }
}
