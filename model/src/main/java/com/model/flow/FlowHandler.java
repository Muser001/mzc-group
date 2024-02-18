package com.model.flow;

import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.dto.FlowExchangeDTO;
import com.model.flow.context.DataArea;
import com.model.flow.context.FlowContext;
import com.model.flow.helper.FlowHelper;
import com.model.flownode.FlowTransactionConf;
import com.model.mapper.FlowOutputMapper;
import com.model.util.ClassUtil;
import com.model.util.EntityDTOConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 服务编排处理入口类
 */

@Slf4j
@Component
public class FlowHandler {

    @Autowired
    private FlowHelper helper;

    /**
     * 调用服务编排方法入口（组合服务公共报文）
     * @param composeCode 组合交易代码
     * @param inputDTO 交易请求信息
     * @return 交易响应结果
     */
    public BaseOutputDTO callFlow(String composeCode, BaseInputDTO inputDTO) {
        return this.excuteFlow(composeCode, inputDTO);
    }

    /**
     * 子流程调用api，与交易调用api分开
     * @param composeCode 组合交易代码
     * @param inputDTO 请求交易信息
     * @return 交易响应结果
     */
    public BaseOutputDTO excuteFlow(String composeCode, BaseInputDTO inputDTO) {
        FlowTransactionConf conf = helper.getModelManager().getFlowConf(composeCode);
        if (conf == null) {
            throw new RuntimeException("没想好");
        }

        FlowContext context = null;

        try {
            context = this.create(composeCode, conf, inputDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        helper.getFlowEngine().execute(conf, context, helper);
        //返回结果
        this.outMapping(conf.getMapper(), context);
        return context.getDataArea().getOutput();
    }

    /**
     * 返回映射
     * @param mapperId
     * @param context 上下文
     */
    protected void outMapping(String mapperId, FlowContext context) {
        FlowOutputMapper mapper = helper.getMapperManager().getFlowMapper(mapperId);
        mapper.outputMapping(context.getDataArea().getInput(), context.getDataArea().getExchange(),
                    context.getDataArea().getOutput());
    }

    /**
     * 创建流程数据区
     * @param composeCode 组合交易代码
     * @param conf 事务配置
     * @param input 交易请求信息
     * @return
     */
    public FlowContext create(String composeCode, FlowTransactionConf conf, BaseInputDTO input) {
        // 新建流程上下文
        FlowContext context = new FlowContext();
        context.setComposeCode(composeCode);
        // 构建数据交换区 DTO
        FlowExchangeDTO exchange = ClassUtil.newInstance(conf.getExchange(), FlowExchangeDTO.class);
        // 构建出参 DTO
        BaseOutputDTO output = ClassUtil.newInstance(conf.getOutput(), null);
        // 拷贝input同名字段数到exchange区
        EntityDTOConvert.convert(input, exchange);
        // 上下文设置数据 入参 DTO、转换DT、出参 DTO
        context.setDataArea(new DataArea(input, output, exchange));
        return context;
    }
}
