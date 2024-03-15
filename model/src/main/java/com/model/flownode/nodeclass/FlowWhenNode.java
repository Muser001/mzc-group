package com.model.flownode.nodeclass;

import com.model.flownode.FlowComposite;
import com.model.flownode.FlowNode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程节点集合
 */
public class FlowWhenNode extends FlowNode implements FlowComposite<FlowNode> {

    private List<FlowNode> nodes = new ArrayList<>();

    @Override
    @XmlElements({
            @XmlElement(name = "case", type = FlowCaseNode.class),
            @XmlElement(name = "block", type = FlowBlockNode.class),
            @XmlElement(name = "method", type = FlowMethodNode.class),
            @XmlElement(name = "bean", type = FlowBeanNode.class),
            @XmlElement(name = "service", type = FlowServiceNode.class),
            @XmlElement(name = "fork", type = FlowForkNode.class),
            @XmlElement(name = "dynamicfork", type = FlowDynamicForkNode.class),
            @XmlElement(name = "expr", type = FlowExprNode.class),
    })
    public List<FlowNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<FlowNode> nodes) {
        this.nodes = nodes;
    }
}
