package com.model.transaction;

import io.netty.util.concurrent.FastThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.ResourceTransactionManager;

@Component
@Slf4j
public class LocalTransactionComponent {

    /**
     * 当前线程的本地事务状态
     */
    private static FastThreadLocal<TransactionStatus> currentTransactionHolder = new FastThreadLocal<>();

    @Autowired
    @Qualifier("transactionManager")
    protected ResourceTransactionManager txManager;

    /**
     * 提交当前事务
     */
    public void commit(){
        TransactionStatus txStatus = currentTransactionHolder.get();
        if(txStatus == null){
            return;
        }

        if(txStatus.isCompleted()){
            cleanThreadLocal();
            throw new RuntimeException("本地事务错误");
        }

        try {
            txManager.commit(txStatus);
        } finally {
            cleanThreadLocal();
        }
    }

    /**
     * 开启新的事务
     */

    public void openTransaction() {
        /** 确保上一个事务被清理，先检查上一事务是否完成状态 */
        TransactionStatus txStatus = currentTransactionHolder.get();
        if(txStatus != null && !txStatus.isCompleted()){
            throw new RuntimeException("开启新事务时上一个事务未关闭");
        }

        /** 清理当前线程中的上一个事务 */
        cleanThreadLocal();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        int txnTimeOut = 5000 / 1000;
        /** 如果外部的超时时间小于1秒，则初始化为1秒 */
        if (txnTimeOut < 1) {
            txnTimeOut = 1;
        }

        def.setTimeout(txnTimeOut);

        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        currentTransactionHolder.set(txManager.getTransaction(def));
        if(log.isDebugEnabled()){
            log.warn("开启数据库本地事务，超时时间（单位秒）：{}",txnTimeOut);
        }
    }

    /**
     * 本地事务回滚
     */
    public void rollback() {
        TransactionStatus txStatus = currentTransactionHolder.get();
        if(txStatus == null){
            return;
        }

        if(txStatus.isCompleted()){
            cleanThreadLocal();
            throw new RuntimeException("本地事务错误");
        }

        try {
            txManager.rollback(txStatus);
        } catch (Exception e) {
            log.debug("回滚数据库本地事务失败");
            throw new RuntimeException(e);
        } finally {
            cleanThreadLocal();
        }
    }

    /**
     * 清理上下文中事务 本地和远程会多次清理导致空指针
     */
    public void cleanThreadLocal() {
        currentTransactionHolder.remove();
    }

    /**
     * 判断当前是否存在事务
     */
    public boolean hasLocalTransaction() {

        if(currentTransactionHolder.get() == null){
            return false;
        }

        return true;
    }

}
