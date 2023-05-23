package com.digdes.school.models;

import com.digdes.school.models.statuses.MemberRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Data
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Project project;

    @ElementCollection
    @CollectionTable(name = "team_membership", joinColumns = @JoinColumn(name = "team_id"))
    @MapKeyJoinColumn(name = "member_id")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Map<Member, MemberRole> teamMemberships;
}
