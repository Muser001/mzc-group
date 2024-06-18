package com.psbc.rm.dom.pcs.dto;

import com.model.annotation.Field;
import com.model.dto.FlowExchangeDTO;
import lombok.Data;

@Data
public class T3927ExchangeDTO extends FlowExchangeDTO {

    private static final long serialVersionUID = 6304198569646256309L;

    @Field(id = "T1111",name = "姓名", isNull = false)
    private String name;
}
