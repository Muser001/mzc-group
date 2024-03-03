package com.model.message.body.txcomn;

import lombok.Data;

import java.io.Serializable;

@Data
public class TxEntity implements Serializable {
    private static final long serialVersionUID = -2115809062183957088L;
    private String A;
    private String B;
    private String C;
    private String D;
}
