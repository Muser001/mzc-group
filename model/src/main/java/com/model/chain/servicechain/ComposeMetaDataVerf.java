package com.model.chain.servicechain;

import com.model.annotation.ComposeChainStep;
import com.model.chain.constans.ComposeChainStepConstans;
import com.model.chain.properties.ComposeServiceProperties;
import com.model.chain.request.MetadataValidateResult;
import com.model.context.EngineContextWrapper;
import com.model.context.ServiceContextHandler;
import com.model.dto.BaseInputDTO;
import com.model.enumtype.EngineBaseEnumType;
import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import com.model.registry.entity.ComposeServiceRegister;
import com.model.util.ClassUtil;
import com.model.util.JsonUtils;
import com.model.util.metadata.MetadataValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 责任链步骤：元数据校验
 */
@ComposeChainStep(serviceTypeCode = {EngineBaseEnumType.ServTypeCodeType.NORMAL,
        EngineBaseEnumType.ServTypeCodeType.BUSI_CANCEL,
        EngineBaseEnumType.ServTypeCodeType.BUSI_REVERSE,
        EngineBaseEnumType.ServTypeCodeType.BUSI_REDO,
        EngineBaseEnumType.ServTypeCodeType.SAF_RECOVERY,
        EngineBaseEnumType.ServTypeCodeType.SAF_REDO,
        EngineBaseEnumType.ServTypeCodeType.INNER_SAF_PROCESS,
        EngineBaseEnumType.ServTypeCodeType.STATUE_QUERY,
        EngineBaseEnumType.ServTypeCodeType.ERROR_RETRY
                },order = ComposeChainStepConstans.COMPOSE_METADATA_VERF_STEP)
public class ComposeMetaDataVerf extends AbstractChainStep{

    @Autowired
    private ComposeServiceProperties composeServiceProperties;

    @Override
    public boolean preProcess(ServiceRequestMsg serviceRequestMsg, ServiceResponseMsg serviceResponseMsg) {

        ComposeServiceRegister serviceRegister = EngineContextWrapper.getComposeServiceRegister();
        /** 组合服务层输入DTO的转换 **/
        BaseInputDTO inputDTO = null;
        if(false){
            inputDTO = (BaseInputDTO) serviceRequestMsg.getTxBody().getTxEntity();
        }else {
            if(serviceRequestMsg.getTxBody().getTxEntity() == null){
                inputDTO = ClassUtil.newInstance(serviceRegister.getInnInfDto(),BaseInputDTO.class);
            }else {
                try {
                    Map<String,Object> txEntity = (Map<String, Object>) serviceRequestMsg.getTxBody().getTxEntity();
                    String txEnTityStr = JsonUtils.serializeToString(txEntity);
                    inputDTO = (BaseInputDTO) JsonUtils.deserialize(txEnTityStr,
                            ClassUtil.forName(serviceRegister.getInnInfDto()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        //理论上inputDTO永远不会是null对象，保险起见保留校验
        if (inputDTO == null){
            throw new RuntimeException("请求报文缺失");
        }

        serviceRequestMsg.getTxBody().setTxEntity(inputDTO);

        //SAF恢复和SAF重做不做元校验 未写

        //元数据数据格式校验开关放入上下文
        ServiceContextHandler.getTxCommon().getEngineInfo().put("1",null);
        /** 组合服务层的元数据校验 */
        MetadataValidateResult validateRet = MetadataValidationUtil.validate(inputDTO);

        return super.preProcess(serviceRequestMsg, serviceResponseMsg);
    }
}
