package com.model.enumtype;

public class OnlineCommonEnumType {

    /**
     * DUS类型
     */
    public enum DusType {
        BUDS("B","BUDS"),
        CDUS("C","CDUS");

        private String value;
        private String name;

        DusType(String value, String name) {
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
}
