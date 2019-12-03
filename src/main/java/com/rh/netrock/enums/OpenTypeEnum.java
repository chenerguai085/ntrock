package com.rh.netrock.enums;


public enum OpenTypeEnum {
    ICCARD(1, "iccard"),     //刷卡
    IDCARD(2, "idcard"),// 刷身份证
    PASSWORD(3, "password"), //密码类型
    FACE(4, "face"), //刷人脸开门
    ;

    private Integer code;
    private String msg;

    OpenTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static OpenTypeEnum getEnumByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (OpenTypeEnum item : OpenTypeEnum.values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public Integer code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
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
