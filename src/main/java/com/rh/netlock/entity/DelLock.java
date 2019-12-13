package com.rh.netlock.entity;

import com.rh.netlock.enums.OpenTypeEnum;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/5
 */
public class DelLock extends LockBase implements Serializable {
    /**
     * 开门数据
     */
    private String lockData;

    /**
     * 开门类型
     */
    private OpenTypeEnum openTypeEnum;

    public OpenTypeEnum getOpenTypeEnum() {
        return openTypeEnum;
    }

    public void setOpenTypeEnum(OpenTypeEnum openTypeEnum) {
        this.openTypeEnum = openTypeEnum;
    }

    public String getLockData() {
        return lockData;
    }

    public void setLockData(String lockData) {
        this.lockData = lockData;
    }

}
