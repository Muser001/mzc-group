package com.model.flownode;

import java.util.List;

/**
 * 复合节点集合
 * @param <T>
 */
public interface FlowComposite <T extends FlowNode>{

    /**
     * 返回节点id
     * @return
     */
    String getId();

    /**
     * 返回节点列表
     * @return
     */
    List<T> getNodes();
}
