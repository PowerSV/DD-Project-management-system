package com.digdes.school.repos.JpaRepos;

import com.digdes.school.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Member deleteMemberById(Long id);
    Optional<Member> findMemberByAccount(String account);
}
