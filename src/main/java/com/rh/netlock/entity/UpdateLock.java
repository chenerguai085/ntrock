package com.rh.netlock.entity;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/5
 */
public class UpdateLock extends NetLock implements Serializable {

    /**
     * 旧的开门数据  悉点更新使用
     */
    private String oldLockData;

    public String getOldLockData() {
        return oldLockData;
    }

    public void setOldLockData(String oldLockData) {
        this.oldLockData = oldLockData;
    }
}
