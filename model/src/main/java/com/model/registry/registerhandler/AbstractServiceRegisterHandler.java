package com.model.registry.registerhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractServiceRegisterHandler {

    public Map<String,Class<?>> doScan(String scanPackage){
        Map<String,Class<?>> classes = new HashMap<>();
        try {
            ResourcePatternResolver resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(null);
            String[] scanAtomicPackageArray = scanPackage.split(",");
            for(String sacnAtomicPackage : scanAtomicPackageArray){
                String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(sacnAtomicPackage.trim()) + '/' + "*.class";
                Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
                for (Resource resource : resources){
                    if(resource.isReadable()){
                        MetadataReader metadataReader = new CachingMetadataReaderFactory(
                                resourcePatternResolver.getClassLoader()).getMetadataReader(resource);
                        try {

                            Class<?> aClass = resourcePatternResolver.getClassLoader().
                                    loadClass(metadataReader.getClassMetadata().getClassName());
                            filter(classes,aClass);
                        } catch (ClassNotFoundException e) {
                            log.warn("Classs Load failed",e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classes;
    }

    public abstract void filter(Map<String,Class<?>> classes, Class<?> clazz);

    /**
     * 判断当前class是否是代理
     */
    protected boolean isProxyClass(Class<?> clazz){
        if(clazz == null){
            return false;
        }
        if(Proxy.isProxyClass(clazz) || clazz.getName().contains("$$EnhancerBy")){
            return true;
        }
        return false;
    }

}
