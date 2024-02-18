package com.model.chain.manager;

import com.model.chain.servicechain.ServiceChainStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractChainStapManager {

    protected Map<String, List<ServiceChainStep>> chainMap;

    /**
     * 遍历责任链步骤的集合，获取tag及其对应的责任链步骤的映射关系
     * @param chainStepMap 责任链步骤集合
     * @return tag及其对应的责任链步骤的映射关系集合
     */
    protected Map<String,List<ServiceChainStep>> tfrStep2TaggedChain(Map<String,Object> chainStepMap){
        Map<String,List<ServiceChainStep>> map = new HashMap<>();
        for(Object chainStep : chainStepMap.values()){
            addStep2TaggedChain(map,chainStep);
        }
        return map;
    }

    /**
     * 把责任链放入相应tag的列表中
     * @param chainMap tag及其对应的责任链映射关系，key为tag，value为其对应的责任链步骤列表
     * @param chainStep 责任链步骤
     */
    private void addStep2TaggedChain(Map<String,List<ServiceChainStep>> chainMap, Object chainStep){
        List<String> tags = getChainTags(chainStep);
        for(String tag : tags){
            List<ServiceChainStep>  chainSteps = chainMap.get(tag);
            if(null == chainSteps){
                chainSteps = new ArrayList<>();
                chainMap.put(tag,chainSteps);
            }
            chainSteps.add((ServiceChainStep) chainStep);
        }
    }

    /**
     * 获取指定责任链步骤的tag集合
     * @param chainStep 指定责任链步骤
     * @return tag集合
     */
    protected abstract List<String> getChainTags(Object chainStep);

    /**
     * 返回指定tag类型的Step链
     * @param tag 指定tag
     * @return 属于该tag下的所有责任链步骤集合
     */
    public List<ServiceChainStep> getChainByTag(String tag){
        return chainMap.get(tag);
    }
}
