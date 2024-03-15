package com.model.chain.stepcomparator;

import com.model.annotation.AtomicChainStep;
import com.model.chain.servicechain.ServiceChainStep;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Comparator;

public class AtomicChainStepComparator extends
        AbstractChainStepComparator implements Comparator<ServiceChainStep> {
    @Override
    protected Integer getOrder(Class<? extends ServiceChainStep> aClass) {
        AtomicChainStep annotation = AnnotationUtils.findAnnotation(aClass,AtomicChainStep.class);
        return annotation.order();
    }
}
