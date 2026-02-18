package com.workhub.api.project.dto;

import com.workhub.domain.Role;
import jakarta.validation.constraints.NotNull;

public record MemberRoleUpdateRequest(
        @NotNull Role role
) {}
