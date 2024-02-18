package com.model.annotation;

import com.model.enumtype.EngineBaseEnumType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ComposeChainStep {
    /**
     * 返回类型
     */
    EngineBaseEnumType.ServTypeCodeType[] serviceTypeCode()
            default{EngineBaseEnumType.ServTypeCodeType.NORMAL,
            EngineBaseEnumType.ServTypeCodeType.BUSI_CANCEL,
            EngineBaseEnumType.ServTypeCodeType.BUSI_REVERSE,
            EngineBaseEnumType.ServTypeCodeType.BUSI_REDO,
            EngineBaseEnumType.ServTypeCodeType.SAF_RECOVERY,
            EngineBaseEnumType.ServTypeCodeType.SAF_REDO,
            EngineBaseEnumType.ServTypeCodeType.INNER_SAF_PROCESS,
            EngineBaseEnumType.ServTypeCodeType.STATUE_QUERY,
            EngineBaseEnumType.ServTypeCodeType.ERROR_RETRY
    };

    /**
     * 分离部署标识
     */
    EngineBaseEnumType.DeployModeEnum[] deployMode()
            default {EngineBaseEnumType.DeployModeEnum.COMBINE,
            EngineBaseEnumType.DeployModeEnum.SPLIT
    };

    /**
     * 责任链执行顺序
     */
    int order();
}
