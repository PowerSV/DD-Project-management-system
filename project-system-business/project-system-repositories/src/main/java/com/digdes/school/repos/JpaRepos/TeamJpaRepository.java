package com.digdes.school.repos.JpaRepos;

import com.digdes.school.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Team t WHERE t.id = :id")
    void deleteTeamById(@Param("id") Long id);
}
