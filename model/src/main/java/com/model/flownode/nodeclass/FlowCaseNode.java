package com.model.flownode.nodeclass;

import com.model.flownode.FlowComposite;
import com.model.flownode.FlowNode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 条件节点模型属性定义
 */
@XmlRootElement(name = "case")
public class FlowCaseNode extends FlowNode implements FlowComposite<FlowWhenNode> {


    private List<FlowWhenNode> nodes = new ArrayList<>();

    @Override
    @XmlElements({
            @XmlElement(name = "when", type = FlowWhenNode.class)
    })
    public List<FlowWhenNode> getNodes() {
        return nodes;
    }
    public void setNodes(List<FlowWhenNode> nodes) {
        this.nodes = nodes;
    }
}
