package com.model.handler.node;


import com.model.dto.BaseOutputDTO;
import com.model.flow.context.FlowContext;
import com.model.flow.helper.FlowHelper;
import com.model.flownode.nodeclass.FlowBeanNode;
import com.model.handler.NodeHandler;
import com.model.mapper.FlowNodeMapper;
import com.model.util.ClassUtil;
import org.springframework.stereotype.Component;

/**
 * bean节点处理器
 */
@Component
public class BeanNodeHandler implements NodeHandler<FlowBeanNode> {

    @Override
    public boolean handle(FlowBeanNode node, FlowContext context, FlowHelper helper) {
        FlowNodeMapper mapper = helper.getMapperManager().getBeanMapper(node.getMapper());
        Object input = mapper.inputMapping(context.getDataArea().getInput(), context.getDataArea().getExchange());
        //获取bean对象
        Object target = null;
        try {
            target = helper.getMapperManager().getBean(node.getBean());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 获取方法名
        String methodName = getMethodName(node.getMethod());
        // 调用Bean方法
        BaseOutputDTO output = (BaseOutputDTO) ClassUtil.methodInvoke(target, methodName, input);
        // 返回结果
        if (output != null) {
            mapper.outputMapping(output, context.getDataArea().getExchange());
        }
        return true;
    }

    /**
     * 获取方法名称
     * @param fullName 名称
     * @return
     */
    private static String getMethodName(String fullName) {
        int index = fullName.lastIndexOf(".");
        if (index > 0) {
            return fullName.substring(index + 1);
        }else {
            return fullName;
        }
    }
}
