package com.digdes.school.mapping;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.models.Member;

import java.util.StringJoiner;

public class MemberMapper {

    public Member create(CreateUpdateMemberDTO dto) {
        Member member = new Member();
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setMiddleName(dto.getMiddleName());
        member.setEmail(dto.getEmail());
        member.setPosition(dto.getPosition());
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
        if (entity.getMiddleName() != null) {
            displayNameJoiner.add(entity.getMiddleName());
        }
        dto.setDisplayName(displayNameJoiner.toString());

        if (entity.getPosition() != null) {
            dto.setPosition(entity.getPosition());
        }

        if (entity.getEmail() != null) {
            dto.setEmail(entity.getEmail());
        }

        return dto;
    }
}
