package com.digdes.school.services;

import com.digdes.school.dto.team.AddMemberDTO;
import com.digdes.school.dto.team.RemoveMemberDTO;
import com.digdes.school.dto.team.TeamDTO;

public interface TeamService extends Service<TeamDTO, TeamDTO> {
    TeamDTO addMember(AddMemberDTO dto);
    TeamDTO removeMember(RemoveMemberDTO dto);
}
