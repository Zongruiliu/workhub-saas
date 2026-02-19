package com.workhub.api.workspace;

import com.workhub.app.WorkspaceInviteService;
import com.workhub.domain.WorkspaceInvite;
import com.workhub.security.RequirePermission;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceInviteController {

    private final WorkspaceInviteService service;

    public WorkspaceInviteController(WorkspaceInviteService service) {
        this.service = service;
    }

    @PostMapping("/{id}/invites")
    @RequirePermission("workspace:member:update")
    public Map<String, String> createInvite(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt, @RequestBody Map<String, String> body) {

        Long userId = jwt.getClaim("uid");
        String role = body.getOrDefault("role", "MEMBER");
        String token = service.createInvite(id, userId, role);
        return Map.of("token", token);

    }
}
