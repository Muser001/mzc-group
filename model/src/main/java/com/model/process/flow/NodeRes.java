package com.model.process.flow;

import com.model.dto.BaseOutputDTO;
import lombok.Data;

@Data
public class NodeRes {
    /**
     * 输出
     */
    private BaseOutputDTO baseOutputDTO;
    /**
     * 节点状态
     */
    private String status;
    /**
     * 节点状态
     */
    private boolean isBreak;



}
