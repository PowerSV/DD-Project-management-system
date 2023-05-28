package com.digdes.school.controllers;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.services.MemberService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> create(@RequestBody CreateUpdateMemberDTO request){
        return ResponseEntity.ok().body(memberService.create(request));
    }

    @PutMapping(value = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> update(@RequestBody CreateUpdateMemberDTO request) {
        return ResponseEntity.ok().body(memberService.update(request));
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> getById(@RequestParam Long id) {
        return ResponseEntity.ok().body(memberService.getMember(id));
    }

    @GetMapping(value = "/all-members",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MemberDTO>> getAll() {
        return ResponseEntity.ok().body(memberService.getAll());
    }

    @DeleteMapping(value = "delete",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberDTO> deleteById(@RequestParam Long id) {
        return ResponseEntity.ok().body(memberService.delete(id));
    }
}
