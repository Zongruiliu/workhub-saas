package com.workhub.api;

import com.workhub.security.RequirePermission;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    @PostMapping
    @RequirePermission("project:create")
    public Map<String, Object> create() {
        return Map.of("ok", true);
    }

    @GetMapping
    @RequirePermission("project:read")
    public Map<String, Object> list() {
        return Map.of("ok", true);
    }

}
