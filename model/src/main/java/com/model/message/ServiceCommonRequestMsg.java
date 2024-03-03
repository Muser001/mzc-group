package com.model.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class ServiceCommonRequestMsg implements Serializable {
    private static final long serialVersionUID = 5516108629482621168L;
    private Map<String,Object> engineInfo = new HashMap<>();
}
