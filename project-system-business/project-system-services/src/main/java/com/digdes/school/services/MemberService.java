package com.digdes.school.services;


import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;

import java.util.List;

public interface MemberService {

    MemberDTO create(CreateUpdateMemberDTO dto);

    MemberDTO update(CreateUpdateMemberDTO dto);

    MemberDTO delete(Long id);

    MemberDTO getMember(Long id);

    MemberDTO getMember(String uz);

    List<MemberDTO> getAll();

}
