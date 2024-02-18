package com.model.handler.node;

import com.model.flow.context.FlowContext;
import com.model.flow.helper.FlowHelper;
import com.model.flownode.FlowNode;
import com.model.flownode.nodeclass.FlowCaseNode;
import com.model.flownode.nodeclass.FlowWhenNode;
import com.model.handler.NodeHandler;
import com.model.util.FlowEval;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 条件节点处理器
 */
@Component
public class CaseNodeHandler implements NodeHandler<FlowCaseNode> {

    /**
     *
     * @param node 节点
     * @param flowContext 上下文
     * @param helper 映射帮助类
     * @return
     */
    @Override
    public boolean handle(FlowCaseNode node, FlowContext flowContext, FlowHelper helper) {
        List<FlowWhenNode> whenNodes = node.getNodes();
        for (FlowWhenNode whenNode : whenNodes) {
            if (!FlowEval.isRunnable(whenNode, flowContext.getDataArea())) {
                continue;
            }
            List<FlowNode> nodes = whenNode.getNodes();
            for (FlowNode blockNode : nodes) {
                helper.getFlowEngine().run(blockNode, flowContext, helper);
            }
        }
        return true;
    }
}
