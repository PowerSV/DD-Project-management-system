package com.digdes.school.dto.task;

import com.digdes.school.dto.member.MemberDTO;
import lombok.Data;

import java.util.Date;

@Data
public class CreateTaskDTO {
    private Long id;
    private String name;
    private String description;
    private MemberDTO assignee;
    private Integer complexity;
    private Date deadline;
    private MemberDTO author;
}
