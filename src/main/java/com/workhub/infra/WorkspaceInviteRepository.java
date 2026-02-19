package com.workhub.infra;

import com.workhub.domain.WorkspaceInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceInviteRepository extends JpaRepository<WorkspaceInvite,Long> {
    Optional<WorkspaceInvite> findByToken(String token);
}
