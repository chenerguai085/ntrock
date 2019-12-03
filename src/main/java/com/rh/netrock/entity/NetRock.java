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
     * 开门的唯一标识  豪利士标识roomId ， 悉点标识门锁序列号 携住-RoomNo
     */
    private String deviceSerial;

    /**
     * 门锁型号  携住-表示HotelId字段
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

    /**
     * 姓名 -携住人脸设备提供
     */
    private String name;

    /**
     * 姓名 -携住人脸 请求是要加token
     */
    private  String token;

    /**
     * 用户名 -携住人脸 获取token接口要传入用户名
     */
    private String userName;

    /**
     * 密码 -携住人脸 获取token接口要传入密码
     */
    private String password;

    private String idCardNo;

    private String mobile;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
