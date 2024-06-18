package com.psbc.rm.dom.pcs.dto;

import com.model.annotation.Field;
import com.model.dto.FlowExchangeDTO;
import lombok.Data;

@Data
public class T3122ExchangeDTO extends FlowExchangeDTO {

    private static final long serialVersionUID = -1471108830446186348L;

    @Field(id = "T1111",name = "姓名", isNull = false)
    private String name;
}
