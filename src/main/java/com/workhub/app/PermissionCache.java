package com.workhub.app;

import java.util.List;
import java.util.Optional;

public interface PermissionCache {

    Optional<List<String>> get(Long userId, Long workspaceId);
    void put(Long userId, Long workspaceId, List<String> permissions);
    void evict(Long userId, Long workspaceId);

}
