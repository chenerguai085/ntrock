package com.rh.netlock.entity;

import com.rh.netlock.enums.CompanyBusEnum;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/5
 */
public class LockBase implements Serializable {

    /**
     * 接口的域名
     */
    private String domain;

    /**
     * 对应厂商名称
     */
    private CompanyBusEnum companyBusEnum;

    /**
     * 姓名 -携住人脸 请求是要加token
     */
    private  String token;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public CompanyBusEnum getCompanyBusEnum() {
        return companyBusEnum;
    }

    public void setCompanyBusEnum(CompanyBusEnum companyBusEnum) {
        this.companyBusEnum = companyBusEnum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
