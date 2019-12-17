package com.rh.netlock.entity;

import com.rh.netlock.enums.OpenTypeEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/14
 */
public class NetLock extends LockBase implements Serializable {

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 开门数据
     */
    private String lockData;

    /**
     * base64 数据串
     */
    private String fileStr;

    /**
     * 开门类型
     */
    private OpenTypeEnum openTypeEnum;


    public String getFileStr() {
        return fileStr;
    }

    public void setFileStr(String fileStr) {
        this.fileStr = fileStr;
    }

    public OpenTypeEnum getOpenTypeEnum() {
        return openTypeEnum;
    }

    public void setOpenTypeEnum(OpenTypeEnum openTypeEnum) {
        this.openTypeEnum = openTypeEnum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLockData() {
        return lockData;
    }

    public void setLockData(String lockData) {
        this.lockData = lockData;
    }

}
