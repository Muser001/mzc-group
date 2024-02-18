package com.model.flownode;

import com.model.flownode.nodeclass.FlowBeanNode;
import com.model.flownode.nodeclass.FlowServiceNode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 复合节点集合配置
 */
@XmlRootElement(name = "flow")
public class FlowConf extends FlowNode{

    /**
     * 创建节点集合
     */
    private List<FlowNode> nodes = new ArrayList<>();

    @XmlElements({
            @XmlElement(name = "bean", type = FlowBeanNode.class),
            @XmlElement(name = "service", type = FlowServiceNode.class)
    })
    public List<FlowNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<FlowNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "FlowConf{" +
                "nodes=" + nodes +
                '}';
    }
}
