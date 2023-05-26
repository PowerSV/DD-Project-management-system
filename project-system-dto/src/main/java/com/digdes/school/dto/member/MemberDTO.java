package com.digdes.school.dto.member;

import lombok.Data;

@Data
public class MemberDTO {
    private Long id;
    private String displayName;
    private String position;
    private String email;
    private String status;
}
