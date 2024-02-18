package com.model.util.metadata;


import lombok.Data;

@Data
public class ValidateResult {
    /**
     * 校验结果
     */
    boolean resultFlag = true;

    /**
     * 校验失败原因
     */
    String errorMsg;

    public ValidateResult(boolean resultFlag) {
        this.resultFlag = resultFlag;
    }

    public ValidateResult(boolean resultFlag, String errorMsg) {
        this.resultFlag = resultFlag;
        this.errorMsg = errorMsg;
    }
}
