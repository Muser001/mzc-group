package com.model.registry.registerhandler;

import com.model.annotation.PBS;
import com.model.enumtype.DlockFlagEnum;
import com.model.enumtype.OperateType;
import com.model.registry.MdpConfig;
import com.model.registry.entity.AtomicServiceRegister;
import com.model.registry.properties.AtomicServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Slf4j
public class AtomicServiceRegisterHandler extends AbstractServiceRegisterHandler implements IAtomicServiceRegisterHandler {

    private Map<String,AtomicServiceRegister> serviceRegisterMap = new HashMap<>();



    public AtomicServiceRegisterHandler(AtomicServiceProperties atomicServiceProperties, MdpConfig mdpConfig){
        registAtomicServiceByIntf(atomicServiceProperties,mdpConfig);
    }

    private void registAtomicServiceByIntf(AtomicServiceProperties atomicServiceProperties,MdpConfig mdpConfig){
        Map<String,Class<?>> atomicServiceMap = doScan(atomicServiceProperties.getScanAtomicInterfacePackage());
        if(atomicServiceMap == null || atomicServiceMap.isEmpty()){
            log.warn("根据atomic基础服务的接口构建注册模型，加载到的@PBS数为0");
            return;
        }

        log.info("=================初始化基础服务的注册模型开始=================");
        long start = System.currentTimeMillis();

        for(Map.Entry<String,Class<?>> entry : atomicServiceMap.entrySet()){
            Class<?> pbsClass = entry.getValue();
            if(IAtomicService.class.isAssignableFrom(pbsClass)){
                PBS pbsService =  pbsClass.getAnnotation(PBS.class);
                if(pbsService == null){
                    throw new RuntimeException("当前PBS服务接口[" + pbsClass.getSimpleName() + "]没有标注@PBS注解，请检查");
                }
                buildAtomicRegister(pbsService,mdpConfig,pbsClass,atomicServiceProperties);
            }else{
                throw new RuntimeException("当前PBS服务接口[" + pbsClass.getSimpleName() + "]不是继承IAtomicService同一接口，请检查");
            }
        }
        log.info("=================初始化基础服务的注册模型结束,模型数量[{}],耗时[{}]ms=================",
                serviceRegisterMap.size(),(System.currentTimeMillis() - start));
    }

    public void filter(Map<String,Class<?>> classes, Class<?> aClass){
        if(isProxyClass(aClass)) {
            Class<?>[] interfaces = aClass.getSuperclass().getInterfaces();
            if (interfaces != null) {
                for (Class<?> intfClazz : interfaces) {
                    PBS serviceName = AnnotationUtils.findAnnotation(intfClazz, PBS.class);
                    classes.put(serviceName.id(), aClass);
                }
            }
        }
            else{
                if(aClass.isAnnotationPresent(PBS.class) && aClass.isInterface()){
                    PBS serviceName = aClass.getAnnotation(PBS.class);
                    classes.put(serviceName.id(), aClass);
                }

        }
    }

    /**
     * 构建单个基础服务的注册模型
     */

    private void buildAtomicRegister(PBS pbsService, MdpConfig mdpConfig, Class<?> pbsClass, AtomicServiceProperties atomicServiceProperties){
        Type[] intfTypes = pbsClass.getGenericInterfaces();
        Type[] paramTypes = ((ParameterizedType) intfTypes[0]).getActualTypeArguments();
        String atomicCode = pbsService.id();
        if(log.isDebugEnabled()){
            log.debug("开始构建基础服务[{}]的模型定义信息", atomicCode);
        }
        AtomicServiceRegister serviceRegister = new AtomicServiceRegister();
        serviceRegister.setServiceId(atomicCode);
        serviceRegister.setServiceName(pbsService.name());
        serviceRegister.setParamType((Class<?>) paramTypes[0]);
        serviceRegister.setResultType((Class<?>) paramTypes[1]);
        serviceRegister.setServiceClass(pbsClass);

        if(pbsService.type() != null){
            serviceRegister.setServiceType(pbsService.type());
        }
        if(pbsService.oprAtt() != null){
            serviceRegister.setOperateType(pbsService.oprAtt());
        }
        serviceRegister.setRegistRunLog(true);
        //查询类PBS默认不记RunLog
        if(OperateType.QUERY == pbsService.oprAtt()){
            serviceRegister.setRegistRunLog(false);
        }

        /**从配置中心获取基础服务动态配置数据 */
        String dynLockFlg = DlockFlagEnum.N.getValue();

        /** 加锁标志 */
        serviceRegister.setDynLockFlg(dynLockFlg);
        /** 判断加锁配置和服务类型配置是否冲突 */
        if((serviceRegister.getOperateType() == OperateType.QUERY) &&
            !DlockFlagEnum.N.getValue().equals(dynLockFlg)){
            throw new RuntimeException("加锁配置和服务类型配置冲突");
        }
        serviceRegisterMap.put(atomicCode,serviceRegister);
        log.info("PBS[{}]服务注册模型加载完成，模型信息:{}",atomicCode,serviceRegister);
    }


    @Override
    public Map<String, AtomicServiceRegister> getServiceRegisterMap() {
        return this.serviceRegisterMap;
    }

    @Override
    public AtomicServiceRegister getServiceRegister(String serviceId) {
        return (AtomicServiceRegister)this.serviceRegisterMap.get(serviceId);
    }
}
