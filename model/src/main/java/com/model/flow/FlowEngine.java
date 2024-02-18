package com.model.flow;

import com.model.flow.context.DataArea;
import com.model.flow.context.FlowContext;
import com.model.flow.helper.FlowHelper;
import com.model.flownode.FlowNode;
import com.model.flownode.FlowTransactionConf;
import com.model.flownode.nodeclass.FlowServiceNode;
import com.model.handler.NodeHandlerFactory;
import com.model.util.FlowEval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 流程执行
 */
@Component
public class FlowEngine {

    @Autowired
    private NodeHandlerFactory nodeHandlerFactory;

    /**
     * 遍历流程节点（入口）
     * @param conf 事务配置
     * @param context 上下文信息
     * @param helper 相关资源对象
     */
    public void execute(FlowTransactionConf conf, FlowContext context, FlowHelper helper) {
        List<FlowNode> nodes = conf.getFlow().getNodes();
        boolean isKeyNodeExecuted = false;
        for (FlowNode node : nodes) {
            if (null == node) {
                continue;
            }
            if (isNotRunnable(node, context.getDataArea())) {
                continue;
            }

            boolean isContinue;

            try {
                isContinue = nodeHandlerFactory.gethandler(node).handle(node, context, helper);
            } catch (Throwable t) {
                if (isKeyNodeExecuted) {
                    break;
                }else {
                    throw t;
                }
            }
            // 关键节点没有执行过 && 服务节点 && 关键节点标识为true
            if (!isKeyNodeExecuted && node instanceof FlowServiceNode && ((FlowServiceNode) node).isKeyNodeFlag()) {
                isKeyNodeExecuted = true;
            }

            if (!isContinue) {
                break;
            }
        }
    }

    /**
     * 各类节点处理
     * @param node 节点
     * @param context 上下文信息
     * @param helper 相关资源
     * @return
     */
    public boolean run(FlowNode node, FlowContext context, FlowHelper helper) {
        //判断当前节点是否需要执行
        if (isNotRunnable(node, context.getDataArea())) {
            //不必执行返回true，不影响后续节点处理
            return true;
        }

        boolean isContinue = nodeHandlerFactory.gethandler(node).handle(node, context, helper);
        return isContinue;
    }

    private boolean isNotRunnable(FlowNode node, DataArea data) {
        return !FlowEval.isRunnable(node, data);
    }
}
