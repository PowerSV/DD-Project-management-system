package com.digdes.school.services.impl;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.mapping.MemberMapper;
import com.digdes.school.models.Member;
import com.digdes.school.models.statuses.MemberStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final MemberJpaRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, MemberJpaRepository memberRepository) {
        this.memberMapper = memberMapper;
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDTO create(CreateUpdateMemberDTO newMember) {
        Member member = memberMapper.create(newMember);
        member.setStatus(MemberStatus.ACTIVE);
        String email = member.getEmail();
        String account = email == null || email.isBlank() ? null : email.substring(0, email.indexOf("@"));
        member.setAccount(account);
        member = memberRepository.save(member);
        return memberMapper.map(member);
    }

    @Override
    public MemberDTO update(CreateUpdateMemberDTO dto) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow();
        member.setEmail(dto.getEmail());
        member.setLastName(dto.getLastName());
        member.setFirstName(dto.getFirstName());
        member.setMiddleName(dto.getMiddleName());
        member = memberRepository.save(member);
        return memberMapper.map(member);
    }

    @Override
    public MemberDTO delete(Long id) {
        Member deletedMember = memberRepository.findById(id).orElseThrow();
        memberRepository.deleteMemberById(id);
        return memberMapper.map(deletedMember);
    }

    @Override
    public MemberDTO getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isEmpty()) {
            return new MemberDTO();
        }
        return memberMapper.map(member.get());
    }

    @Override
    public MemberDTO getMember(String account) {
        Optional<Member> member = memberRepository.findMemberByAccount(account);
        if (member.isEmpty()) {
            return new MemberDTO();
        }
        return memberMapper.map(member.get());
    }

    @Override
    public List<MemberDTO> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(memberMapper::map)
                .collect(Collectors.toList());
    }
}
