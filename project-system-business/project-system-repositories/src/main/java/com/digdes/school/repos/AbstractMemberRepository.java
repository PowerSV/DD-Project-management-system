package com.digdes.school.repos;

import com.digdes.school.dto.member.SearchMemberFilter;
import com.digdes.school.models.Member;

import java.util.List;

public interface AbstractMemberRepository<M extends Member> extends Repository<M> {
    List<Member> searchMembers(SearchMemberFilter filter);
}
