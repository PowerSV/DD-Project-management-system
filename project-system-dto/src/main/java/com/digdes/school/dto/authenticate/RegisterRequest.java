package com.digdes.school.dto.authenticate;

import com.digdes.school.dto.member.CreateUpdateMemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends CreateUpdateMemberDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String position;
    private String email;
    private String account;
    private String password;
}
