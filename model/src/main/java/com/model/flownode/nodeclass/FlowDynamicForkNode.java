package com.model.flownode.nodeclass;

import com.model.flownode.FlowComposite;
import com.model.flownode.FlowNode;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态并发节点模型定义
 */
@XmlRootElement(name = "dynamicfork")
public class FlowDynamicForkNode extends FlowNode implements FlowComposite<FlowNode> {

    /**
     * 动态并发节点集合
     */
    private List<FlowNode> nodes = new ArrayList<>();

    /**
     * 额外属性，服务引擎使用
     */
    private String paras;

    @XmlAttribute(required = true)
    public String getParas() {
        return paras;
    }

    public void setParas(String paras) {
        this.paras = paras;
    }

    @Override
    @XmlElements({
            @XmlElement(name = "bean", type = FlowBeanNode.class),
            @XmlElement(name = "expr", type = FlowExprNode.class),
            @XmlElement(name = "method", type = FlowMethodNode.class),
            @XmlElement(name = "service", type = FlowServiceNode.class),
    })
    public List<FlowNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<FlowNode> nodes) {
        this.nodes = nodes;
    }
}
