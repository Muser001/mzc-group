package com.model.flownode.nodeclass;

import com.model.flownode.FlowNode;
import com.model.mapper.DataMapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 方法节点模型定义
 */
public class FlowMethodNode extends FlowNode {

    /**
     * 方法节点
     */
    private String method;

    /**
     * mapper映射
     */
    private String mapper;

    /**
     * 输入
     */
    private String input;

    /**
     * 输出
     */
    private String output;
    /**
     * 输入数据映射
     */
    private DataMapping inMapping;
    /**
     * 输出数据映射
     */
    private DataMapping outMapping;

    @XmlAttribute
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    @XmlAttribute
    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }
    @XmlAttribute
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
    @XmlAttribute
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    @XmlElement(
            name = "in_mappings"
    )
    public DataMapping getInMapping() {
        return inMapping;
    }

    public void setInMapping(DataMapping inMapping) {
        this.inMapping = inMapping;
    }
    @XmlElement(
            name = "out_mappings"
    )
    public DataMapping getOutMapping() {
        return outMapping;
    }

    public void setOutMapping(DataMapping outMapping) {
        this.outMapping = outMapping;
    }
}
