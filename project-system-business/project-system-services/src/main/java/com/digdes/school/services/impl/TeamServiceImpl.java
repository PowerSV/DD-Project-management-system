package com.digdes.school.services.impl;

import com.digdes.school.dto.team.AddMemberDTO;
import com.digdes.school.dto.team.RemoveMemberDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.mapping.MemberMapper;
import com.digdes.school.mapping.TeamMapper;
import com.digdes.school.models.Member;
import com.digdes.school.models.Project;
import com.digdes.school.models.Team;
import com.digdes.school.models.TeamMember;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.repos.JpaRepos.ProjectJpaRepository;
import com.digdes.school.repos.JpaRepos.TeamJpaRepository;
import com.digdes.school.repos.JpaRepos.TeamMemberJpaRepository;
import com.digdes.school.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamJpaRepository teamRepository;
    private final MemberJpaRepository memberRepository;
    private final ProjectJpaRepository projectRepository;
    private final TeamMapper teamMapper;
    private final TeamMemberJpaRepository teamMemberRepository;
    private final MemberMapper memberMapper;

    @Override
    public List<TeamDTO> getAll() {
        return teamRepository.findAll().stream()
                .map(teamMapper::map)
                .toList();
    }

    @Override
    public TeamDTO create(TeamDTO dto) {
        Team newTeam = teamMapper.create(dto);
        newTeam = teamRepository.save(newTeam);
        if (!newTeam.getTeamMembers().isEmpty()) {
            teamMemberRepository.saveAll(newTeam.getTeamMembers());
        }
        return teamMapper.map(newTeam);
    }

    @Override
    public TeamDTO update(TeamDTO teamDTO) {
        Team team = teamRepository.findById(teamDTO.getId())
                .orElseThrow();

        if (teamDTO.getProjectId() != null) {
            Project project = projectRepository.findById(teamDTO.getProjectId())
                    .orElseThrow();
            team.setProject(project);
        }
        if (teamDTO.getTeamMembers() != null) {
            System.out.println(12345);
            teamMemberRepository.deleteByTeam(team);
            System.out.println(67890);
            List<TeamMember> newTeamMembers = teamMapper.createNewTeamMembers(teamDTO.getTeamMembers(), team);
            team.setTeamMembers(newTeamMembers);
        }

        team = teamRepository.save(team);
        return teamMapper.map(team);
    }

    @Override
    public TeamDTO get(Long id) {
        return teamRepository.findById(id)
                .map(teamMapper::map)
                .orElse(new TeamDTO());
    }

    @Override
    public TeamDTO addMember(AddMemberDTO dto) {
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();
        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow();

        TeamMember teamMember = memberMapper.createTeamMember(member, team, dto.getRole());
        teamMember = teamMemberRepository.save(teamMember);

        List<TeamMember> teamMemberList = team.getTeamMembers();
        teamMemberList.add(teamMember);
        team.setTeamMembers(teamMemberList);
        team = teamRepository.save(team);
        return teamMapper.map(team);
    }

    @Override
    public TeamDTO removeMember(RemoveMemberDTO dto) {
        Team team = teamRepository.findById(dto.getTeamId()).orElseThrow();

        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow();

        if (team.getTeamMembers() != null) {
            List<TeamMember> teamMembers = team.getTeamMembers()
                    .stream()
                    .filter(tm -> !tm.getMember().equals(member))
                    .collect(Collectors.toList());

            teamMemberRepository.deleteByMember(member);

            team.setTeamMembers(teamMembers);
        }

        team = teamRepository.save(team);
        return teamMapper.map(team);
    }

    @Override
    public TeamDTO deleteFromStorage(Long id) {
        Team deletedTeam = teamRepository.findById(id).orElseThrow();
        if (deletedTeam.getTeamMembers() != null) {
            teamMemberRepository.deleteAll(deletedTeam.getTeamMembers());
            deletedTeam.setTeamMembers(null);
        }
        teamRepository.deleteTeamById(id);
        return teamMapper.map(deletedTeam);
    }
}
