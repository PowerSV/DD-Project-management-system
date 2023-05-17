package com.digdes.school.models;

import com.digdes.school.models.statuses.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String position;
    private String account;
    private String email;
    private MemberStatus status;
}
