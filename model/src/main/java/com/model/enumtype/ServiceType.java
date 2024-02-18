package com.model.enumtype;

public enum ServiceType {
    /**
     * 普通PBS
     */
    ORDINARY,
    /**
     * 外呼PBS，调用前会自动提交本地事务，调用结束后会开始本地事务
     */
    OUTBOUND
}
