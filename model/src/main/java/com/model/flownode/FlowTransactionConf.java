package com.model.flownode;

import com.model.flownode.FlowNode;
import com.model.flownode.properties.FlowProperties;
import com.model.mapper.DataMapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(
        name = "transflow"
)
@XmlType(
        propOrder = {"flowProps", "flow", "outMapping"}
)
public class FlowTransactionConf extends FlowNode {
    /**
     * 输入
     */
    private String input = "";
    /**
     * 交换机
     */
    private String exchange = "";
    /**
     * 输出
     */
    private String output = "";
    /**
     * mapper映射
     */
    private String mapper = "";
    /**
     * 流程节点
     */
    private FlowConf flow;
    /**
     * 输出数据映射
     */
    private DataMapping outMapping;
    /**
     * 额外属性，服务引擎使用
     */
    private FlowProperties flowProps;

    public FlowTransactionConf() {
    }

    @XmlAttribute(
            required = true
    )
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
    @XmlAttribute(
            required = true
    )
    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
    @XmlAttribute(
            required = true
    )
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    @XmlAttribute(
            required = true
    )
    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }

    public FlowConf getFlow() {
        return flow;
    }

    public void setFlow(FlowConf flow) {
        this.flow = flow;
    }
    @XmlElement
    public DataMapping getOutMapping() {
        return outMapping;
    }

    public void setOutMapping(DataMapping outMapping) {
        this.outMapping = outMapping;
    }
    @XmlElement(
        name = "properties"
    )
    public FlowProperties getFlowProps() {
        return flowProps;
    }

    public void setFlowProps(FlowProperties flowProps) {
        this.flowProps = flowProps;
    }
}
