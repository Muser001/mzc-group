package com.model.context.entity;

import com.model.message.ServiceCommonRequestMsg;
import com.model.message.body.txcomn.TxComn1;
import com.model.message.body.txcomn.TxComn2;
import com.model.message.body.txcomn.TxComn3;
import lombok.Data;

@Data
public class SharedContext {
    private ServiceCommonRequestMsg txCommon = new ServiceCommonRequestMsg();
    private TxComn1 txComn1 = new TxComn1();
    private TxComn2 txComn2 = new TxComn2();
    private TxComn3 txComn3 = new TxComn3();

}
