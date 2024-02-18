package com.model.process;

import com.model.message.ServiceRequestMsg;
import com.model.message.ServiceResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("reverseProcess")
public class AtomicReverseProcess extends AbstractAtomicProcess{
    @Override
    public void invoke(ServiceRequestMsg request, ServiceResponseMsg response) {

    }
}
