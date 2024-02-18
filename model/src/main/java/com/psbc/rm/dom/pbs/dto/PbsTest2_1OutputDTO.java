package com.psbc.rm.dom.pbs.dto;

import com.model.annotation.Field;
import com.model.dto.BaseOutputDTO;
import lombok.Data;

@Data
public class PbsTest2_1OutputDTO extends BaseOutputDTO {
    @Field(id = "T1111",name = "姓名", isNull = false)
    private String name;
}
