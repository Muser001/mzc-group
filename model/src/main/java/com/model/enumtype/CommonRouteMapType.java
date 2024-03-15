package com.model.enumtype;

import lombok.Getter;

/**
 * 映射要素类型
 */
@Getter
public enum CommonRouteMapType {

    CUST_NO("04"),
    MEDIUM_NO("05");

    private String value;

    CommonRouteMapType(String value) {
        this.value = value;
    }
}
