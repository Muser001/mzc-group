package com.model.process.flow;

import com.model.restservice.ServiceCallFactory;
import com.model.context.ServiceContextHandler;
import com.model.custinfo.CustInfoBean;
import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.registry.entity.AtomicServiceRegister;
import com.model.route.RouteInfo;
import com.model.util.MetadataRouteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PBS调用入口类
 */
@Slf4j
@Component
public class ComposeServiceNode extends AbstractComposeServiceNode{

    @Autowired
    private ServiceCallFactory serviceCallFactory;

    /**
     * 应用最多允许注册到BDUS和CDUS两种
     */
    private static final int ALLOWED_MAX_DUS_TYPE_SIZE = 2;

    /**
     *
     * @param atomicCode 原子码
     * @param dusType dus类型
     * @param isKeyNode 关键节点
     * @param strategy 策略
     * @param inputDTO 输入DTO
     * @return
     */
    @Override
    public NodeRes process(String atomicCode, String dusType, boolean isKeyNode, String strategy, BaseInputDTO inputDTO) {

        //检测PCS是否超时
        checkAndRefreshTimeout();

        //服务编排号，CustInfoMap里的路由信息以此作为key
        int stepSeq = countStepSeq();

        int validStepSeq = 0;

        AtomicServiceRegister atomicServiceRegister = loadAtomicRegister(atomicCode);
        String dynLockFlg = atomicServiceRegister.getDynLockFlg();

        //初始化PCS上下文
        initPcsContext(atomicServiceRegister, dusType, stepSeq, dynLockFlg, inputDTO);

        // 基于当前PCS上下文、PBS inputDTO、stepSeq、atomicCode构造PBS请求报文
        ServiceRequestMsg requestMsg = buildAtomicRequestMsg(stepSeq, atomicCode, inputDTO, atomicServiceRegister);

        log.info("PBS开始调用[{}]-[{}]", stepSeq, validStepSeq);
        ServiceResponseMsg serviceResponseMsg = serviceCallFactory.getServiceExecutor(false).invokeService(requestMsg, 999999);
        log.info("PBS调用结束[{}]-[{}]", stepSeq, validStepSeq);
        return buildNodeResponse(serviceResponseMsg);
    }

    private AtomicServiceRegister loadAtomicRegister(String atomicCode) {
        AtomicServiceRegister atomicServiceRegister = atomicServiceRegisterHandler.getServiceRegister(atomicCode);
        if (atomicServiceRegister == null) {
            log.error("原子服务【{}】注册模型未找到",atomicCode);
            throw new RuntimeException("没想好");
        }
        return atomicServiceRegister;
    }

    /**
     * 初始化PCS上下文
     * @param atomicServiceRegister 原子服务注册
     * @param dusType dus类型
     * @param stepSeq 调用链
     * @param dynLockFlg 分布式锁标识
     * @param inputDTO 输入DTO
     */
    private void initPcsContext(AtomicServiceRegister atomicServiceRegister, String dusType,
                                int stepSeq, String dynLockFlg, BaseInputDTO inputDTO) {
        //PBS路由信息和跨库标识塞到PCS上下文
        String mainMapElementInfo = ServiceContextHandler.getMainElementInfo();
        String atomicCode = atomicServiceRegister.getServiceId();
        // TODO: 2024/1/9 分库，有错未完成，后期完善 
//        CustInfoBean pbsInfo = getRoute(atomicCode, dusType, inputDTO, mainMapElementInfo);
//        String crossDbFlag =
//        ServiceContextHandler.
    }

    /**
     * 获取路由信息
     * @param atomicCode 原子码
     * @param dusType dus类型
     * @param inputDTO 输入dto
     * @param mainMapElementInfo 映射要素信息
     * @return
     */
    private CustInfoBean getRoute(String atomicCode, String dusType, BaseInputDTO inputDTO,
                                  String mainMapElementInfo) {
        // PBS在服务编排里指定为CDUS节点，直接返回CDS
        if (StringUtils.equals("CUDS",dusType)) {
            CustInfoBean pbsInfo = serviceRoute.routeCDus();
            if(log.isDebugEnabled()) {
                log.debug("PCS Flow XML配置了该PBS节点的dusType为CDUS，故路由请求到：{}",pbsInfo.getDusId());
            }
            return pbsInfo;
        }

        // 查询Nacos上该PBS在那些DUS上有注册
        List<String> listType = getRegistDusType(atomicCode);

        // PBS同时在BDUS和CDUS注册，需在BDUS路由结果报客户不存在或不存在异常转发到CDUS
        List<RouteInfo> routeRet = MetadataRouteUtil.getRouteInfo(inputDTO);
        return serviceRoute.routeTargetDus(routeRet,mainMapElementInfo,true);
    }

    /**
     * 查询DMF 和获取PBS服务注册到哪些DUS上
     * @param atomicCode 原子吗
     * @return 注册DUS类型List
     */
    private List<String> getRegistDusType(String atomicCode) {
//        List<String> returnList =
        return null;
    }

    private NodeRes buildNodeResponse(ServiceResponseMsg serviceResponseMsg) {
        NodeRes nodeRes = new NodeRes();
        nodeRes.setBaseOutputDTO((BaseOutputDTO)serviceResponseMsg.getTxBody().getTxEntity());
        return nodeRes;
    }

}
