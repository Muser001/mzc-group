package com.model.chain.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;


/**
 * 校验信息
 */
@Data
@AllArgsConstructor
public class ValidateInfo {
    /**
     * obj字段
     */
    Field field;

    /**
     * 错误码
     */
    String errorCode;

    /**
     * 错误信息
     */
    String errorMessage;
}
