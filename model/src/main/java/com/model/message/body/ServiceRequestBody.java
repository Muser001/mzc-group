package com.model.message.body;

import com.model.message.body.txcomn.TxComn1;
import com.model.message.body.txcomn.TxComn2;
import com.model.message.body.txcomn.TxComn3;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceRequestBody<T> implements Serializable {

    private static final long serialVersionUID = -6614656230306219725L;

    private TxComn1 txComn1 = new TxComn1();
    private TxComn2 txComn2 = new TxComn2();
    private TxComn3 txComn3 = new TxComn3();
    private T txEntity ;
}
