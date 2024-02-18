package com.model.flow.context;

import com.model.context.ThreadLocalContext;

import java.util.Map;

public class AppContextHandler {

    public static Object getPrivateArea(String key) {
        Map map = (Map) ThreadLocalContext.getServiceContext().getPrivateContext().get("privateAppMap");
        return map != null ? map.get(key) : null;
    }
}
