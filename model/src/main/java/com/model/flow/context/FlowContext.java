package com.model.flow.context;

/**
 * 服务编排山下文
 */
public class FlowContext {

    /**
     * 交易码
     */
    private String composeCode;

    /**
     * 数据区
     */
    private DataArea dataArea;

    public String getComposeCode() {
        return composeCode;
    }

    public void setComposeCode(String composeCode) {
        this.composeCode = composeCode;
    }

    public DataArea getDataArea() {
        return dataArea;
    }

    public void setDataArea(DataArea dataArea) {
        this.dataArea = dataArea;
    }

    public FlowContext copy() throws InstantiationException, IllegalAccessException {
        FlowContext ret = new FlowContext();
        ret.setComposeCode(composeCode);
        ret.setDataArea(dataArea.copy());
        return ret;
    }
}
