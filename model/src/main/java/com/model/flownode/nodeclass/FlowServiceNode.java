package com.model.flownode.nodeclass;

import com.model.enumtype.OnlineCommonEnumType;
import com.model.flownode.FlowNode;
import com.model.mapper.DataMapping;

import javax.xml.bind.annotation.*;


/**
 * 服务节点模型属性定义
 */
@XmlRootElement(name = "service")
@XmlType(propOrder = {"inMapping", "outMapping"})
public class FlowServiceNode extends FlowNode {
    /**
     * 服务码
     */
    private String ServiceCode;
    /**
     * 输入
     */
    private String input;
    /**
     * 输出
     */
    private String output;
    /**
     * mapper映射
     */
    private String mapper;
    /**
     * 是否开启fork节点
     */
    private boolean inForkNode = false;
    /**
     * 输入数据映射
     */
    private DataMapping inMapping;
    /**
     * 输出数据映射
     */
    private DataMapping outMapping;
    /**
     * 服务节点所处DUS类型B-DUS,C-DUS
     */
    private String dusType = OnlineCommonEnumType.DusType.BUDS.getValue();

    private String readTimeOutProcessStrategy;

    /**
     * 节点标志
     */
    private boolean keyNodeFlag = false;

    @XmlAttribute
    public String getServiceCode() {
        return ServiceCode;
    }

    public void setServiceCode(String serviceCode) {
        ServiceCode = serviceCode;
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
    @XmlAttribute
    public String getMapper() {
        return mapper;
    }

    public void setMapper(String mapper) {
        this.mapper = mapper;
    }
    @XmlTransient
    public boolean isInForkNode() {
        return inForkNode;
    }

    public void setInForkNode(boolean inForkNode) {
        this.inForkNode = inForkNode;
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
    @XmlAttribute
    public String getDusType() {
        return dusType;
    }

    public void setDusType(String dusType) {
        this.dusType = dusType;
    }
    @XmlAttribute
    public String getReadTimeOutProcessStrategy() {
        return readTimeOutProcessStrategy;
    }

    public void setReadTimeOutProcessStrategy(String readTimeOutProcessStrategy) {
        this.readTimeOutProcessStrategy = readTimeOutProcessStrategy;
    }

    public boolean isKeyNodeFlag() {
        return keyNodeFlag;
    }

    public void setKeyNodeFlag(boolean keyNodeFlag) {
        this.keyNodeFlag = keyNodeFlag;
    }
}
