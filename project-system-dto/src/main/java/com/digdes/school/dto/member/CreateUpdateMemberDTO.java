package com.digdes.school.dto.member;

import lombok.Data;

@Data
public class CreateUpdateMemberDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;
    private String email;
}
