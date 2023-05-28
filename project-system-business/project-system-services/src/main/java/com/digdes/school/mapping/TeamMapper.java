package com.digdes.school.mapping;

import com.digdes.school.dto.team.TeamDTO;
import com.digdes.school.models.Member;
import com.digdes.school.models.Project;
import com.digdes.school.models.Team;
import com.digdes.school.models.statuses.MemberRole;
import com.digdes.school.models.statuses.MemberStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.repos.JpaRepos.ProjectJpaRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TeamMapper {
    private final MemberJpaRepository memberRepository;
    private final ProjectJpaRepository projectRepository;

    public TeamMapper(MemberJpaRepository memberRepository, ProjectJpaRepository projectRepository) {
        this.memberRepository = memberRepository;
        this.projectRepository = projectRepository;
    }

    public Team create(TeamDTO dto) {
        Team newTeam = new Team();

        Map<Member, MemberRole> teamMemberships = new HashMap<>();
        if (dto.getTeamMembership() != null) {
            for (Map.Entry<Long, String> dtoEntry : dto.getTeamMembership().entrySet()) {
                Long memberId = dtoEntry.getKey();
                Member member = memberRepository.findById(memberId).orElseThrow();
                if (member.getStatus() == MemberStatus.DELETED) {
                    throw new IllegalArgumentException("You can't add deleted member " + member + " in team ");
                }
                MemberRole role = MemberRole.valueOf(dtoEntry.getValue().toUpperCase());
                teamMemberships.put(member, role);
            }
        }

        newTeam.setTeamMemberships(teamMemberships);

        if (dto.getProjectIds() != null) {
            List<Project> projects = projectRepository.findAllById(dto.getProjectIds());
            newTeam.setProjects(projects);
        } else {
            newTeam.setProjects(new ArrayList<>());
        }
        return newTeam;
    }

    public TeamDTO map(Team entity) {
        if (entity == null) {
            return null;
        }
        return new TeamDTO(
                entity.getId(),
                entity.getProjects().stream()
                        .map(Project::getId)
                        .collect(Collectors.toList()),
                entity.getTeamMemberships().entrySet()
                        .stream()
                        .collect(Collectors.toMap(
                                e -> e.getKey().getId(),
                                e -> e.getValue().toString()
                        ))
        );
    }
}
