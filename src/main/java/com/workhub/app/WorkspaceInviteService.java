package com.workhub.app;

import com.workhub.domain.WorkspaceInvite;
import com.workhub.infra.WorkspaceInviteRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class WorkspaceInviteService {

    private final WorkspaceInviteRepository repo;

    public WorkspaceInviteService(WorkspaceInviteRepository repo) {
        this.repo = repo;
    }

    public String createInvite(Long workspaceId, Long creatorId, String role) {
        String token = UUID.randomUUID().toString();
        WorkspaceInvite invite = new WorkspaceInvite();
        invite.setWorkspaceId(workspaceId);
        invite.setToken(token);
        invite.setRole(role);
        invite.setCreatedBy(creatorId);
        invite.setCreatedAt(Instant.now());
        invite.setUsed(false);

        repo.save(invite);
        return token;
    }
}
