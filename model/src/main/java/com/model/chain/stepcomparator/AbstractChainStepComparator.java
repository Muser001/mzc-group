package com.model.chain.stepcomparator;

import com.model.chain.servicechain.ServiceChainStep;

public abstract class AbstractChainStepComparator {

    public int compare(ServiceChainStep o1, ServiceChainStep o2){
        return doCompare(o1,o2);
    }

    private int doCompare(ServiceChainStep o1, ServiceChainStep o2){
        int i1 = getOrder(o1.getClass());
        int i2 = getOrder(o2.getClass());
        return Integer.compare(i1,i2);
    }

    protected abstract Integer getOrder(Class<? extends ServiceChainStep> aClass);

}
