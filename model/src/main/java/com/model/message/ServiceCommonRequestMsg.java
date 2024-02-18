package com.model.message;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ServiceCommonRequestMsg {
    private Map<String,Object> engineInfo = new HashMap<>();
}
