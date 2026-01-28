package com.workhub.api;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MeController {

    @GetMapping({"/me", "/api/me"})
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of(
                "email", jwt.getSubject(),
                "uid", jwt.getClaim("uid"),
                "issuer", jwt.getIssuer() != null ? jwt.getIssuer().toString() : null,
                "expiresAt", jwt.getExpiresAt()
        );
    }

}
