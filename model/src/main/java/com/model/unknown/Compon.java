package com.model.unknown;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Compon implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
//        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }
        System.out.println(applicationContext.getBean("restServiceImpl"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
