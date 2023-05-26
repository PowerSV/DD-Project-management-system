package com.digdes.school.repos.JpaRepos;

import com.digdes.school.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectJpaRepository extends JpaRepository<Project, Long> {
}
