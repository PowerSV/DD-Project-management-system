package com.digdes.school.services.impl;

import com.digdes.school.models.Member;
import com.digdes.school.dto.member.CreateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.repos.impl.MemberRepositoryImpl;
import com.digdes.school.mapping.MemberMapper;
import com.digdes.school.services.MemberService;

import java.util.List;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper = new MemberMapper();
    private final MemberRepositoryImpl memberRepository = new MemberRepositoryImpl();

    @Override
    public MemberDTO create(CreateMemberDTO newMember) {
        Member member = memberMapper.create(newMember);
        member = memberRepository.createMember(member);
        return memberMapper.map(member);
    }

    @Override
    public MemberDTO update(MemberDTO dto) {
        return null;
    }

    @Override
    public MemberDTO delete(Long id) {
        return null;
    }

    @Override
    public List<MemberDTO> search() {
        return null;
    }

    @Override
    public MemberDTO getMember(Long id) {
        return null;
    }

    @Override
    public MemberDTO getMember(String uz) {
        return null;
    }

    @Override
    public List<MemberDTO> getAll() {
        List<Member> members = memberRepository.getAll();
        return members.stream().map(memberMapper::map).collect(Collectors.toList());
    }
}
