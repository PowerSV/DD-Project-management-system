package com.digdes.school.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Member implements Serializable {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String position;
    private String account;
    private String email;
//    private MemberStatus status;
}
