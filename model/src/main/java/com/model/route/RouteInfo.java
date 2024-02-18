package com.model.route;

import com.model.enumtype.CommonRouteMapType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * RouteKey注解对应的bean,value这里为字段
 */
@Data
@AllArgsConstructor
public class RouteInfo {

    /**
     * 路由类型
     */
    private CommonRouteMapType type;

    /**
     * 字段值
     */
    private String value;
}
