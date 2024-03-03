package com.model.message.header;


import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceResponseHeader implements Serializable {

    private static final long serialVersionUID = 1067638407236449111L;
    private String servRespCd;
    private String servRespDescInfo;
}
