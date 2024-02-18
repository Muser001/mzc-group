package com.model.dto;

import java.io.Serializable;

/**
 * 流程交换数据区，交换dto需要继承该dto类
 * @param <T>
 */
public abstract class FlowExchangeDTO<T> implements Serializable {

    private static final long serialVersionUID = 5057160924425500335L;

    /**
     * 针对循环节点 当前循环的参数对象
     */
    private T loopObject;

    public T getLoopObject() {
        return loopObject;
    }

    public void setLoopObject(T loopObject) {
        this.loopObject = loopObject;
    }
}
