package com.digdes.school.services;

import com.digdes.school.dto.team.AddRemoveMemberDTO;
import com.digdes.school.dto.team.TeamDTO;

public interface TeamService {
    TeamDTO create(TeamDTO dto);
    TeamDTO getTeam(Long id);
    TeamDTO addMember(AddRemoveMemberDTO dto);
    TeamDTO removeMember(AddRemoveMemberDTO dto);
}
