package com.model.util.metadata;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "field", propOrder = {"id", "name", "desc", "type", "length", "digits", "pattern", "enumerations"})
public class Field {

    /**
     * 数据字典项ID
     */
    private String id;

    /**
     * 数据字典项名称
     */
    private String name;

    /**
     * 数据字典项中文描述
     */
    private String desc;

    /**
     * 数据字典项类型
     */
    private String type;

    /**
     * 数据字典项长度
     */
    private int length;

    /**
     * 数据字典项精度
     */
    private int digits;

    /**
     * 数据字典项模式匹配表达式
     */
    private String pattern;

    /**
     * 数据字典项数据格式
     */
    private String format;

    /**
     * 枚举值
     */
    private List<com.model.util.metadata.Enumeration> enumerations = new ArrayList<>();

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @XmlAttribute
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    @XmlAttribute
    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }
    @XmlAttribute
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    @XmlAttribute
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
    @XmlElement(name = "enumeration")
    public List<com.model.util.metadata.Enumeration> getEnumerations() {
        return enumerations;
    }

    public void setEnumerations(List<Enumeration> enumerations) {
        this.enumerations = enumerations;
    }
}
