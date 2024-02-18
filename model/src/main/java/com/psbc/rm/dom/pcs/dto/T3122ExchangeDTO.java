package com.psbc.rm.dom.pcs.dto;

import com.model.annotation.Field;
import com.model.dto.FlowExchangeDTO;
import lombok.Data;

@Data
public class T3122ExchangeDTO extends FlowExchangeDTO {
    @Field(id = "T1111",name = "姓名", isNull = false)
    private String name;
}
