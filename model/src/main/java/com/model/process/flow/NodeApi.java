package com.model.process.flow;

import com.model.dto.BaseInputDTO;


public interface NodeApi {

    NodeRes process(String atomicCode, String dusType, boolean isKeyNode, String strategy, BaseInputDTO inputDTO);
}
