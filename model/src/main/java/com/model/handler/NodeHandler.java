package com.model.handler;

import com.model.flow.context.FlowContext;
import com.model.flow.helper.FlowHelper;
import com.model.flownode.FlowNode;

/**
 * 节点处理器接口
 * @param <T>
 */
public interface NodeHandler<T extends FlowNode> {

    /**
     * 节点处理
     * @param node 节点
     * @param flowContext 上下文
     * @param helper 映射帮助类
     * @return
     */
    boolean handle(T node, FlowContext flowContext, FlowHelper helper);
}
