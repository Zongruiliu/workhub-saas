package com.workhub.security;

import com.workhub.app.PermissionService;
import com.workhub.context.WorkspaceContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Aspect
@Component
public class RequirePermissionAspect {

    private final PermissionService permissionService;

    public RequirePermissionAspect(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Around("@annotation(requirePermission)")
    public Object checkPermission(ProceedingJoinPoint pjp, RequirePermission requirePermission) throws Throwable {

        Long workspaceId = WorkspaceContext.get();
        if(workspaceId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing workspace context");
        }

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if(!(auth instanceof JwtAuthenticationToken jwtAuth)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Long userId = jwtAuth.getToken().getClaim("uid");
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing uid claim");
        }

        String required = normalize(requirePermission.value());

        List<String> perms = permissionService.getPermissions(userId, workspaceId);

        if(!perms.contains(required)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied" + required);
        }

        return pjp.proceed();

    }

    private String normalize(String raw) {
        if (raw == null) return "";
        return raw.trim().toUpperCase().replace(':', '_');
    }
}
