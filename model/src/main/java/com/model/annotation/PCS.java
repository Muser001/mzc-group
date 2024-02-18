package com.model.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PCS {
    /**
     * 服务ID
     */
    String id();

    /**
     * 服务名称
     */
    String name();
}
