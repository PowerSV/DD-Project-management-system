package com.digdes.school.services;


import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import com.digdes.school.dto.member.MemberDTO;

import java.util.List;

public interface MemberService {

    MemberDTO create(CreateUpdateMemberDTO dto);

    MemberDTO update(CreateUpdateMemberDTO dto);

    MemberDTO deleteFromStorage(Long id);

    MemberDTO delete(Long id);

    List<MemberDTO> search(String filter);

    MemberDTO getMember(Long id);

    MemberDTO getMember(String account);

    List<MemberDTO> getAll();

}
