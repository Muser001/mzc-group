package modelconsumer.register;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.NotifyListener;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.support.AbstractRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class CustomRegistry implements Registry {

    // Nacos注册中心地址
    private static final String NACOS_ADDRESS = "localhost:8848";

    private URL url;
    private NamingService namingService;

    public CustomRegistry(URL url) {
        this.url = url;
        try {
            this.namingService = NamingFactory.createNamingService(NACOS_ADDRESS);
        } catch (NacosException e) {
            throw new RuntimeException("Failed to create NamingService", e);
        }
    }

    @Override
    public void register(URL url) {
        try {
            // 获取服务接口名称
            String serviceName = url.getServiceInterface();
            // 将URL转换为Nacos Instance对象
            Instance instance = new Instance();
            instance.setIp(url.getHost());
            instance.setPort(url.getPort());
            // 注册服务
            namingService.registerInstance(serviceName, instance);
        } catch (NacosException e) {
            throw new RuntimeException("Failed to register service: " + url.getServiceInterface(), e);
        }
    }

    @Override
    public void unregister(URL url) {
        try {
            // 获取服务接口名称
            String serviceName = url.getServiceInterface();
            // 将URL转换为Nacos Instance对象
            Instance instance = new Instance();
            instance.setIp(url.getHost());
            instance.setPort(url.getPort());
            // 注销服务
            namingService.deregisterInstance(serviceName, instance);
        } catch (NacosException e) {
            throw new RuntimeException("Failed to unregister service: " + url.getServiceInterface(), e);
        }
    }

    @Override
    public void subscribe(URL url, NotifyListener listener) {
        try {
            // 获取服务接口名称
            String serviceName = url.getServiceInterface();
            // 订阅服务
            namingService.subscribe(serviceName, event -> {
                // 处理服务变更事件
                List<URL> urls = new ArrayList<>();
                if (event instanceof NamingEvent) {
                    NamingEvent namingEvent = (NamingEvent) event;
                    List<Instance> instances = namingEvent.getInstances();
                    for (Instance instance : instances) {
                        URL u = new URL(url.getProtocol(), instance.getIp(), instance.getPort(), url.getPath());
                        urls.add(u);
                    }
                }
                // 调用监听器的notify方法通知订阅者
                listener.notify(urls);
            });
        } catch (NacosException e) {
            throw new RuntimeException("Failed to subscribe service: " + url.getServiceInterface(), e);
        }
    }

    @Override
    public void unsubscribe(URL url, NotifyListener listener) {
        // 不支持取消订阅
    }

    @Override
    public List<URL> lookup(URL url) {
        try {
            // 获取服务接口名称
            String serviceName = url.getServiceInterface();
            // 查找服务
            List<Instance> instances = namingService.getAllInstances(serviceName);
            List<URL> urls = new ArrayList<>();
            for (Instance instance : instances) {
                URL u = new URL(url.getProtocol(), instance.getIp(), instance.getPort(), url.getPath());
                urls.add(u);
            }
            return urls;
        } catch (NacosException e) {
            throw new RuntimeException("Failed to lookup service: " + url.getServiceInterface(), e);
        }
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void destroy() {
        // 释放资源
        if (namingService != null) {
            try {
                namingService.shutDown();
            } catch (NacosException e) {
                throw new RuntimeException("Failed to shutdown NamingService", e);
            }
        }
    }
}
