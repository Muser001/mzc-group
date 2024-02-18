package com.model.flow.context;

import com.model.dto.FlowExchangeDTO;
import com.model.message.ServiceCommonRequestMsg;
import com.model.message.header.ServiceRequestHeader;
import lombok.Data;

@Data
public class FlowEvalArea {

    /**
     * 输入DTO
     */
    private Object input;
    /**
     * 流程交换区
     */
    private FlowExchangeDTO exchange;

    protected ServiceRequestHeader header = new ServiceRequestHeader();

    protected ServiceCommonRequestMsg common = new ServiceCommonRequestMsg();
    /**
     * 结构上下文
     */
    protected Object appPrivate = AppContextHandler.getPrivateArea("privateAppMap");
    /**
     * 线程序号
     */
    private int indexLocal = 0;
}
