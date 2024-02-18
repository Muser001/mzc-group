package com.model.process.flow;

import com.google.common.collect.Maps;
import com.model.chain.properties.ComposeServiceProperties;
import com.model.context.EngineContextWrapper;
import com.model.context.ServiceContextHandler;
import com.model.custinfo.CustInfoBean;
import com.model.dao.po.SeComposeRunLogPo;
import com.model.dto.BaseInputDTO;
import com.model.enumtype.EngineBaseEnumType;
import com.model.enumtype.ServiceType;
import com.model.lock.DtxLockService;
import com.model.log.RunLogDService;
import com.model.message.ServiceCommonRequestMsg;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.message.header.ServiceRequestHeader;
import com.model.process.properties.CommonServiceProties;
import com.model.registry.entity.AtomicServiceRegister;
import com.model.registry.properties.AtomicServiceProperties;
import com.model.registry.registerhandler.IAtomicServiceRegisterHandler;
import com.model.route.ServiceRoute;
import com.model.util.EntityBeanCopier;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务调用执行器，一般在外部程序需要外部调用时会用到，比如handler.ServiceNodeHandler 服务调度执行器主要执行功能
 * 1.构建请求对象
 * 2.获取原子服务注册信息
 * 3.服务路由
 * 4.登记交易日志
 * 5.服务调用
 * 6.多错误码处理
 */
@Slf4j
public abstract class AbstractComposeServiceNode implements NodeApi{

    @Autowired(required = false)
    protected DtxLockService dtxLockService;

    @Autowired
    protected IAtomicServiceRegisterHandler atomicServiceRegisterHandler;

    @Autowired
    protected RunLogDService runLogDService;

    @Autowired
    protected ServiceRoute serviceRoute;

    @Autowired
    protected CommonServiceProties commonServiceProties;

    @Autowired
    protected ComposeServiceProperties composeServiceProperties;

    @Autowired
    protected AtomicServiceProperties atomicServiceProperties;

    /**
     * 从上下文构建PBS请求报文
     * @param stepSeq 步骤序号
     * @param atomicCode 原子编码
     * @param baseInputDTO 输入DTO
     * @param atomicServiceRegister 基础服务注册
     * @return
     */
    protected ServiceRequestMsg buildAtomicRequestMsg(
            Integer stepSeq, String atomicCode, BaseInputDTO baseInputDTO, AtomicServiceRegister atomicServiceRegister) {
        ServiceRequestMsg atomicRequestMsg = new ServiceRequestMsg();

        // 报文头部分
        ServiceRequestHeader atomicTxHeader = atomicRequestMsg.getTxHeader();
        EntityBeanCopier.copyProperties(ServiceContextHandler.getTxHeader(), atomicTxHeader);
        atomicTxHeader.setStepSeq(stepSeq);
        atomicTxHeader.setAtomicCode(atomicCode);
        // 动态扩展的报文头字段

        // 通用域相关信息

        // 核心内部引擎共享域部分信息
        ServiceCommonRequestMsg atomicTxCommon = new ServiceCommonRequestMsg();
        atomicTxCommon.setEngineInfo(Maps.newHashMap(ServiceContextHandler.getTxCommon().getEngineInfo()));


        // 报文业务部分
        atomicRequestMsg.getTxBody().setTxEntity(baseInputDTO);

        return atomicRequestMsg;
    }

    /**
     * 从报文头，报文体中获取指定分库要素值
     * @param header 请求报文
     * @param inputDTO 输入DTO
     * @param shardingKey 分库标识键
     * @return
     */
    private String findShardingValue(ServiceRequestHeader header, BaseInputDTO inputDTO, String shardingKey) {
        if (StringUtil.isNullOrEmpty(shardingKey)) {
            return null;
        }
        String getMethodName = "get" + Character.toUpperCase(shardingKey.charAt(0)) + shardingKey.substring(1);
        Object shardingValue = null;
        try {
            shardingValue = MethodUtils.invokeMethod(header, getMethodName);
        } catch (Exception e) {
            try {
                shardingValue = MethodUtils.invokeMethod(inputDTO, getMethodName);
            } catch (Exception ex) {

            }
        }
        if (shardingValue != null) {
            return shardingKey.toString();
        }
        return null;
    }

    /**
     * 根据PBS请求报文构建PBS RunLog
     * @param serviceRequestMsg 请求报文
     * @param isKeyNode
     * @return
     */
    protected SeComposeRunLogPo reqMsg2RunLogPo(ServiceRequestMsg serviceRequestMsg, boolean isKeyNode) {

        return null;
    }

    /**
     * 初始登记PBS RunLog
     * @param seComposeRunLogPo 流水登记Po
     */
    protected void recordComposeRunLog(SeComposeRunLogPo seComposeRunLogPo) {

    }

    /**
     * 更新登记PBS RunLog状态
     * @param seComposeRunLogPo 流水登记Po
     */
    protected void  updtComposeRunLog(SeComposeRunLogPo seComposeRunLogPo) {

    }

    /**
     * 交易步骤号获取
     * @return 交易步骤号
     */
    protected int countStepSeq() {
        AtomicInteger stepSeqInteger = EngineContextWrapper.getStepSeqAtomicInteger();
        if (null == stepSeqInteger) {
            stepSeqInteger = new AtomicInteger(0);
            EngineContextWrapper.setStepSeqAtomicInteger(stepSeqInteger);
        }
        int stepSeq = stepSeqInteger.incrementAndGet();
        return stepSeq;
    }

    /**
     * 参与补偿的PBS的交易步骤号计算
     * @return 交易步骤号
     */
    protected int countValidStepSeq() {
        AtomicInteger validStepSeqInteger = EngineContextWrapper.getValidStepSeqAtomicInteger();
        if (null == validStepSeqInteger) {
            validStepSeqInteger = new AtomicInteger(0);
            EngineContextWrapper.setValidStepSeqAtomicInteger(validStepSeqInteger);
        }
        int validstepSeq = validStepSeqInteger.incrementAndGet();
        return validstepSeq;
    }

    /**
     *
     * @param t 异常信息
     * @param responseMsg 响应报文
     */
    protected void throwUncertainException(Exception t, ServiceResponseMsg responseMsg) {
        if (t != null) {
            //throw new UncertainRuntimeException("没想好");
            throw new RuntimeException("没想好");
        }

        String fulllServRespCd = responseMsg.getTxHeader().getServRespCd();
        String errorMessage = responseMsg.getTxHeader().getServRespDescInfo();
        //throw new LogicControlUncertainException(fulllServRespCd,errorMessage);
        throw new RuntimeException("没想好");
    }

    /**
     * DMF抛了CommonRuntionException,直接返回
     * @param t 异常
     * @param responseMsg
     */
    protected void throwException(Exception t, ServiceResponseMsg responseMsg) {
        if (t != null) {
            //throw new CommonRuntimeException("没想好");
            throw new RuntimeException("没想好");
        }

        String fulllServRespCd = responseMsg.getTxHeader().getServRespCd();
        String errorMessage = responseMsg.getTxHeader().getServRespDescInfo();
        //throw new LogicControlUncertainException(fulllServRespCd,errorMessage);
        throw new RuntimeException("没想好");
    }

    /**
     * 判断夸库标识
     * @param custInfo 客户信息
     * @param localDb 本地数据库
     * @param localDus 分类标识
     * @return 跨库标识
     */
    protected String getCrossDbFlag(CustInfoBean custInfo, String localDb, String localDus) {
        String targetDus = custInfo.getDusId();

        //跨单元跨库
        if(!StringUtils.equals(localDus, targetDus)) {
            return EngineBaseEnumType.CrossDbFlag.CROSS_DUS.getValue();
        }

        //同单元跨库
        String targetDb = custInfo.getDbId();
        if(!StringUtils.equals(localDus, targetDus)) {
            return EngineBaseEnumType.CrossDbFlag.CROSS_DB.getValue();
        }

        //默认不跨库
        return EngineBaseEnumType.CrossDbFlag.SAME_DB.getValue();
    }

    /**
     * 判定当前PBS是否是外呼
     * @param atomicCode 原子码
     * @return 是否外呼
     */
    protected boolean isOutBoundPbs(String atomicCode) {
        return ServiceType.OUTBOUND == atomicServiceRegisterHandler.getServiceRegister(atomicCode).getServiceType();
    }

    protected void checkAndRefreshTimeout() {
        //PCS支持超时检查开启,检查交易是否超时；如果不支持，则不执行以下逻辑
        if(composeServiceProperties.isCheckTimeoutSupport()) {
            long currentTime = System.currentTimeMillis();
            int txTimeOut = EngineContextWrapper.getTimeout();
            long txStartTime = EngineContextWrapper.getStartTime();
            long txUsedTime = currentTime - txStartTime;
            if(txTimeOut < txUsedTime) {
                log.error("交易:[{}],总的执行耗时[{}]毫秒超过了设置的交易超时时间[{}]毫秒,交易抛出异常",
                        ServiceContextHandler.getTxHeader().getServNo(), txUsedTime ,txTimeOut);
//                throw new UncertainRuntimeException("没想好");
                throw new RuntimeException("没想好");
            }
            ServiceContextHandler.getTxHeader().setTxnUsedTime(String.valueOf(txUsedTime));
        }
    }
}
