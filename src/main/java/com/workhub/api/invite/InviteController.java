package com.workhub.api.invite;

import com.workhub.app.InviteService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/invites")
public class InviteController {

    private final InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @PostMapping("/accept")
    public Map<String, Object> accept(@AuthenticationPrincipal Jwt jwt,
                                      @Valid @RequestBody AcceptInviteRequest req) {

        Long userId = jwt.getClaim("uid");

        InviteService.AcceptInviteResult result = inviteService.accept(req.token(), userId);

        return Map.of(
                "ok", result.ok(),
                "status", result.status(),
                "workspaceId", result.workspaceId()
        );
    }
}
