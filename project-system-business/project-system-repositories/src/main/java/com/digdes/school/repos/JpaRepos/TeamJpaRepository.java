package com.digdes.school.repos.JpaRepos;

import com.digdes.school.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamJpaRepository extends JpaRepository<Team, Long> {
}
