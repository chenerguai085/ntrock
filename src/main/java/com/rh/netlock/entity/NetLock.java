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
     * 开门的唯一标识  豪利士标识roomId ， 悉点标识门锁序列号 携住-RoomNo
     */
    private String lockId;

    /**
     * 扩展字段 目前  悉点-门锁的型号  携住-表示HotelId字段
     */
    private String lockOption;

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

    public String getLockId() {
        return lockId;
    }

    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    public String getLockOption() {
        return lockOption;
    }

    public void setLockOption(String lockOption) {
        this.lockOption = lockOption;
    }

    public String getLockData() {
        return lockData;
    }

    public void setLockData(String lockData) {
        this.lockData = lockData;
    }

}
