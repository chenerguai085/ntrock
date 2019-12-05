package com.rh.netlock.enums;

public enum CompanyBusEnum {

    XD_NETROCK(1, "悉点互联网门锁"),
    HLS_NETROCK(2,"豪力士互联网门锁"),
    XIEZ_FACEROCK(3,"携住人脸识别门锁"),
    YUNDING_NETROCK(4,"云丁互联网门锁"),
    ;

    private Integer code;
    private String msg;


    CompanyBusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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

}
