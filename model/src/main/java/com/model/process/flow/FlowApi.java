package com.model.process.flow;

import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;

/**
 * 服务编排接口
 */
public interface FlowApi {
    BaseOutputDTO callFlow(BaseInputDTO input);
}
