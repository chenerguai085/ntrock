package com.rh.netlock.enums;

/**
 *remark:锁对接url
 *@author:chenj
 *@date: 2019/11/12
 *@return
 */
public enum LockEnum {

    ADD_PWD_API("/openApi/v1/addPwd"),
    UPDATE_PWD_API("/openApi/v1/updatePwd"),
    DELETE_PWD_API("/openApi/v1/deletePwd"),
    ADD_CARD_API("/openApi/v1/addCard"),
    UPDATE_CARD_API("/openApi/v1/updateCard"),
    DELETE_CARD_API("/openApi/v1/deleteCard"),
    HLS_URL_SUF("/NetLockWebServer/NetLockWebServer.asmx"),
    XMLPARSE_PRE("Command"),
    XMLPARSE_SUF("</Command>"),
    HLS_Add_OpenUser_REQ("Add_OpenUser_REQ"),
    HLS_Delete_Openuser_REQ("Delete_Openuser_REQ"),
    HLS_Clear_Openuser_REQ("Clear_Openuser_REQ"),
    HLS_OpenLock_REQ("OpenLock_REQ"),
    PMS_CODE("RWKJ"), //与携住对接的PmsCode值
    GET_TOKEN_API("/token"), //获取token url https://open.xiezhuwang.com
    FACE_ADD_API("/api/FaceCollect/ReceiveFace"), //增加人脸url
    XIEZHU_USERNAME("rwkj"),  //获取token信息的用户名
    XIEZHU_PASSWORD("rwkj123"), //获取token信息的密码
    XIEZHU_DOMAIN("https://open.xiezhuwang.com"),
    HLS_DOMAIN("http://121.201.67.205:8007"),
    XIDIAN_DOMAIN("http://gateway.seedien.com"),
    YUNDING_DOMAIN("https://saas-openapi.dding.net/v2"),
    YUND_TOKEN_API("/access_token"),
    YUND_ADD_PASSWORD("/add_password"),
    YUND_UPDATE_PASSWORD("/update_password"),
    YUND_DELETE_PASSWORD("/delete_password"),
    HONGRFACE_DOMAIN("http://39.108.175.59:8080"),
    HONGR_FACE_IMAGEUPLOAD_API("/img/upload"),
    HONGR_FACE_ADD_API("/person/staff/add"),
    HONGR_FACE_DEL_API("/person/staff/delete"),
    HONGR_FACE_LOGIN_API("/admin/login"),
    HONGR_FACE_BATCHDEL_API("/person/staff/batch_delete"),

    HONGR_FACE_USERNAME("睿沃科技"),  //获取token信息的用户名
    HONGR_FACE_PASSWORD("RWkj1234"), //获取token信息的密码
    HONGR_FACE_COMPANY("RW"),
    ;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;

    LockEnum(String msg) {

        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }

}
