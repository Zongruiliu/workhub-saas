package com.workhub.app;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class NoopPermissionCache implements PermissionCache{

    @Override
    public Optional<List<String>> get(Long userId, Long workspaceId) {
        return Optional.empty();
    }

    @Override
    public void put(Long userId, Long workspaceId, List<String> permissions) {}

    @Override
    public void evict(Long userId, Long workspaceId) {}

}
