package com.digdes.school.dto.task;

import com.digdes.school.dto.member.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    private String name;
    private String description;
    private Integer complexity;
    private Date creationDate;
    private Date lastModified;
    private Date deadline;
    private String status;
    private MemberDTO assignee;
    private MemberDTO author;
}
