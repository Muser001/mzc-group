package com.model.util.metadata.ext;


import com.model.util.metadata.ValidateResult;

/**
 * 元数据校验扩展接口
 */
public interface MetadataValidateExt {

    ValidateResult extendedValidate(String extendRules, Object value);
}
