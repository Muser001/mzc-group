package com.model.context.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ServiceContext {
    public CommonContext commonContext = new CommonContext();
    public SharedContext sharedContext = new SharedContext();
    public Map<String,Object> privateContext = new HashMap<>();
}
