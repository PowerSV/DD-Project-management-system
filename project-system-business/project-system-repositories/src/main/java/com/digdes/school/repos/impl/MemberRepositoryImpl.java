package com.digdes.school.repos.impl;

import com.digdes.school.models.Member;
import com.digdes.school.repos.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MemberRepositoryImpl implements MemberRepository {

    private final List<Member> members = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(0L);

    @Override
    public Member createMember(Member member) {
        member.setId(idGenerator.incrementAndGet());
        members.add(member);
        return member;
    }

    @Override
    public List<Member> getAll() {
        return members;
    }
}
