package com.psbc.rm.dom.pcs.dto;

import com.model.annotation.Field;
import com.model.dto.BaseInputDTO;
import lombok.Data;

@Data
public class T3927InputDTO extends BaseInputDTO {

    private static final long serialVersionUID = -8115050831528709301L;

    @Field(id = "T1111",name = "姓名", isNull = false)
    private String name;
}
