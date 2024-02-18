package com.model.annotation;

import com.model.enumtype.CommonRouteMapType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteKey {
    CommonRouteMapType type() default CommonRouteMapType.MEDIUM_NO;

    String value() default "";
}
