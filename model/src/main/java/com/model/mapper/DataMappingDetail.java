package com.model.mapper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 数据映射信息
 */
@XmlType(name = "",propOrder = {"src", "dest", "description"})
@XmlRootElement
public class DataMappingDetail {
    /**
     * 路径
     */
    private String src;

    /**
     * 目的
     */
    private String dest;

    /**
     * 数据描述信息
     */
    private String description;

    @XmlAttribute
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
    @XmlAttribute
    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }
    @XmlAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
