package com.digdes.school.repos.JpaRepos;

import com.digdes.school.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepository extends JpaRepository<Task, Long> {
}
