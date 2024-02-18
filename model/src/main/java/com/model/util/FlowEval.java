package com.model.util;

import com.model.context.EngineContextWrapper;
import com.model.context.ServiceContextHandler;
import com.model.flow.context.AppContextHandler;
import com.model.flow.context.DataArea;
import com.model.flow.context.FlowEvalArea;
import com.model.flownode.FlowNode;
import io.netty.util.internal.StringUtil;

/**
 * 表达式节点执行工具类
 */
public class FlowEval {


    public static Object eval(String expr, DataArea data) {
        FlowEvalArea context = new FlowEvalArea();
        context.setInput(data.getInput());
        context.setExchange(data.getExchange());
        context.setHeader(ServiceContextHandler.getTxHeader());
        context.setCommon(ServiceContextHandler.getTxCommon());
        context.setAppPrivate(AppContextHandler.getPrivateArea("privateAppMap"));
        context.setIndexLocal(EngineContextWrapper.getIndexLocal());
        return SpelUtil.eval(expr, context);
    }

    /**
     * 节点表达式计算结果
     * @param node 节点模型 条件语法：根节点必须为：input、output、exchange
     * @param data 数据域
     * @return
     */
    public static boolean isRunnable(FlowNode node, DataArea data) {
        String expr = node.getCondition();
        try {
            if (StringUtil.isNullOrEmpty(expr)){
                return true;
            }else {
                boolean res = (boolean) eval(expr, data);
                return res;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
