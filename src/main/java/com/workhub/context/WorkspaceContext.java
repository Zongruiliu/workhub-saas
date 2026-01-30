package com.workhub.context;

import com.workhub.entity.Workspace;

public class WorkspaceContext {
    private static final ThreadLocal<Long> WORKSPACE_ID = new ThreadLocal<>();

    private WorkspaceContext(){}

    public static void set(Long workspaceId){
        WORKSPACE_ID.set(workspaceId);
    }

    public static Long get(){
        return WORKSPACE_ID.get();
    }

    public static void clear(){
        WORKSPACE_ID.remove();
    }
}
