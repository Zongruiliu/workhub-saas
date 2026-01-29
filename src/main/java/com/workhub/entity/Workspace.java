package com.workhub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "workspaces")
@Getter @Setter
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    private Instant createdAt = Instant.now();

}
