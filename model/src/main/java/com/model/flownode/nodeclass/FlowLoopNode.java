package com.model.flownode.nodeclass;

import com.model.flownode.FlowComposite;
import com.model.flownode.FlowNode;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "loop")
public class FlowLoopNode extends FlowNode implements FlowComposite<FlowNode> {
    @Override
    public List<FlowNode> getNodes() {
        return null;
    }
}
