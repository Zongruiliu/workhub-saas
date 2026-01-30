package com.workhub.security;

import com.workhub.context.WorkspaceContext;
import com.workhub.infra.WorkspaceMemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class WorkspaceContextInterceptor implements HandlerInterceptor {

    private static final String HEADER = "X-Workspace-Id";

    private final WorkspaceMemberRepository memberRepo;

    public WorkspaceContextInterceptor(WorkspaceMemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getRequestURI();
        if(!path.startsWith("/api")) {
            return true;
        }

        if(path.startsWith("/api/auth") || path.equals("/api/ping") || path.equals("/api/workspaces")) {
            return true;
        }

        String workspaceIdStr = request.getHeader(HEADER);
        if(workspaceIdStr == null || workspaceIdStr.isBlank()) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing X-Workspace-Id header");
            return false;
        }

        Long workspaceId;
        try {
            workspaceId = Long.parseLong(workspaceIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Invalid X-Workspace-Id");
            return false;
        }

        // Jwt jwt = (Jwt) request.getAttribute("org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken.jwt");
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
            return false;
        }

        Jwt jwt = jwtAuth.getToken();
        Long userId = jwt.getClaim("uid");
        if(userId == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing uid claim");
            return false;
        }

        boolean ok = memberRepo.existsByWorkspaceIdAndUserId(workspaceId, userId);
        if(!ok) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Workspace access denied");
            return false;
        }

        WorkspaceContext.set(workspaceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WorkspaceContext.clear();
    }
}
