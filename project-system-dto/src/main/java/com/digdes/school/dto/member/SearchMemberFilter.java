package com.digdes.school.dto.member;

import lombok.Data;

@Data
public class SearchMemberFilter {
    private Long teamId;
    private String firstName;
    private String email;
}
