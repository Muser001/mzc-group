package com.model.chain.manager;

import com.model.annotation.ComposeChainStep;
import com.model.chain.servicechain.ServiceChainStep;
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
public class ComposeChainStepManagerImpl extends AbstractChainStapManager implements
        IChainStepManager, InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 初始化Bean的时候执行，获取所有的组合服务tag与责任链对应关系集合，并按优先级排序
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,Object> composeChainStepMap =
                applicationContext.getBeansWithAnnotation(ComposeChainStep.class);
        chainMap = tfrStep2TaggedChain(composeChainStepMap);
        for(Map.Entry<String,List<ServiceChainStep>> mapEntry : chainMap.entrySet()){
            List<ServiceChainStep> chain = mapEntry.getValue();
            chain.sort(new ComposeChainStepComparator());
            StringBuilder stepStr = new StringBuilder();
            chain.forEach(item -> stepStr.append(item.getClass().getSimpleName()).append("; "));
            if(log.isDebugEnabled()){
                log.debug("联机引擎启动组装Compose责任链，tag: " + mapEntry.getKey() + " ," + "链条步骤 :\n" + stepStr);
            }
        }
    }

    @Override
    protected List<String> getChainTags(Object chainStep) {
        ComposeChainStep composeChainStep =
                AnnotationUtils.findAnnotation(chainStep.getClass(),ComposeChainStep.class);
        EngineBaseEnumType.ServTypeCodeType[] servTypeCodes = composeChainStep.serviceTypeCode();
        EngineBaseEnumType.DeployModeEnum[] deployMode = composeChainStep.deployMode();
        List<String> tags = new ArrayList<>();
        for(int i = 0; i < servTypeCodes.length; i++){
            for(int j = 0; j < deployMode.length; j++){
                String tag = new StringBuilder().append(servTypeCodes[i].getValue())
                        .append("_").append(deployMode[j].getValue()).toString();
                tags.add(tag);
            }
        }
        return tags;
    }




}
