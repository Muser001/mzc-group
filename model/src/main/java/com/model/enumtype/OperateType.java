package com.model.enumtype;

public enum OperateType {
    QUERY(1,"查询类"),
    MAINTAIN(2,"维护类"),
    ACCOUNTS(3,"财务类");

    private int value;

    private String name;

    OperateType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
