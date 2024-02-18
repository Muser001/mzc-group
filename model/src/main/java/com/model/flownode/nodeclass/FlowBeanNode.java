package com.model.flownode.nodeclass;

import com.model.flownode.FlowNode;
import com.model.mapper.DataMapping;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * bean节点属性
 */
@XmlRootElement(name = "bean")
@XmlType(propOrder = {"inMapping", "outMapping"})
public class FlowBeanNode extends FlowNode {
    /**
     * bean注入
     */
    private String bean;
    /**
     * 方法
     */
    private String method;
    /**
     * 映射mapper
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
     * 数据输入mapping
     */
    private DataMapping inMapping;
    /**
     * 数据输出mapping
     */
    private DataMapping outMapping;
    @XmlAttribute
    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }
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
    @XmlElement(name = "in_mapping")
    public DataMapping getInMapping() {
        return inMapping;
    }

    public void setInMapping(DataMapping inMapping) {
        this.inMapping = inMapping;
    }
    @XmlElement(name = "out_mapping")
    public DataMapping getOutMapping() {
        return outMapping;
    }

    public void setOutMapping(DataMapping outMapping) {
        this.outMapping = outMapping;
    }
}
