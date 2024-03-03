package com.psbc.rm.dom.pojo;

import lombok.Data;

@Data
public class Test_1 {
    private String name;
    private Integer number;
    private String time;

    public Test_1(){

    }
    public Test_1(String name, Integer number, String time) {
        this.name = name;
        this.number = number;
        this.time = time;
    }
    public Test_1(String name, Integer number) {
        this.name = name;
        this.number = number;
    }
}
