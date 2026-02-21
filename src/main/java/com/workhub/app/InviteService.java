package com.workhub.app;

import com.workhub.domain.WorkspaceInvite;
import com.workhub.domain.WorkspaceRole;
import com.workhub.entity.WorkspaceMember;
import com.workhub.infra.WorkspaceInviteRepository;
import com.workhub.infra.WorkspaceMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InviteService {

    private final WorkspaceInviteRepository inviteRepo;
    private final WorkspaceMemberRepository memberRepo;

    public InviteService(WorkspaceInviteRepository inviteRepo, WorkspaceMemberRepository memberRepo) {
        this.inviteRepo = inviteRepo;
        this.memberRepo = memberRepo;
    }

    @Transactional
    public AcceptInviteResult accept(String token, Long userId) {

        WorkspaceInvite invite = inviteRepo.findByToken(token).orElseThrow(() -> new IllegalArgumentException("Invalid invite token"));

        Long workspaceId = invite.getWorkspaceId();

        if(invite.isUsed()) {
            return new AcceptInviteResult(workspaceId, true, "already_used");
        }

        boolean alreadyMember = memberRepo.existsByWorkspaceIdAndUserId(workspaceId, userId);
        if (alreadyMember) {
            invite.setUsed(true);
            return new AcceptInviteResult(workspaceId, true, "already_member");
        }

        WorkspaceMember m = new WorkspaceMember();
        m.setWorkspaceId(workspaceId);
        m.setUserId(userId);
        m.setRole(WorkspaceRole.valueOf(invite.getRole()));
        memberRepo.save(m);

        invite.setUsed(true);

        return new AcceptInviteResult(workspaceId, true, "joined");

    }

    public record AcceptInviteResult(Long workspaceId, boolean ok, String status) {
    }
}
