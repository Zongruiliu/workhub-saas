package com.workhub.app;

import com.workhub.context.WorkspaceContext;
import com.workhub.domain.Project;
import com.workhub.infra.ProjectRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository repo;

    public ProjectService(ProjectRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Project create(String name, String description){

        Long ws = WorkspaceContext.get();

        if(repo.existsByWorkspaceIdAndName(ws,name)){
            throw new IllegalArgumentException("Project name already exists in this workspace");
        }

        return repo.save(new Project(ws, name, description));
    }

    @Transactional(readOnly = true)
    public List<Project> list() {
        Long ws = WorkspaceContext.get();
        return repo.findAllByWorkspaceIdOrderByIdDesc(ws);
    }

    @Transactional(readOnly = true)
    public Project get(Long id) {
        Long ws = WorkspaceContext.get();
        return repo.findByIdAndWorkspaceId(id, ws)
                .orElseThrow(() -> new NotFoundException("Project not found"));
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String msg) { super(msg); }
    }

}
