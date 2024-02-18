package com.model.flow.context;

import com.model.dto.BaseInputDTO;
import com.model.dto.BaseOutputDTO;
import com.model.dto.FlowExchangeDTO;
import net.sf.cglib.beans.BeanCopier;

/**
 * 流程交换区
 */
public class DataArea {

    private BaseInputDTO input;
    private BaseOutputDTO output;
    private FlowExchangeDTO exchange;

    public DataArea(BaseInputDTO input, BaseOutputDTO output, FlowExchangeDTO exchange) {
        this.input = input;
        this.output = output;
        this.exchange = exchange;
    }

    public BaseInputDTO getInput() {
        return input;
    }

    public BaseOutputDTO getOutput() {
        return output;
    }

    public FlowExchangeDTO getExchange() {
        return exchange;
    }

    public DataArea copy() throws InstantiationException, IllegalAccessException {
        BaseInputDTO inputDTO = input.getClass().newInstance();
        BaseOutputDTO outputDTO = output.getClass().newInstance();
        BeanCopier.create(input.getClass(), input.getClass(), false).copy(input, inputDTO, null);
        BeanCopier.create(output.getClass(), output.getClass(), false).copy(output, inputDTO, null);
        return new DataArea(inputDTO, outputDTO, exchange);
    }
}
