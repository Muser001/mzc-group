package com.model.flow.model;

import com.model.flow.model.properties.FlowResourcesClasspathProperties;
import com.model.flownode.FlowTransactionConf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component
public class FlowModelAdaptor implements FlowModelAdaptorInterface{

    public static final String DEFAULT_FLOW_RESOURCES_CLASSPATH = "classpath*:flow/**/*.flow";

    @Autowired
    @Qualifier("customExecutorService")
    private ExecutorService executorService;

    private LoadFuture result;

    @Autowired
    private FlowResourcesClasspathProperties flowResourcesClasspathProperties;

    @Override
    public Map<String, FlowTransactionConf> getFlowModelMap(boolean isSkip) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Map<String, FlowTransactionConf> flowModelCache = new ConcurrentHashMap<>();

        try {
            List<Future<LoadFuture>> futures = new ArrayList();
            Resource[] resources = resolver.getResources(this.flowResourcesClasspathProperties.getPath());
            Resource[] var6 = resources;
            int var7 = resources.length;

            for (int var8 = 0; var8 < var7; ++var8) {
                Resource r = var6[var8];
                log.debug("loading file {}", r.getFilename());
                Future<LoadFuture> future = this.executorService.submit(new FlowFileLoadJob(flowModelCache, r));
                futures.add(future);
            }

            Iterator var16 = futures.iterator();

            while (var16.hasNext()) {
                Future<LoadFuture> future = (Future)var16.next();
                this.result = (LoadFuture)future.get();
                if (!this.result.isOk()) {
                    if (!isSkip) {
                        throw this.result.getException();
                    }
                    log.error(this.result.getFlowFileName() + "流程文件加载失败，已被忽略", this.result.getException());
                }
            }
            log.debug("{} flow file loaded", flowModelCache.size());
        } catch (Exception e) {
            log.error(this.result.getFlowFileName() + "流程文件加载失败", e);
            if (!isSkip) {
                throw new RuntimeException("流程文件加载失败");
            }
        }finally {
            this.executorService.shutdown();
        }

        return flowModelCache;
    }
}
