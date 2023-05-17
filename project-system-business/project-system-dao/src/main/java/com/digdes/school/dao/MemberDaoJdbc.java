package com.digdes.school.dao;

import com.digdes.school.repos.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

public class MemberDaoJdbc implements Repository<Member> {
    @Override
    public Member create(Member member) {
        return null;
    }

    @Override
    public Member update(Member member) {
        return null;
    }

    @Override
    public Member deleteById(Long id) {
        return null;
    }

    @Override
    public Optional<Member> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Member> getAll() {
        return null;
    }
}
