package com.model.register;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegister {

    public void register() {
        RegisterConfig registerConfig = new RegisterConfig();
        registerConfig.setServiceName("DEFAULT-SERVICE-" + "MODEL");
        registerConfig.setProtocol("rest");
        Map<String, String> parameters = new HashMap<>();
        parameters.put("default.return","false");
        parameters.put("interface", registerConfig.getServiceName());
        registerConfig.setParameters(parameters);
    }
}
