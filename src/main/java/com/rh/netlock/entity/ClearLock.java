package com.rh.netlock.entity;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/5
 */
public class ClearLock extends LockBase implements Serializable {

    /**
     *锁标识
     */
    private String lockId;

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }
}
