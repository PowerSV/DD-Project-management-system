package com.digdes.school.repos.JpaRepos;

import com.digdes.school.models.Member;
import com.digdes.school.models.Team;
import com.digdes.school.models.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberJpaRepository extends JpaRepository<TeamMember, Long> {

    void deleteByMember(Member member);

    @Modifying
    @Query("DELETE FROM TeamMember tm WHERE tm.team = :team")
    void deleteByTeam(@Param("team") Team team);


    @Modifying
    @Query("DELETE FROM TeamMember tm WHERE tm.member.email = :email")
    void deleteTeamMemberByEmail(@Param("email") String email);
}
