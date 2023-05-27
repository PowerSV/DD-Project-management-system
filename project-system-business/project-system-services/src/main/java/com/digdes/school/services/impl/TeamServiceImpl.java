package com.digdes.school.services.impl;

import com.digdes.school.dto.team.AddRemoveMemberDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.mapping.TeamMapper;
import com.digdes.school.models.Member;
import com.digdes.school.models.Team;
import com.digdes.school.models.statuses.MemberRole;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.repos.JpaRepos.TeamJpaRepository;
import com.digdes.school.services.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamJpaRepository teamRepository;
    private final MemberJpaRepository memberRepository;
    private final TeamMapper teamMapper;

    public TeamServiceImpl(TeamJpaRepository teamRepository, MemberJpaRepository memberRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public TeamDTO create(TeamDTO dto) {
        Team newTeam = teamMapper.create(dto);
        newTeam = teamRepository.save(newTeam);
        return teamMapper.map(newTeam);
    }

    @Override
    public TeamDTO getTeam(Long id) {
        return teamRepository.findById(id)
                .map(teamMapper::map)
                .orElse(new TeamDTO());
    }

    @Override
    public TeamDTO addMember(AddRemoveMemberDTO dto) {
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();
        Member member = memberRepository.findById(dto.getMemberDTO().getId()).orElseThrow();
        Map<Member, MemberRole> teamMemberships = team.getTeamMemberships();
        teamMemberships.put(member, MemberRole.valueOf(dto.getRole().toUpperCase()));
        team.setTeamMemberships(teamMemberships);
        team = teamRepository.save(team);
        return teamMapper.map(team);
    }

    @Override
    public TeamDTO removeMember(AddRemoveMemberDTO dto) {
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();
        Member member = memberRepository.findById(dto.getMemberDTO().getId()).orElseThrow();
        Map<Member, MemberRole> teamMemberships = team.getTeamMemberships();
        teamMemberships.remove(member);
        team.setTeamMemberships(teamMemberships);
        team = teamRepository.save(team);
        return teamMapper.map(team);
    }
}
