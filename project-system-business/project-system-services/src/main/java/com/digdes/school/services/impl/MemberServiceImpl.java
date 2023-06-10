package com.digdes.school.services.impl;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;
import com.digdes.school.mapping.MemberMapper;
import com.digdes.school.models.Member;
import com.digdes.school.models.statuses.MemberStatus;
import com.digdes.school.repos.JpaRepos.MemberJpaRepository;
import com.digdes.school.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final MemberJpaRepository memberRepository;

    @Override
    public MemberDTO create(CreateUpdateMemberDTO newMember) {
        Member member = memberMapper.create(newMember);
        member.setAccount(getAccount(newMember));
        member = memberRepository.save(member);
        return memberMapper.map(member);
    }

    @Override
    public MemberDTO update(CreateUpdateMemberDTO dto) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow();
        if (!member.getStatus().equals(MemberStatus.ACTIVE)) {
            throw new RuntimeException("Нельзя изменить удаленного сотрудника");
        }
        if (dto.getEmail() != null) {
            member.setEmail(dto.getEmail());
        }
        if (dto.getLastName() != null) {
            member.setLastName(dto.getLastName());
        }
        if (dto.getFirstName() != null) {
            member.setFirstName(dto.getFirstName());
        }
        if (dto.getMiddleName() != null) {
            member.setMiddleName(dto.getMiddleName());
        }
        if (dto.getAccount() != null) {
            member.setAccount(getAccount(dto));
        }
        member = memberRepository.save(member);
        return memberMapper.map(member);
    }

    private String getAccount(CreateUpdateMemberDTO dto) {
        String email = dto.getEmail();
        if (dto.getAccount() == null || dto.getAccount().isBlank()) {
            return email == null || email.isBlank() ? null : email.substring(0, email.indexOf("@"));
        }
        return dto.getAccount();
    }

    @Override
    public MemberDTO deleteFromStorage(Long id) {
        Member deletedMember = memberRepository.findById(id).orElseThrow();
        memberRepository.deleteMemberById(id);
        return memberMapper.map(deletedMember);
    }

    @Override
    public MemberDTO delete(Long id) {
        Member deletedMember = memberRepository.findById(id).orElseThrow();
        deletedMember.setStatus(MemberStatus.DELETED);
        deletedMember = memberRepository.save(deletedMember);
        return memberMapper.map(deletedMember);
    }

    //todo: search for members
    @Override
    public List<MemberDTO> search(String filter) {
        return null;
    }

    @Override
    public MemberDTO get(Long id) {
        return memberRepository.findById(id)
                .map(memberMapper::map)
                .orElse(new MemberDTO());
    }

    @Override
    public MemberDTO getMember(String account) {
        return memberRepository.findMemberByAccount(account)
                .map(memberMapper::map)
                .orElse(new MemberDTO());
    }

    @Override
    public List<MemberDTO> getAll() {
        return memberRepository.findAll().stream()
                .map(memberMapper::map)
                .toList();
    }
}
