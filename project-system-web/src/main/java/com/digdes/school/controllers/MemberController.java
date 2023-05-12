package com.digdes.school.controllers;

import com.digdes.school.dto.member.CreateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.services.MemberService;
import com.digdes.school.services.impl.MemberServiceImpl;

import java.util.List;

public class MemberController {

    private final MemberService memberService = new MemberServiceImpl();

    public MemberDTO create(CreateMemberDTO request){
        return memberService.create(request);
    }

    public List<MemberDTO> getAll(){
        return memberService.getAll();
    }
}
