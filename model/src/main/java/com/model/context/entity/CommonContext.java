package com.model.context.entity;

import com.model.message.header.ServiceRequestHeader;
import lombok.Data;

@Data
public class CommonContext {
    private ServiceRequestHeader txHeader = new ServiceRequestHeader();

}
