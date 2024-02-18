package com.model.chain.stepcomparator;

import com.model.annotation.ComposeChainStep;
import com.model.chain.servicechain.ServiceChainStep;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Comparator;

public class ComposeChainStepComparator extends
        AbstractChainStepComparator implements Comparator<ServiceChainStep> {

    @Override
    protected Integer getOrder(Class<? extends ServiceChainStep> aClass) {
        ComposeChainStep annotation = AnnotationUtils.findAnnotation(aClass,ComposeChainStep.class);
        return annotation.order();
    }
}
