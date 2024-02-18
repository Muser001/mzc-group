package com.model.mapper;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据映射信息list集合
 */
@XmlType
public class DataMapping {

    /**
     * true表示直接按照配置映射，false表示按组合映射，即先按DTO同名映射再按配置映射
     */
    private Boolean byInterface = false;

    /**
     * 数据映射信息集合
     */
    private List<DataMappingDetail> mappingList = new ArrayList<>();

    @XmlAttribute
    public Boolean getByInterface() {
        return byInterface;
    }

    public void setByInterface(Boolean byInterface) {
        this.byInterface = byInterface;
    }
    @XmlElement(
            name = "mapping"
    )
    public List<DataMappingDetail> getMappingList() {
        return mappingList;
    }

    public void setMappingList(List<DataMappingDetail> mappingList) {
        this.mappingList = mappingList;
    }
}
