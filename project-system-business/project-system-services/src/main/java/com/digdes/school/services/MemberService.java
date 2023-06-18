package com.digdes.school.services;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;

import java.util.List;

public interface MemberService extends Service<MemberDTO, CreateUpdateMemberDTO> {
    MemberDTO delete(Long id);
    List<MemberDTO> search(String filter);
    MemberDTO getMember(String account);
}
