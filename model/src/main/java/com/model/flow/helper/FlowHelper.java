package com.model.flow.helper;

import com.model.flow.FlowEngine;
import com.model.flow.FlowMapperManager;
import com.model.flow.model.FlowModelManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工具类汇总
 */
@Component
public class FlowHelper {

    @Autowired
    private FlowEngine flowEngine;

    @Autowired
    private FlowMapperManager mapperManager;

    @Autowired
    private FlowModelManager modelManager;

    public FlowEngine getFlowEngine() {
        return flowEngine;
    }

    public void setFlowEngine(FlowEngine flowEngine) {
        this.flowEngine = flowEngine;
    }

    public FlowMapperManager getMapperManager() {
        return mapperManager;
    }

    public void setMapperManager(FlowMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public FlowModelManager getModelManager() {
        return modelManager;
    }

    public void setModelManager(FlowModelManager modelManager) {
        this.modelManager = modelManager;
    }
}
