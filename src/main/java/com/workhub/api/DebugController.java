package com.workhub.api;

import com.workhub.app.PermissionService;
import com.workhub.context.WorkspaceContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    private final PermissionService permissionService;

    public DebugController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/whoami")
    public Map<String, Object> whoami(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "uid", jwt.getClaim("uid"),
                "sub", jwt.getSubject(),
                "workspaceId", WorkspaceContext.get()
        );
    }

    @GetMapping("/perms")
    public Object perms(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("uid");
        Long workspaceId = WorkspaceContext.get();
        return permissionService.getPermissions(userId, workspaceId);
    }
}
