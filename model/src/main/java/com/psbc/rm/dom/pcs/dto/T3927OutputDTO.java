package com.psbc.rm.dom.pcs.dto;

import com.model.annotation.Field;
import com.model.dto.BaseOutputDTO;
import lombok.Data;

@Data
public class T3927OutputDTO extends BaseOutputDTO {

    private static final long serialVersionUID = -4789910686376098512L;

    @Field(id = "T1111",name = "姓名", isNull = false)
    private String name;
}
