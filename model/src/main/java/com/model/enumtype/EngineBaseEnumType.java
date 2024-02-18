package com.model.enumtype;

public class EngineBaseEnumType {

    public enum ServTypeCodeType{
        /**
         * 1-正常交易
         */
        NORMAL("1","正常交易"),

        /**
         * 2-业务取消交易
         */
        BUSI_CANCEL("2","业务取消交易"),

        /**
         * 3-业务冲正交易
         */
        BUSI_REVERSE("3","业务冲正交易"),

        /**
         * 4-业务冲正交易
         */
        BUSI_REDO("4","重发交易"),

        /**
         * 5-SAF恢复交易1
         */
        SAF_RECOVERY("5","SAF恢复交易"),

        /**
         * 6-SAF重发交易
         */
        SAF_REDO("6","SAF重发交易"),

        /**
         * 0-内置SAF处理交易，用于处理引擎异步发起的SAF补偿和SAF重做
         */
        INNER_SAF_PROCESS("0","内置SAF处理交易"),

        /**
         * 7-状态查证交易
         */
        STATUE_QUERY("7","状态查证交易"),

        /**
         * 8-差错重发交易
         */
        ERROR_RETRY("8","差错重发交易");

        private String value;
        private String name;
        ServTypeCodeType(String value, String name){
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

        public static ServTypeCodeType toEnum(String descValue) {
            for (ServTypeCodeType retEnum : ServTypeCodeType.values()) {
                if (retEnum.getValue().equals(descValue)) {
                    return retEnum;
                }
            }
            return null;
        }
    }

    public enum DeployModeEnum{

        COMBINE("0","合并部署"),

        SPLIT("1","分离部署");

        private String value;
        private String name;
        DeployModeEnum(String value, String name){
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
    /**
     * 业务登记方向
     */
    public enum BizExeDirectEnum{
        NORMAL ("0","正常登记"),

        COMPENSATE("1","补偿登记"),

        QUERY("2","状态查询");

        private String value;

        private String name;

        BizExeDirectEnum(String value, String name){
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

    public static enum CrossDbFlag {
        SAME_DB("1","本单元本库"),
        CROSS_DB("2","本段元跨库"),
        CROSS_DUS("3","跨单元跨库");

        private String value;
        private String name;

        CrossDbFlag(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }
    }
}
