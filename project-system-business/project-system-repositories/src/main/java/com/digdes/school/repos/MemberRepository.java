package com.digdes.school.repos;

import com.digdes.school.models.Member;

import java.util.List;

public interface MemberRepository {

    Member createMember(Member member);

    List<Member> getAll();
}
