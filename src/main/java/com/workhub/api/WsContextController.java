package com.workhub.api;

import com.workhub.context.WorkspaceContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ws-context")
public class WsContextController {

    // for temp use
    @GetMapping
    public Map<String, Object> ctx() {
        Map<String, Object> res = new HashMap<>();
        res.put("workspaceId", WorkspaceContext.get());
        return res;
    }



}
