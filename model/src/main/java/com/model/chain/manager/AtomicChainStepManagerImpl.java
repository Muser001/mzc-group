package com.model.chain.manager;

import com.model.annotation.AtomicChainStep;
import com.model.annotation.ComposeChainStep;
import com.model.chain.servicechain.ServiceChainStep;
import com.model.chain.stepcomparator.AtomicChainStepComparator;
import com.model.chain.stepcomparator.ComposeChainStepComparator;
import com.model.enumtype.EngineBaseEnumType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class AtomicChainStepManagerImpl extends AbstractChainStapManager implements
        IChainStepManager, InitializingBean, ApplicationContextAware {

    protected ApplicationContext applicationContext;
    @Override
    protected List<String> getChainTags(Object chainStep) {
        AtomicChainStep atomicChainStep =
                AnnotationUtils.findAnnotation(chainStep.getClass(),AtomicChainStep.class);
        EngineBaseEnumType.BizExeDirectEnum[] bizRegDirect = atomicChainStep.bizRegDirect();
        EngineBaseEnumType.DeployModeEnum[] deployMode = atomicChainStep.deployMode();
        List<String> tags = new ArrayList<>();
        for(int i = 0; i < bizRegDirect.length; i++){
            for(int j = 0; j < deployMode.length; j++){
                String tag = new StringBuilder().append(bizRegDirect[i].getValue())
                        .append("_").append(deployMode[j].getValue()).toString();
                tags.add(tag);
            }
        }
        return tags;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,Object> atomicChainStepMap =
                applicationContext.getBeansWithAnnotation(AtomicChainStep.class);
        chainMap = tfrStep2TaggedChain(atomicChainStepMap);
        for(Map.Entry<String,List<ServiceChainStep>> mapEntry : chainMap.entrySet()){
            List<ServiceChainStep> chain = mapEntry.getValue();
            chain.sort(new AtomicChainStepComparator());
            StringBuilder stepStr = new StringBuilder();
            chain.forEach(item -> stepStr.append(item.getClass().getSimpleName()).append("; "));
            if(log.isDebugEnabled()){
                log.debug("联机引擎启动组装Compose责任链，tag: " + mapEntry.getKey() + " ," + "链条步骤 :\n" + stepStr);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
