package com.model.flownode.nodeclass;

import com.model.flownode.FlowNode;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 表达式节点模型定义
 */
@XmlRootElement(name = "expr")
public class FlowExprNode extends FlowNode {

    /**
     * 表达式
     */
    private String expr;

    /**
     * 模型
     */
    private String mode;

    /**
     * 错误码
     */
    private String errCode;

    /**
     * 错误提示信息
     */
    private String errMessage;
    @XmlAttribute(required = true)
    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }
    @XmlAttribute(required = true)
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    @XmlAttribute
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
    @XmlAttribute
    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
