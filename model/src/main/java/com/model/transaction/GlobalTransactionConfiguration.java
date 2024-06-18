package com.model.transaction;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Configuration
@EnableAspectJAutoProxy
public class GlobalTransactionConfiguration {

    @Bean("txadvice")
    public TransactionInterceptor txadvice(TransactionManager transactionManager) {

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute readOblyTx = new RuleBasedTransactionAttribute();
        readOblyTx.setPropagationBehavior(4);
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setIsolationLevel(2);
        requiredTx.setPropagationBehavior(0);
        requiredTx.setTimeout(999999);
        RuleBasedTransactionAttribute requiresNewTx = new RuleBasedTransactionAttribute();
        requiresNewTx.setIsolationLevel(2);
        requiresNewTx.setPropagationBehavior(3);
        requiresNewTx.setTimeout(999999);
        RuleBasedTransactionAttribute supportTx = new RuleBasedTransactionAttribute();
        requiresNewTx.setPropagationBehavior(1);
        requiresNewTx.setTimeout(999999);
        RuleBasedTransactionAttribute nestedTx = new RuleBasedTransactionAttribute();
        requiresNewTx.setIsolationLevel(2);
        requiresNewTx.setPropagationBehavior(6);
        requiresNewTx.setTimeout(999999);

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("doTx*", requiredTx);
        txMap.put("doNoTx*", readOblyTx);
        txMap.put("doNewTx*", requiresNewTx);
        txMap.put("doNestedTx*", nestedTx);
        txMap.put("*", supportTx);
        source.setNameMap(txMap);
        TransactionInterceptor txadvice = new TransactionInterceptor(transactionManager, source) {
            private static final long serialVersionUID = -885337677008787067L;

            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object object = super.invoke(invocation);
                System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                log.info("事务切面");
                return object;
            }

//            protected TransactionAspectSupport.TransactionInfo prepareTransactionInfo(PlatformTransactionManager tm, TransactionAttribute txAttr, String joinpointIdentification, TransactionStatus status) {
//                return super.prepareTransactionInfo(tm, txAttr, joinpointIdentification, status);
//            }
        };
        return txadvice;
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(TransactionInterceptor txadvice) {
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        pointcutAdvisor.setAdvice(txadvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(this.generateAopPointCutExpression());
        pointcutAdvisor.setPointcut(pointcut);
        return pointcutAdvisor;
    }

    private String generateAopPointCutExpression() {
        StringBuilder sb = new StringBuilder("execution(public * com.model.*.*.*(..))");
        return sb.toString();
    }

}
