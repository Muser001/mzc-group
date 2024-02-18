package com.model.flowconfig;

import com.model.flow.model.FlowModelManager;
import com.model.init.dict.MetadataManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;

@Configuration
public class FlowBasicAutoConfig {

    @Bean("flowModelManager")
    @DependsOn({"contextUtil"})
    public FlowModelManager flowModelManager(){
        FlowModelManager flowModelManager = new FlowModelManager();
        flowModelManager.loadAllFlow();
        MetadataManager.getInstance();
        return flowModelManager;
    }
}
