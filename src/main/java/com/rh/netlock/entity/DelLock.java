package com.rh.netlock.entity;

import com.rh.netlock.enums.OpenTypeEnum;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/5
 */
public class DelLock extends ClearLock implements Serializable {

    /**
     * 扩展字段 目前  悉点-门锁的型号  携住-表示HotelId字段
     */
    private String lockOption;

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

    public String getLockOption() {
        return lockOption;
    }

    public void setLockOption(String lockOption) {
        this.lockOption = lockOption;
    }
}
