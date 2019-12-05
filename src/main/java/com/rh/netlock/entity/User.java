package com.rh.netlock.entity;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/5
 */
public class User extends LockBase implements Serializable {

    /**
     * 用户名 -携住人脸 获取token接口要传入用户名
     */
    private String userName;

    /**
     * 密码 -携住人脸 获取token接口要传入密码
     */
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
