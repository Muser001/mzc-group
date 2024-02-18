package com.model.flow.model;

import com.model.flownode.FlowTransactionConf;
import com.model.util.ContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Slf4j
public class FlowModelManager implements InitializingBean {

    /**
     * 加载流程定义文件获取模型对象
     */
    @Autowired
    private FlowModelAdaptorInterface flowModelAdaptorInterface;

    /**
     * 模型校验失败时是否跳过该流程，继续其他流程
     */
    private boolean isSkip = false;
    /**
     * 模型缓存
     */
    private Map<String, FlowTransactionConf> flowModelCache = new HashMap<>();


    public FlowTransactionConf getFlowConf(String flowId) {
        return flowModelCache.get(flowId);
    }
    public Map<String, FlowTransactionConf> getFlowModelCache() {
        return flowModelCache;
    }

    public void loadAllFlow() {
       long startTime = System.currentTimeMillis();
       if (log.isDebugEnabled()) {
           log.debug("编排文件加载开始===========" + startTime);
       }
       flowModelAdaptorInterface = (FlowModelAdaptor)ContextUtil.getBean("flowModelAdaptor");
       flowModelCache = flowModelAdaptorInterface.getFlowModelMap(isSkip);
       long endTime = System.currentTimeMillis();
        if (log.isDebugEnabled()) {
            log.debug("编排文件加载结束===========" + endTime);
        }
        int loadNum = flowModelCache.size();
        Iterator<Map.Entry<String, FlowTransactionConf>> iterable = flowModelCache.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<String, FlowTransactionConf> entry = iterable.next();
            FlowTransactionConf conf = entry.getValue();
            if (log.isDebugEnabled()) {
                log.debug("流程ID:{},流程定义信息:{}", entry.getKey(), conf.toString());
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("加载到的模型数量:{},加载耗时:{}",loadNum, (endTime - startTime)/1000);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        loadAllFlow();
    }
}
