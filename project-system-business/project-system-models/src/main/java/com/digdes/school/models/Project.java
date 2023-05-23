package com.digdes.school.models;

import com.digdes.school.models.statuses.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus projectStatus;

    @OneToOne
    private Team team;
}
