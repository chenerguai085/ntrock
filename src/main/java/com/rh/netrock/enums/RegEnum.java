package com.rh.netrock.enums;

public enum RegEnum {


    CARD_DATA_REG("^\\d{1,10}$"),

    TIME_REG("^(\\d{4})(\\-)(\\d{2})(\\-)(\\d{2})(\\s+)(\\d{2})(\\:)(\\d{2})(\\:)(\\d{2})$"),

    PWD_REG("^\\d{6}$"),
    ;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    RegEnum(String msg) {

        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }

}
