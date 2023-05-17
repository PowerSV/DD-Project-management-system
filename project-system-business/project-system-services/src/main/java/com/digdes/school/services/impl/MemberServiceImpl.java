package com.digdes.school.services.impl;

import com.digdes.school.dao.MemberDaoJdbc;
import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.dto.member.SearchMemberFilter;
import com.digdes.school.mapping.MemberMapper;
import com.digdes.school.models.Member;
import com.digdes.school.repos.MemberRepository;
import com.digdes.school.services.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper = new MemberMapper();
    private final MemberRepository<Member> memberRepository = new MemberDaoJdbc(
            "jdbc:postgresql://localhost:5432/project_management_sys",
            "postgres",
            "alexey"
    );

    @Override
    public MemberDTO create(CreateUpdateMemberDTO newMember) {
        Member member = memberMapper.create(newMember);
        member = memberRepository.create(member);
        return memberMapper.map(member);
    }

    @Override
    public MemberDTO update(CreateUpdateMemberDTO dto) {
        Member member = memberMapper.create(dto);
        member.setId(dto.getId());
        member = memberRepository.update(member);
        return memberMapper.map(member);
    }

    @Override
    public MemberDTO delete(Long id) {
        Member member = memberRepository.deleteById(id);
        return memberMapper.map(member);
    }

    @Override
    public List<MemberDTO> search(SearchMemberFilter filter) {
        List<Member> members = memberRepository.searchMembers(filter);
        return members.stream()
                .map(memberMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public MemberDTO getMember(Long id) {
        Optional<Member> member = memberRepository.getById(id);
        if (member.isEmpty()) {
            return new MemberDTO();
        }
        return memberMapper.map(member.get());
    }

    @Override
    public MemberDTO getMember(String account) {
        return null;
    }

    @Override
    public List<MemberDTO> getAll() {
        List<Member> members = memberRepository.getAll();
        return members.stream()
                .map(memberMapper::map)
                .collect(Collectors.toList());
    }
}
