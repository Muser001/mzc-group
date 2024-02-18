package com.model.chain.request;

import lombok.Data;

import java.util.List;

/**
 * 根据元数据规范校验结果
 */

@Data
public class MetadataValidateResult {

    /**
     * 是否有错
     */
    boolean error;

    /**
     * 字段规范错误列表
     */
    List<ValidateInfo> msgList;
}
