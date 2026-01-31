package com.workhub.api;

import com.workhub.app.PermissionService;
import com.workhub.context.WorkspaceContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionDebugController {

    private final PermissionService permissionService;

    public PermissionDebugController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public List<String> myPermissions(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("uid");
        Long workspaceId = WorkspaceContext.get();
        return permissionService.getPermissions(userId, workspaceId);
    }

}
