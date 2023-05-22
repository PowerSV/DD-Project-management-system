package com.digdes.school.controllers;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.services.MemberService;

import java.util.List;

public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public MemberDTO create(CreateUpdateMemberDTO request){
        return memberService.create(request);
    }

    public MemberDTO update(CreateUpdateMemberDTO request) {
        return memberService.update(request);
    }

    public MemberDTO getById(Long id) {
        return memberService.getMember(id);
    }

    public List<MemberDTO> getAll() {
        return memberService.getAll();
    }

    public MemberDTO deleteById(Long id) {
        return memberService.delete(id);
    }
}
