package com.model.flownode;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * 流程节点模型属性定义
 */
@XmlType(propOrder = {"id", "condition", "longname", "description"})
public class FlowNode {
    /**
     * 节点id
     */
    private String id;

    /**
     * 节点名
     */
    private String longname;

    /**
     * 节点描述
     */
    private String description;

    /**
     * 节点情况
     */
    private String condition;
    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }
    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @XmlAttribute
    public String getCondition() {
        if (condition != null){
            return condition.trim();
        }
        return "";
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
