package com.digdes.school.dto.team;

import com.digdes.school.dto.member.MemberDTO;
import lombok.Data;

@Data
public class AddRemoveMemberDTO {
    private Long teamId;
    private MemberDTO memberDTO;
    private String role;
}
