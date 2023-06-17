package com.digdes.school.mapping;

import com.digdes.school.dto.member.MemberRoleDTO;
import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.models.Member;
import com.digdes.school.models.Project;
import com.digdes.school.models.Team;
import com.digdes.school.models.TeamMember;
import com.digdes.school.models.statuses.MemberStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.repos.JpaRepos.ProjectJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class TeamMapper {
    private final MemberJpaRepository memberRepository;
    private final ProjectJpaRepository projectRepository;
    private final MemberMapper memberMapper;

    public Team create(TeamDTO dto) {
        log.info("Creating new team");
        Team newTeam = new Team();

        List<TeamMember> teamMembers = new ArrayList<>();
        if (dto.getTeamMembers() != null) {
            teamMembers = createNewTeamMembers(dto.getTeamMembers(), newTeam);
        }
        newTeam.setTeamMembers(teamMembers);

        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow();
            newTeam.setProject(project);
        }
        log.info("New team created");
        return newTeam;
    }

    public List<TeamMember> createNewTeamMembers(Collection<MemberRoleDTO> teamMembersDTO, Team team) {
        log.info("Creating new team members");
        List<TeamMember> teamMembers = new ArrayList<>();

        for (MemberRoleDTO teamMemberDto : teamMembersDTO) {
            Long memberId = teamMemberDto.getMember().getId();
            Member member = memberRepository.findById(memberId)
                    .orElseThrow();

            if (member.getStatus() == MemberStatus.DELETED) {
                log.error("You can't add deleted member");

                throw new IllegalArgumentException("You can't add deleted member " + member + " in team ");
            }

            TeamMember newTeamMember = memberMapper.createTeamMember(member, team, teamMemberDto.getRole());
            teamMembers.add(newTeamMember);
        }

        log.info("New team members created");
        return teamMembers;
    }

    public TeamDTO map(Team entity) {
        log.info("Mapping team to DTO: {}", entity);

        if (entity == null) {
            return null;
        }

        Long projectId = Optional.ofNullable(entity.getProject())
                .map(Project::getId)
                .orElse(null);

        String projectName = Optional.ofNullable(entity.getProject())
                .map(Project::getName)
                .orElse(null);

        List<MemberRoleDTO> memberRoles = Optional.ofNullable(entity.getTeamMembers())
                .map(list -> list.stream()
                        .map(memberMapper::mapToMemberRoleDTO)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        TeamDTO teamDTO = new TeamDTO(entity.getId(), projectId, projectName, memberRoles);
        log.info("Mapped team to DTO successfully");
        return teamDTO;
    }
}
