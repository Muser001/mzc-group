package com.model.flow.model;

import com.model.flownode.FlowTransactionConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
public class FlowFileLoadJob implements Callable<LoadFuture> {

    private Map<String, FlowTransactionConf> flowModelCache;

    private Resource resource;

    public FlowFileLoadJob(Map<String, FlowTransactionConf> flowModelCache, Resource resource) {
        this.flowModelCache = flowModelCache;
        this.resource = resource;
    }

    @Override
    public LoadFuture call() throws Exception {
        String flowFileName = this.resource.getFilename();

        try {
            log.debug("Starting load file {}", flowFileName);
            FlowTransactionConf flow = ModelAdaptor.idModel2Corebanking(this.resource.getInputStream(), flowFileName);
            FlowTransactionConf flowModel = (FlowTransactionConf) this.flowModelCache.putIfAbsent(flow.getId(), flow);
            if (null != flowModel) {
                throw new RuntimeException("流程[" + flow.getId() + "]ID重复，流程定义文件名称：" + flowFileName);
            }else {
                log.debug("{} load completed", flowFileName);
                return new LoadFuture(flowFileName, true, (Exception)null);
            }
        } catch (Exception e) {
            return new LoadFuture(flowFileName, false, e);
        }

    }
}
