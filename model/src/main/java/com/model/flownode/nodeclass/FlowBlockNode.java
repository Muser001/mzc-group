package com.model.flownode.nodeclass;

import com.model.flownode.FlowComposite;
import com.model.flownode.FlowNode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "block")
public class FlowBlockNode extends FlowNode implements FlowComposite<FlowNode> {

    /**
     * 创建块节点集合
     */
    private List<FlowNode> nodes = new ArrayList<>();

    /**
     * 记录所有节点id
     */
    private List<String> serviceIds = new ArrayList<>();

    public void setNodes(List<FlowNode> nodes) {
        this.nodes = nodes;
    }
    @XmlTransient
    public List<String> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(List<String> serviceIds) {
        this.serviceIds = serviceIds;
    }
    @XmlElements({
            @XmlElement(name = "bean", type = FlowBeanNode.class),
            @XmlElement(name = "expr", type = FlowExprNode.class),
            @XmlElement(name = "method", type = FlowMethodNode.class),
            @XmlElement(name = "service", type = FlowServiceNode.class),
    })
    @Override
    public List<FlowNode> getNodes() {
        return nodes;
    }
}
