package com.model.register;

import lombok.Data;

import java.util.Map;

@Data
public class RegisterConfig {
    private String serviceName;
    private String protocol;
    private String group;
    private String version;
    private int timeout;
    private Map<String, String> parameters;
}
