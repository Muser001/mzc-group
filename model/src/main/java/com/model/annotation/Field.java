package com.model.annotation;

import java.lang.annotation.*;

/**
 * 元数据字典的注解定义
 * 注解用于DTO/VO/Entity字典上，格式如： @Field(name="BizDict.xxx")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Field {
    /**
     * id
     */
    String id() default "";

    /**
     * 名称(简介)
     */
    String name() default "";

    /**
     * 是否可空，字符串，数组，集合，map个数为0也作为空值处理
     */
    boolean isNull() default true;

    /**
     * 扩展校验字段
     */
    String extendRules() default "";
}
