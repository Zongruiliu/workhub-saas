package com.workhub.app;

import com.workhub.domain.Role;
import com.workhub.domain.WorkspaceRole;
import com.workhub.entity.WorkspaceMember;
import com.workhub.infra.WorkspaceMemberRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceMemberService {

    private final WorkspaceMemberRepository repo;

    public WorkspaceMemberService(WorkspaceMemberRepository repo) {
        this.repo = repo;
    }

    public void updateRole(Long workspaceId, Long memberId, WorkspaceRole newRole) throws BadRequestException {

        WorkspaceMember member = repo.findByWorkspaceIdAndUserId(workspaceId, memberId)
                .orElseThrow(() -> new ProjectService.NotFoundException("Member not found"));

        if(member.getRole() == WorkspaceRole.OWNER && newRole != WorkspaceRole.OWNER) {
            long ownercount = repo.countByWorkspaceIdAndRole(workspaceId, WorkspaceRole.OWNER);
            if(ownercount <= 1) {
                throw new BadRequestException("Cannot demote the last OWNER");
            }
        }
        member.setRole(newRole);
        repo.save(member);
    }
}
