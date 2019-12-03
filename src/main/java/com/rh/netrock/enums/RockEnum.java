package com.rh.netrock.enums;

/**
 *remark:锁对接url
 *@author:chenj
 *@date: 2019/11/12
 *@return
 */
public enum RockEnum {

    ADD_PWD_API("/openApi/v1/addPwd"),

    UPDATE_PWD_API("/openApi/v1/updatePwd"),

    DELETE_PWD_API("/openApi/v1/deletePwd"),

    ADD_CARD_API("/openApi/v1/addCard"),

    UPDATE_CARD_API("/openApi/v1/updateCard"),

    DELETE_CARD_API("/openApi/v1/deleteCard"),

    HLS_URL_SUF("/NetLockWebServer/NetLockWebServer.asmx"),
    XMLPARSE_PRE("Command"),

    XMLPARSE_SUF("</Command>"),

    HL_Add_OpenUser_REQ("Add_OpenUser_REQ"),
    HL_Delete_Openuser_REQ("Delete_Openuser_REQ"),
    HL_Clear_Openuser_REQ("Clear_Openuser_REQ"),
    HL_OpenLock_REQ("OpenLock_REQ"),
    ;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    RockEnum(String msg) {

        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }

}
