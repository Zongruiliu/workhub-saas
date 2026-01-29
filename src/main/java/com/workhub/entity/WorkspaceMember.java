package com.workhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(
        name = "workspace_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"workspace_id", "user_id"})
)
@Getter @Setter
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "workspace_id", nullable = false)
    private Long workspaceId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String role;

    private Instant joinedAt = Instant.now();

}

