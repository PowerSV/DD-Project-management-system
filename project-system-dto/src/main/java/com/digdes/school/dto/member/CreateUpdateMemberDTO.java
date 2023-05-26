package com.digdes.school.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateMemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;
    private String email;
    private String account;
}
