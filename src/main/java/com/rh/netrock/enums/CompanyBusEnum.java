package com.rh.netrock.enums;

public enum CompanyBusEnum {

    XD_NETROCK(1, "悉点互联网门锁","com.rh.netrock.handle.impl.XidianRockImpl"),
    HLS_NETROCK(2,"豪力士互联网门锁","com.rh.netrock.handle.impl.HlisRockImpl"),
    ;

    private Integer code;
    private String msg;
    private String clazz;

    CompanyBusEnum(Integer code, String msg,String clazz) {
        this.code = code;
        this.msg = msg;
        this.clazz = clazz;
    }

    public static CompanyBusEnum getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (CompanyBusEnum item : CompanyBusEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public Integer code() {
        return this.code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
