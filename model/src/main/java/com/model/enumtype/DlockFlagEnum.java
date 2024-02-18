package com.model.enumtype;

public enum DlockFlagEnum {

    C("2","检查锁"),
    Y("1","加锁"),
    N("3","不加锁");

    private String value;
    private String name;

    DlockFlagEnum(String value, String name){
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
