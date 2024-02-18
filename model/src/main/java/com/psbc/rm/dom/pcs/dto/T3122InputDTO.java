package com.psbc.rm.dom.pcs.dto;

import com.model.annotation.Field;
import com.model.dto.BaseInputDTO;
import lombok.Data;

@Data
public class T3122InputDTO extends BaseInputDTO {

    @Field(id = "T1111",name = "姓名", isNull = false)
    private String name;
}
