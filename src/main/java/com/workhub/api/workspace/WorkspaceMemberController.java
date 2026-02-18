package com.workhub.api.workspace;

import com.workhub.app.WorkspaceMemberService;
import com.workhub.domain.WorkspaceRole;
import com.workhub.security.RequirePermission;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/workspaces/{workspaceId}/members")
public class WorkspaceMemberController {

    private final WorkspaceMemberService service;

    public WorkspaceMemberController(WorkspaceMemberService service) {
        this.service = service;
    }

    @PatchMapping("/{memberId}/role")
    @RequirePermission("workspace:member:update")
    public void updateRole(@PathVariable Long workspaceId,
                           @PathVariable Long memberId,
                           @RequestBody Map<String, Object> body) throws BadRequestException {


        Object raw = body.get("role");
        if (raw == null) {
            throw new BadRequestException("Missing role");
        }

        WorkspaceRole newRole;
        try {
            newRole = WorkspaceRole.valueOf(raw.toString().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid role: " + raw);
        }
        service.updateRole(workspaceId, memberId, newRole);
    }

    public record UpdateRoleRequest(WorkspaceRole role) {}
}
