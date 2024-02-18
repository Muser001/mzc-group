package com.model.init.dict;

import com.model.process.properties.CommonServiceProties;
import com.model.util.ContextUtil;
import com.model.util.metadata.Field;
import com.model.util.metadata.JaxUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

/**
 * 元数据字典xml加载管理类，使用说明：单例模式，启动时加载resources/dict下所有dict.xml后缀文件
 */
@Slf4j
public class MetadataManager {

    private CommonServiceProties proties = ContextUtil.getBean(CommonServiceProties.class);

    /**
     * key=字典id,value=字段属性,缓存到内存
     */
    private static HashMap<String, Field> cache = new HashMap<>();
    private static HashMap<String,String> fileMap = new HashMap<>();

    /**
     * xml加载路径，只能是.dict.xml结尾的xml文件
     */
    private static String DEFAULT_DICT_RESOURCES_CLASSPATH = "classpath*:dict/**/*.dict.xml";

    private static MetadataManager instance = new MetadataManager();

    private MetadataManager() {
        init();
    }

    public static MetadataManager getInstance() {
        return instance;
    }
    public Field getField(String key) {
        return (Field) cache.get(key);
    }

    private void init() {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        boolean loadFailureFlag = false;
        try {
            Resource[] resources = resolver.getResources(DEFAULT_DICT_RESOURCES_CLASSPATH);
            for (Resource r : resources) {
                if (log.isDebugEnabled()) {
                    log.debug("加载元数据配置文件: {}",r.getURI().getPath());
                }
                Dict dict = JaxUtils.readXML(r.getInputStream());
                for (Field f : dict.getFields()) {
                    String key = f.getId();
                    if(cache.containsKey(key)) {
                        loadFailureFlag = true;
                        log.warn("当前加载的数据字典{}已加载的数据字典{}中的数据字典项[{}]重复",r.getFilename(), fileMap.get(key) ,key);
                    }else {
                        cache.put(key, f);
                        fileMap.put(key, r.getFilename());
                    }
                }
            }
            if(loadFailureFlag){
                throw new RuntimeException("数据字典项重复，加载失败");
            }
        } catch (IOException | JAXBException e) {
            throw new RuntimeException(DEFAULT_DICT_RESOURCES_CLASSPATH + "路径加载文件错误",e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("圆数据配置解析失败", e);
        } catch (SAXException e) {
            throw new RuntimeException("元数据加载SAX错误", e);
        }

    }
}
