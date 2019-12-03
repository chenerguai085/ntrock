package com.rh.netrock.entity;

import com.rh.netrock.enums.CompanyBusEnum;
import com.rh.netrock.enums.OpenTypeEnum;

import java.io.Serializable;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/14
 */
public class NetRock implements Serializable {


    /**
     * 开门的唯一标识  豪利士标识roomId ， 悉点标识门锁序列号
     */
    private String deviceSerial;

    /**
     * 门锁型号
     */
    private String productModel;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


    /**
     * 开门数据
     */
    private String cardData;

    /**
     * 旧的开门数据
     */
    private String oldCardData;

    /**
     * 接口的域名
     */
    private String domain;

    /**
     * 对应厂商名称
     */
    private CompanyBusEnum companyBusEnum;

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


    public CompanyBusEnum getCompanyBusEnum() {
        return companyBusEnum;
    }

    public void setCompanyBusEnum(CompanyBusEnum companyBusEnum) {
        this.companyBusEnum = companyBusEnum;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


//    public String getCardNo() {
//        return cardNo;
//    }
//
//    public void setCardNo(String cardNo) {
//        this.cardNo = cardNo;
//    }
//
//    public String getPwd() {
//        return pwd;
//    }
//
//    public void setPwd(String pwd) {
//        this.pwd = pwd;
//    }
//
//
//    public String getNewPwd() {
//        return newPwd;
//    }
//
//    public void setNewPwd(String newPwd) {
//        this.newPwd = newPwd;
//    }
//
//    public String getOldPwd() {
//        return oldPwd;
//    }
//
//    public void setOldPwd(String oldPwd) {
//        this.oldPwd = oldPwd;
//    }


//    public String getRoomId() {
//
//        return roomId;
//    }
//
//    public void setRoomId(String roomId) {
//        this.roomId = roomId;
//    }


    public String getCardData() {
        return cardData;
    }

    public void setCardData(String cardData) {
        this.cardData = cardData;
    }


    public String getOldCardData() {
        return oldCardData;
    }

    public void setOldCardData(String oldCardData) {
        this.oldCardData = oldCardData;
    }
}
