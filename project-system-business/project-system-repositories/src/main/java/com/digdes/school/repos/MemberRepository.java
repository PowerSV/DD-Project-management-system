package com.digdes.school.repos;

import com.digdes.school.models.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository <M extends Member> {

    M createMember(M m);

    M updateMember(M m);

    M deleteById(Long id);

    List<M> searchMembers(String pattern);

    Optional<M> getById(Long id);

    List<M> getAll();
}
