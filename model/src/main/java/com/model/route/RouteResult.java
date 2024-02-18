package com.model.route;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class RouteResult implements Serializable {
    private static final long serialVersionUID = 7160607776085301663L;
    private String resultCode;
    private String reslutMsg;
    private String dusCode;
    private String subTableId;
    private String custNo;
    private String routeStatus;
    private String migratedStatus;
    private Date migrateTime;
    private String shardingTime;
    private String dataSourceId;
    private String mapKeyType;
    private String mapKeyValue;
    private String mappingCusId;
}
