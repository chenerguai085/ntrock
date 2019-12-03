package com.rh.netrock.entity;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/11
 */
public  class RockReqResult implements Serializable {
    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String localErrMsg;


    private String remoteErrMsg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getLocalErrMsg() {
        return localErrMsg;
    }

    public void setLocalErrMsg(String localErrMsg) {
        this.localErrMsg = localErrMsg;
    }

    public String getRemoteErrMsg() {
        return remoteErrMsg;
    }

    public void setRemoteErrMsg(String remoteErrMsg) {
        this.remoteErrMsg = remoteErrMsg;
    }
}
