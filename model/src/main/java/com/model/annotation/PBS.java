package com.model.annotation;

import com.model.enumtype.OperateType;
import com.model.enumtype.ServiceType;

import java.lang.annotation.*;

/**
 * 基础服务，业务代码
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PBS {
    /**
     * 服务ID
     */
    String id();

    /**
     * 服务名称
     */
    String name();

    ServiceType type() default ServiceType.ORDINARY;

    OperateType oprAtt() default OperateType.QUERY;
}
