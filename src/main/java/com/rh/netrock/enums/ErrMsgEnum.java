package com.rh.netrock.enums;

/**
 *remark: 错误信息enum
 *@author:chenj
 *@date: 2019/11/21
 *@return
 */
public enum ErrMsgEnum {

    REMOTE_ERRMSG("调用第三方接口异常："),
    REMOTE_RESP_ERRJSON("第三方接口返回非json格式"),
    REMOTE_RESP_ERRDATE("接口返回过期时间格式有误转换异常"),
    ERR("请求失败"),
    EER_COMANY_NO("未传入厂商代号"),
    ERR_API_UNSUPPORT("改厂商互联网门锁不支持此API"),
    ERR_OPENTRPE("未传入开门类型"),
    ERR_OBJECTISNULL("对象值为空"),
    ERR_PARAMS_NULL("非空参数不能为空"),
    ERR_DOMAIN_NULL("互联网门锁厂商对接域名不能为空"),
    ;



    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    ErrMsgEnum(String msg) {

        this.msg = msg;
    }

    public String msg() {
        return this.msg;
    }

}
