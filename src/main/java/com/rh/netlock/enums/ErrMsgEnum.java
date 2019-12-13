package com.rh.netlock.enums;

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
    EER_COMANY_NO("未传入门锁厂商或门锁类型"),
    ERR_API_UNSUPPORT("该厂商互联网门锁不支持此API"),
    ERR_OPENTRPE("未传入开门类型"),
    ERR_OBJECTISNULL("对象值为空"),
    ERR_PARAMS_NULL("非空参数不能为空"),
    ERR_DOMAIN_NULL("互联网门锁厂商对接域名不能为空"),
    ERR_MSG_TOKEN("获取tokne信息异常,返回token信息非json格式字符串"),
    ERR_MSG_DATE_FORMAT("获取tokne信息异常,返回日期格式非约定格式"),
    ERR_MSG_OLDPWD_ISNULL("旧密码不能为空"),

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
