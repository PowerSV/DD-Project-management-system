package com.digdes.school.mapping;

import com.digdes.school.models.Member;
import com.digdes.school.dto.member.CreateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;

import java.util.StringJoiner;

public class MemberMapper {

    public Member create(CreateMemberDTO dto) {
        Member member = new Member();
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        return member;
    }

    public MemberDTO map(Member entity) {
        MemberDTO dto = new MemberDTO();
        dto.setId(entity.getId());
        StringJoiner displayNameJoiner = new StringJoiner(" ");
        if (entity.getLastName() != null)
            displayNameJoiner.add(entity.getLastName());
        if (entity.getFirstName() != null)
            displayNameJoiner.add(entity.getFirstName());
        dto.setDisplayName(displayNameJoiner.toString());
        return dto;
    }

}
