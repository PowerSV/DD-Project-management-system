package com.digdes.school.services;


import com.digdes.school.dto.member.CreateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;

import java.util.List;

public interface MemberService {

    MemberDTO create(CreateMemberDTO dto);

    MemberDTO update(MemberDTO dto);

    MemberDTO delete(Long id);

    List<MemberDTO> search();

    MemberDTO getMember(Long id);

    MemberDTO getMember(String uz);

    List<MemberDTO> getAll();

}
