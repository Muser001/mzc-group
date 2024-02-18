package com.model.flow;

import com.model.mapper.FlowNodeMapper;
import com.model.mapper.FlowOutputMapper;
import com.model.mapper.ServiceNodeMapper;
import org.checkerframework.checker.formatter.qual.ReturnsFormat;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class FlowMapperManager implements ApplicationContextAware {

    private ApplicationContext context;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public FlowOutputMapper getFlowMapper(String mapperId) {
        return (FlowOutputMapper) context.getBean(mapperId);
    }
    public ServiceNodeMapper getServiceMapper(String mapperId) {
        return (ServiceNodeMapper) context.getBean(mapperId);
    }

    public <T> T getBean(String beanId, Class<T> requiredType) {
        return context.getBean(beanId, requiredType);
    }

    public Object getBean(String beanId) {
        return context.getBean(beanId);
    }
    public FlowNodeMapper getBeanMapper(String mapperId) {
        return (FlowNodeMapper) context.getBean(mapperId);
    }
}
