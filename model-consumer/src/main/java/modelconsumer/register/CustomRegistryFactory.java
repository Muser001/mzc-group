package modelconsumer.register;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.registry.Registry;
import org.apache.dubbo.registry.RegistryFactory;
import org.apache.dubbo.registry.support.AbstractRegistryFactory;
import org.springframework.stereotype.Component;


public class CustomRegistryFactory implements RegistryFactory {


    @Override
    public Registry getRegistry(URL url) {
        // 你可以根据 URL 中的参数来决定创建哪种 Registry 实例
        return new CustomRegistry(url);
    }
}
