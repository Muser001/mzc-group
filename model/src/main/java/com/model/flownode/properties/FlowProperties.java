package com.model.flownode.properties;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 交易静态信息配置
 */

@XmlType(propOrder = {"chkMacFlg", "inTxStep", "oprAtt", "outTxStep", "txnFlag", "safSupportType",
        "safCompensatorCode", "reverseSevrNo", "cancelServNo", "checkFlag", "transProp", "TransactionMode"})
public class FlowProperties {

    /**
     * 组合服务性质 1查询类 2维护类 3财务类
     */
    private String oprAtt = "3";

    /**
     * 是否开启分布式事务 true-开启，false-不开启
     */
    private boolean txnFlag = true;

    /**
     * 非事务管理的责任链应用扩展beanId,以逗号分隔
     */
    private String outTxStep;

    private String inTxStep;

    /**
     * 不用了
     */
    private String macGroupId;

    private boolean chkMacFlg = false;

    /**
     * saf类型，0-不支持，1-saf补偿，2-saf重做，3-1,2都支持
     */
    private int safSupportType = 0;

    /**
     * saf补偿使用新的交易码
     */
    private String safCompensatorCode = "";

    /**
     * 补偿交易服务号
     */
    private String reverseSevrNo;

    /**
     * 取消交易服务号
     */
    private String cancelServNo;

    /**
     * 是否为审核类交易
     */
    private boolean checkFlag = false;

    /**
     * 交易性质 0-正常交易，1-冲正交易，2-取消交易 3-有源调整交易，4-无源调整交易，5-统一冲正交易，6-统一取消交易，7-调整取消，8-通用组合服务映射转换
     */
    private String transProp = "0";

    /**
     * 分布式事务模式，1-SAGA模式，2-TCC模式
     */
    private int transactionMode = 1;

    @XmlElement
    public String getOprAtt() {
        return oprAtt;
    }

    public void setOprAtt(String oprAtt) {
        this.oprAtt = oprAtt;
    }
    @XmlElement
    public boolean isTxnFlag() {
        return txnFlag;
    }

    public void setTxnFlag(boolean txnFlag) {
        this.txnFlag = txnFlag;
    }
    @XmlElement
    public String getOutTxStep() {
        return outTxStep;
    }

    public void setOutTxStep(String outTxStep) {
        this.outTxStep = outTxStep;
    }
    @XmlElement
    public String getInTxStep() {
        return inTxStep;
    }

    public void setInTxStep(String inTxStep) {
        this.inTxStep = inTxStep;
    }
    @XmlElement
    public String getMacGroupId() {
        return macGroupId;
    }

    public void setMacGroupId(String macGroupId) {
        this.macGroupId = macGroupId;
    }
    @XmlElement
    public boolean isChkMacFlg() {
        return chkMacFlg;
    }

    public void setChkMacFlg(boolean chkMacFlg) {
        this.chkMacFlg = chkMacFlg;
    }
    @XmlElement
    public int getSafSupportType() {
        return safSupportType;
    }

    public void setSafSupportType(int safSupportType) {
        this.safSupportType = safSupportType;
    }
    @XmlElement
    public String getSafCompensatorCode() {
        return safCompensatorCode;
    }

    public void setSafCompensatorCode(String safCompensatorCode) {
        this.safCompensatorCode = safCompensatorCode;
    }
    @XmlElement
    public String getReverseSevrNo() {
        return reverseSevrNo;
    }

    public void setReverseSevrNo(String reverseSevrNo) {
        this.reverseSevrNo = reverseSevrNo;
    }
    @XmlElement
    public String getCancelServNo() {
        return cancelServNo;
    }

    public void setCancelServNo(String cancelServNo) {
        this.cancelServNo = cancelServNo;
    }
    @XmlElement
    public boolean isCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(boolean checkFlag) {
        this.checkFlag = checkFlag;
    }
    @XmlElement
    public String getTransProp() {
        return transProp;
    }

    public void setTransProp(String transProp) {
        this.transProp = transProp;
    }
    @XmlElement
    public int getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(int transactionMode) {
        this.transactionMode = transactionMode;
    }
}
