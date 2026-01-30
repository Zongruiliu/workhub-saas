package com.workhub.api.workspace;

import com.workhub.app.WorkspaceService;
import com.workhub.context.WorkspaceContext;
import com.workhub.entity.Workspace;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping
    public Workspace create(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal Jwt jwt
    ) {
        Long userId = jwt.getClaim("uid");
        return workspaceService.createWorkspace(body.get("name"), userId);
    }

    @GetMapping
    public List<Workspace> list(@AuthenticationPrincipal Jwt jwt) {
        Long userId = jwt.getClaim("uid");
        return workspaceService.listMyWorkspaces(userId);
    }

}
