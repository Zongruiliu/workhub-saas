package com.workhub.infra;

import com.workhub.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByWorkspaceIdOrderByIdDesc(Long workspaceId);

    Optional<Project> findByIdAndWorkspaceId(Long id, Long workspaceId);

    boolean existsByWorkspaceIdAndName(Long workspaceId, String name);

}
