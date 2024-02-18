package com.model.handler;

import com.model.flownode.FlowNode;
import com.model.flownode.nodeclass.FlowBeanNode;
import com.model.flownode.nodeclass.FlowCaseNode;
import com.model.flownode.nodeclass.FlowServiceNode;
import com.model.handler.node.BeanNodeHandler;
import com.model.handler.node.CaseNodeHandler;
import com.model.handler.node.ServiceNodeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 节点工厂
 */
@Component
public class NodeHandlerFactory {

    @Autowired
    private BeanNodeHandler beanNodeHandler;

    @Autowired
    private ServiceNodeHandler serviceNodeHandler;

    @Autowired
    private CaseNodeHandler caseNodeHandler;

    public NodeHandler gethandler(FlowNode node){
        if(node instanceof FlowBeanNode){
            return beanNodeHandler;
        }else if(node instanceof FlowServiceNode){
            return serviceNodeHandler;
        } else if (node instanceof FlowCaseNode) {
            return caseNodeHandler;
        }
        return null;
    }
}
