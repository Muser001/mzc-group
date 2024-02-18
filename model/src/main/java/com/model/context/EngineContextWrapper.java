package com.model.context;

import com.model.dao.po.SeComposeRunLogPo;
import com.model.registry.entity.AtomicServiceRegister;
import com.model.registry.entity.ComposeServiceRegister;
import com.model.registry.registerhandler.IComposeServiceRegisterHandler;
import com.model.route.ServiceRoute;
import com.model.util.ContextUtil;

import javax.xml.ws.Service;
import java.util.concurrent.atomic.AtomicInteger;

public class EngineContextWrapper {

    /**
     * pbs执行顺序，流程的服务节点执行序号
     */
    private static final String SE_STEP_SEQ_ATOMIC_INTEGER = "stepSeqAtomicInteger";

    /**
     * pbs执行顺序，流程的服务节点执行序号
     */
    private static final String SE_VALID_STEP_SEQ_ATOMIC_INTEGER = "validStepSeqAtomicInteger";

    /**
     * pcs服务注册信息
     */
    private static final String SE_COMPOSE_SERVICE_REGISTER = "seComposeServiceRegister";

    public static ComposeServiceRegister getComposeServiceRegister(){
        Object obj = ServiceContextHandler.getPrivateContextInfo(SE_COMPOSE_SERVICE_REGISTER);
        if (null == obj) {
            return null;
        }
        if (!(obj instanceof ComposeServiceRegister)) {
            return ContextUtil.getBean(IComposeServiceRegisterHandler.class)
                    .getServiceRegister(ServiceContextHandler.getServNo());
        }
        return (ComposeServiceRegister) obj;
    }

    /**
     * 获取执行PBS服务顺序
     * @return 执行PBS服务顺序
     */
    public static AtomicInteger getStepSeqAtomicInteger() {
        return (AtomicInteger) ServiceContextHandler.getPrivateContextInfo(SE_STEP_SEQ_ATOMIC_INTEGER);
    }

    /**
     * 统计执行PBS服务顺序
     * @param stepSeq 调用链
     */
    public static void setStepSeqAtomicInteger(AtomicInteger stepSeq) {
        ServiceContextHandler.putPrivateContextInfo(SE_STEP_SEQ_ATOMIC_INTEGER, stepSeq);
    }
    /**
     * 获取执行PBS服务顺序
     * @return 执行PBS服务顺序
     */
    public static AtomicInteger getValidStepSeqAtomicInteger() {
        return (AtomicInteger) ServiceContextHandler.getPrivateContextInfo(SE_VALID_STEP_SEQ_ATOMIC_INTEGER);
    }
    /**
     * 统计执行PBS服务顺序
     * @param stepSeq 调用链
     */
    public static void setValidStepSeqAtomicInteger(AtomicInteger stepSeq) {
        ServiceContextHandler.putPrivateContextInfo(SE_VALID_STEP_SEQ_ATOMIC_INTEGER, stepSeq);
    }

    /**
     * 获取私有域超时时间
     * @return
     */
    public static int getTimeout() {
        Object o = ServiceContextHandler.getPrivateContextInfo("seTimeout");
        return Integer.parseInt(o.toString());
    }

    public static long getStartTime() {
        Object o = ServiceContextHandler.getPrivateContextInfo("seStartTime");
        return Long.parseLong(o.toString());
    }

    /**
     * 获取上下文中执行的交易码，如果为空则获取报文头中的交易码
     * @return 真正执行的交易码
     */
    public static String getProcessServNo() {
        Object obj = ServiceContextHandler.getPrivateContextInfo("processServNo");
        if (obj == null) {
            return ServiceContextHandler.getServNo();
        }
        return (String) obj;
    }

    /**
     * 上下文中设置动态并发节点下的线程序号key
     * @param indexLocal 动态并发节点下的线程序号key
     */
    public static void setIndexLocal(int indexLocal) {
        ServiceContextHandler.putPrivateContextInfo("INDEX_LOCAL", indexLocal);
    }

    /**
     * 上下文中获取动态并发节点下的线程序号key
     * @return 动态并发节点下的线程序号key
     */
    public static Integer getIndexLocal() {
        return (Integer) ServiceContextHandler.getPrivateContextInfo("INDEX_LOCAL");
    }

    /**
     * 从上下文私有域获取注册信息
     * @return
     */
    public static AtomicServiceRegister getAtomicServiceRegister() {
        Object obj = ServiceContextHandler.getPrivateContextInfo("seAtomicServiceRegister");
        if (null == obj) {
            return null;
        }
        return (AtomicServiceRegister)obj;
    }

    /**
     * 将PBS注册信息放入私有域
     * @param atomicServiceRegister
     */
    public static void setAtomicServiceRegister(AtomicServiceRegister atomicServiceRegister) {
        ServiceContextHandler.putPrivateContextInfo("seAtomicServiceRegister", atomicServiceRegister);
    }
    /**
     * 把当前组合服务的服务注册信息放入上下文私有域
     * @param composeServiceRegister
     */
    public static void setComposeServiceRegister(ComposeServiceRegister composeServiceRegister) {
        ServiceContextHandler.putPrivateContextInfo(
                SE_COMPOSE_SERVICE_REGISTER, composeServiceRegister
        );
    }

    public static void setSeComposeRunLogPo(SeComposeRunLogPo seComposeRunLogPo) {
        ServiceContextHandler.putPrivateContextInfo("seComposeRunLogPo",seComposeRunLogPo);
    }
    /**
     * 流水登记PO
     * @return
     */
    public static SeComposeRunLogPo getSeComposeRunLogPo() {
        Object seComposeRunLogPo = ServiceContextHandler.getPrivateContextInfo("seComposeRunLogPo");
        if (null == seComposeRunLogPo) {
            return null;
        }
        return (SeComposeRunLogPo)seComposeRunLogPo;
    }
}

