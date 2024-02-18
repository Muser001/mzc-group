package com.model.context.entity;

import lombok.Data;

@Data
public class DbShardingContext {
    private String custNo;
    private String dbId;
    private String tableId;
    private String dusId;
    private String shardingId;
    private String zoneVal;
    private String currentDate;
}
