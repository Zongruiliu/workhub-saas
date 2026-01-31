package com.workhub.infra;

import com.workhub.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, String> {

    @Query("""
        select p.code
        from WorkspaceMember wm
        join Role r on r.code = wm.role
        join RolePermission rp on rp.roleId = r.id
        join Permission p on p.id = rp.permissionId
        where wm.userId = :userId
          and wm.workspaceId = :workspaceId
        order by p.code
    """)
    List<String> findPermissionCodes(@Param("userId") Long userId,
                                     @Param("workspaceId") Long workspaceId);

}
