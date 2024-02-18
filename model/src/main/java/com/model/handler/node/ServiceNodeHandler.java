package com.model.handler.node;

import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.flow.context.FlowContext;
import com.model.flow.helper.FlowHelper;
import com.model.flownode.nodeclass.FlowServiceNode;
import com.model.handler.NodeHandler;
import com.model.handler.properties.FlowSwitcherProperties;
import com.model.mapper.ServiceNodeMapper;
import com.model.process.flow.NodeApi;
import com.model.process.flow.NodeRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务节点处理器
 */
@Component
@Slf4j
public class ServiceNodeHandler implements NodeHandler<FlowServiceNode> {

    private NodeApi invokeProxy;

    @Autowired
    private FlowSwitcherProperties flowSwitch;

    /**
     *
     * @param node 节点
     * @param context 上下文
     * @param helper 映射帮助类
     * @return
     */
    @Override
    public boolean handle(FlowServiceNode node, FlowContext context, FlowHelper helper) {
        // 服务调用前数据映射
        ServiceNodeMapper mapper = helper.getMapperManager().getServiceMapper(node.getMapper());
        BaseInputDTO input = mapper.inputMapping(context.getDataArea().getInput(), context.getDataArea().getExchange());
        // 调用PBS服务
        BaseOutputDTO output;
        NodeRes nodeRes;
        boolean hasError = false;
        try {
            nodeRes = atomicSerivceInvoker(node, input, helper);
            if (nodeRes.isBreak()) {
                return false;
            }
            output = nodeRes.getBaseOutputDTO();
        } catch (Exception e) {
            hasError = true;
            if (log.isDebugEnabled()) {
                log.debug("服务节点异常");
            }
            throw new RuntimeException(e);
        }
        // 服务调用后数据映射
        if (!hasError) {
            mapper.outputMapping(output, context.getDataArea().getExchange());
        }
        return true;
    }

    /**
     *
     * @param node 服务节点对象
     * @param input 交易请求信息
     * @param helper 相关资源对象
     * @return
     */
    private NodeRes atomicSerivceInvoker(FlowServiceNode node, BaseInputDTO input, FlowHelper helper) {
        if (invokeProxy == null) {
            try {
                invokeProxy = helper.getMapperManager().getBean(flowSwitch.getInvokeProxyBean(), NodeApi.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        String atomic = node.getServiceCode();
        String dusType = node.getDusType();
        // 不是关键节点
        boolean isKeyNode = false;
        String strategy = node.getReadTimeOutProcessStrategy();
        NodeRes nodeRes = invokeProxy.process(atomic, dusType, isKeyNode, strategy, input);
        if (log.isDebugEnabled()) {
            log.debug("input[{}]=[{}]",atomic, input);
            log.debug("input[{}]=[{}]",atomic, nodeRes);
        }
        return nodeRes;
    }
}
