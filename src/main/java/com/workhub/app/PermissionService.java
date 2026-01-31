package com.workhub.app;

import com.workhub.infra.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepo;
    private final PermissionCache cache;

    public PermissionService(PermissionRepository permissionRepo,
                             PermissionCache cache) {
        this.permissionRepo = permissionRepo;
        this.cache = cache;
    }

    public List<String> getPermissions(Long userId, Long workspaceId) {
        return cache.get(userId, workspaceId).orElseGet(() -> {
            List<String> perms = permissionRepo.findPermissionCodes(userId, workspaceId);
            cache.put(userId, workspaceId, perms);
            return perms;
        });
    }

}
