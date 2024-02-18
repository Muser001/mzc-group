package com.model.annotation;


import com.model.enumtype.EngineBaseEnumType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface AtomicChainStep {

    /**
     * 业务方向
     * @return
     */
    EngineBaseEnumType.BizExeDirectEnum[] bizRegDirect()
            default {EngineBaseEnumType.BizExeDirectEnum.NORMAL,
                EngineBaseEnumType.BizExeDirectEnum.COMPENSATE};

    /**
     * 分离部署标识
     */
    EngineBaseEnumType.DeployModeEnum[] deployMode()
            default {EngineBaseEnumType.DeployModeEnum.COMBINE,
                EngineBaseEnumType.DeployModeEnum.SPLIT};

    /**
     * 责任链执行顺序
     */
    int order();
}
