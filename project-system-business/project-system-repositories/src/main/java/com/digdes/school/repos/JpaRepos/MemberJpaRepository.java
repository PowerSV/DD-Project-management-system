package com.digdes.school.repos.JpaRepos;

import com.digdes.school.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Member m WHERE m.id = :id")
    void deleteMemberById(@Param("id") Long id);

    Optional<Member> findMemberByAccount(String account);
}
