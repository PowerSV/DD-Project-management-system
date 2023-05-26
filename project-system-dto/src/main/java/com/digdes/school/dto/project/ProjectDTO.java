package com.digdes.school.dto.project;

import com.digdes.school.dto.team.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private Long id;
    private String name;
    private String description;
    private String status;
    private TeamDTO teamDTO;
}
