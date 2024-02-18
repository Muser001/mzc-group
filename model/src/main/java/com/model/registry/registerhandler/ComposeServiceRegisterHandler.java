package com.model.registry.registerhandler;

import com.model.annotation.PCS;
import com.model.chain.properties.ComposeServiceProperties;
import com.model.enumtype.OperateType;
import com.model.flow.model.FlowModelManager;
import com.model.flownode.FlowTransactionConf;
import com.model.registry.MdpConfig;
import com.model.registry.entity.ComposeServiceRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ComposeServiceRegisterHandler extends AbstractServiceRegisterHandler implements IComposeServiceRegisterHandler{

    private Map<String, ComposeServiceRegister> serviceRegisterMap = new HashMap<>();

    private Map<String, Class> serviceClazzMap = new HashMap<>();

    public ComposeServiceRegisterHandler(ComposeServiceProperties composeServiceProperties, MdpConfig mdpConfig,
                                         FlowModelManager flowModelManager, Environment environment) {
        registComposeService(composeServiceProperties, mdpConfig, flowModelManager);
    }

    @Override
    public void filter(Map<String, Class<?>> classes, Class<?> clazz) {
        if(isProxyClass(clazz)){
            Class<?>[] interfaces = clazz.getSuperclass().getInterfaces();
            if(interfaces != null){
                for(Class<?> intfClazz : interfaces) {
                    PCS serviceName = AnnotationUtils.findAnnotation(intfClazz, PCS.class);
                    classes.put(serviceName.id(), clazz);
                }
            }
            else{
                if(clazz.isAnnotationPresent(PCS.class) && clazz.isInterface()){
                    PCS serviceName = clazz.getAnnotation(PCS.class);
                    classes.put(serviceName.id(), clazz);
                }
            }
        }
    }

    @Override
    public Map<String, ComposeServiceRegister> getServiceRegisterMap() {
        return null;
    }

    @Override
    public ComposeServiceRegister getServiceRegister(String serviceId) {
        return serviceRegisterMap.get(serviceId);
    }

    @Override
    public Class getServiceClazz(String serviceId) {
        return IComposeServiceRegisterHandler.super.getServiceClazz(serviceId);
    }

    /**
     * 加载PCS注解
     * @param composeProperties 基础配置
     * @param mdpConfig mdp配置
     * @param flowManager 服务模型
     * @param environment 环境
     */
    private void loadPcs(ComposeServiceProperties composeProperties, MdpConfig mdpConfig,
                         FlowModelManager flowManager, Environment environment) {
//        Set<Class<?>> pcsSet = doScan(composeProperties.getScanComposeServicePakage());

    }

    private void registComposeService(ComposeServiceProperties composeServiceProperties, MdpConfig mdpConfig,
                                      FlowModelManager flowModelManager) {
        Map<String, FlowTransactionConf> flowModelCache = flowModelManager.getFlowModelCache();
        if (flowModelCache == null || flowModelCache.isEmpty()) {
            log.warn("加载到的流程编排数为，组合服务的注册模型组装不执行，请检查是否存在编排文件");
        }
        log.info("=================扫描流程编排文件初始化组合服务的注册模型开始=================");
        long start = System.currentTimeMillis();
        for (String composeCode : flowModelCache.keySet()) {
            if (serviceRegisterMap.containsKey(composeCode)) {
                throw new IllegalStateException("存在重复的组合服务编码" + composeCode);
            }
            FlowTransactionConf flowTransactionConf = flowModelCache.get(composeCode);
            ComposeServiceRegister serviceRegister = new ComposeServiceRegister();
            serviceRegister.setComposeCode(composeCode);
            serviceRegister.setComposeName(flowTransactionConf.getLongname());
            String oprAttStr = flowTransactionConf.getFlowProps().getOprAtt();
            if (!StringUtils.isEmpty(oprAttStr)) {
                serviceRegister.setOperateType(Integer.valueOf(oprAttStr));
            }else {
                serviceRegister.setOperateType(OperateType.ACCOUNTS.getValue());
            }

            serviceRegister.setInnInfDto(flowTransactionConf.getInput());
            serviceRegister.setOutInfDto(flowTransactionConf.getOutput());
            serviceRegister.setOutTxStep(flowTransactionConf.getFlowProps().getOutTxStep());
            serviceRegister.setInTxStep(flowTransactionConf.getFlowProps().getInTxStep());
            // TODO: 2024/1/2 赋值没想好

            // 从配置中心获取其他属性
//            initServiceRegister(serviceRegister, composeServiceProperties, mdpConfig);
            // 组装服务定义信息
            String composeDefInfo = new StringBuilder().append(serviceRegister.getOperateType())
                    .append("|")
                    .append(serviceRegister.getTransProperty())
                    .append("|")
                    .append(serviceRegister.isCheckFlag()).toString();
            serviceRegister.setComposeDefInfo(composeDefInfo);
            serviceRegisterMap.put(composeCode, serviceRegister);
            log.info("PCS[{}]服务注册模型加载完成，模型信息:{}",composeCode, serviceRegister);
        }
        log.info("扫描流程编排文件初始化组合服务的注册模型结束，模型数量[{}],耗时[{}]ms",
                serviceRegisterMap.size(),(System.currentTimeMillis() - start));
    }
}
