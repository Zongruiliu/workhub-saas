package com.workhub.api.project;

import com.workhub.api.project.dto.ProjectCreateRequest;
import com.workhub.app.ProjectService;
import com.workhub.domain.Project;
import com.workhub.security.RequirePermission;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @PostMapping
    @RequirePermission("project:create")
    public Map<String, Object> create(@Valid @RequestBody ProjectCreateRequest req) {
        Project p = service.create(req.name(), req.description());
        return Map.of("id", p.getId(), "name", p.getName());
    }

    @GetMapping
    @RequirePermission("project:read")
    public List<Map<String, Object>> list() {
        return service.list().stream()
                .map(p -> {
                    Map<String, Object> m = new java.util.HashMap<>();
                    m.put("id", p.getId());
                    m.put("name", p.getName());
                    return m;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @RequirePermission("project:read")
    public Map<String, Object> get(@PathVariable Long id) {
        Project p = service.get(id);
        return Map.of("id", p.getId(), "name", p.getName(), "description", p.getDescription());
    }

}
