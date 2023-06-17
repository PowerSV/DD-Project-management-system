package com.digdes.school.mapping;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.dto.member.MemberRoleDTO;
import com.digdes.school.models.Member;
import com.digdes.school.models.Team;
import com.digdes.school.models.TeamMember;
import com.digdes.school.models.statuses.MemberRole;
import com.digdes.school.models.statuses.MemberStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

@Component
@Log4j2
public class MemberMapper {

    public <D extends CreateUpdateMemberDTO> Member create(D dto) {
        Member member = new Member();
        member.setFirstName(dto.getFirstName());
        member.setLastName(dto.getLastName());
        member.setMiddleName(dto.getMiddleName());
        member.setEmail(dto.getEmail());
        member.setAccount(dto.getAccount());
        member.setPosition(dto.getPosition());
        member.setStatus(MemberStatus.ACTIVE);
        member.setAuthoritiesRole("ROLE_USER");

        log.info("Mapper create a new member: {}", member);
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

        dto.setPosition(entity.getPosition());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus().toString());
        log.info("Mapped entity {} to DTO {}", entity, dto);
        return dto;
    }

    public TeamMember createTeamMember(Member member, Team team, String role) {
         TeamMember teamMember = TeamMember.builder()
                .role(MemberRole.valueOf(role.toUpperCase()))
                .member(member)
                .team(team)
                .build();
        log.info("Created a new team member: {}", teamMember);
        return teamMember;
    }

    public MemberRoleDTO mapToMemberRoleDTO(TeamMember teamMember) {
        MemberDTO memberDTO = map(teamMember.getMember());
        MemberRoleDTO memberRoleDTO = new MemberRoleDTO(memberDTO, teamMember.getRole().toString());
        log.info("Mapped TeamMember {} to MemberRoleDTO {}", teamMember, memberRoleDTO);
        return memberRoleDTO;
    }
}
