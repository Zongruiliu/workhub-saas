package com.workhub.infra;

import com.workhub.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember,Long> {

    @Query("""
        select wm.workspaceId
        from WorkspaceMember wm
        where wm.userId = :userId
    """)
    List<Long> findWorkspaceIdsByUserId(Long userId);

    boolean existsByWorkspaceIdAndUserId(Long workspaceId,Long userId);

}
