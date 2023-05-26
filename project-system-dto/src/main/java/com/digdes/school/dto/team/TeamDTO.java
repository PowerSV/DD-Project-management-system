package com.digdes.school.dto.team;

import com.digdes.school.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {
    private Long id;
    private List<Long> projectIds;
    private Map<MemberDTO, String> teamMembership;
}
