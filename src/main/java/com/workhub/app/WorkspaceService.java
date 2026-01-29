package com.workhub.app;

import com.workhub.entity.Workspace;
import com.workhub.entity.WorkspaceMember;
import com.workhub.infra.WorkspaceMemberRepository;
import com.workhub.infra.WorkspaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepo;
    private final WorkspaceMemberRepository memberRepo;

    public WorkspaceService(WorkspaceRepository workspaceRepo, WorkspaceMemberRepository memberRepo) {
        this.workspaceRepo = workspaceRepo;
        this.memberRepo = memberRepo;
    }

    public Workspace createWorkspace(String name, Long userId){
        Workspace ws = new Workspace();
        ws.setName(name);
        ws.setCreatedBy(userId);

        Workspace saved =  workspaceRepo.save(ws);

        WorkspaceMember owner = new WorkspaceMember();
        owner.setWorkspaceId(saved.getId());
        owner.setUserId(userId);
        owner.setRole("OWNER");

        memberRepo.save(owner);

        return saved;
    }

    public List<Workspace> listMyWorkspaces(Long userId) {
        List<Long> ids = memberRepo.findWorkspaceIdsByUserId(userId);
        return workspaceRepo.findAllById(ids);
    }

}
