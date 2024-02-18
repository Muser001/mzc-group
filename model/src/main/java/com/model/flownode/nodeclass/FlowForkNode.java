package com.model.flownode.nodeclass;

import com.model.flownode.FlowComposite;
import com.model.flownode.FlowNode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "fork")
public class FlowForkNode extends FlowNode implements FlowComposite<FlowBlockNode> {

    private List<FlowBlockNode> nodes = new ArrayList<>();

    @Override
    @XmlElements({
            @XmlElement(name = "block", type = FlowBlockNode.class)
    })
    public List<FlowBlockNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<FlowBlockNode> nodes) {
        this.nodes = nodes;
    }
}
