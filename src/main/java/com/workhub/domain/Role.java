package com.workhub.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    protected Role() {}

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }
}
