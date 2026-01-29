package com.workhub.infra;

import com.workhub.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace,Long> {
}
