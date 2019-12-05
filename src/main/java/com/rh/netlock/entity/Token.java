package com.rh.netlock.entity;

import java.io.Serializable;
import java.util.Date;

/** token信息
 * @author:chenj
 * @remark:
 * @date: 2019/11/21
 */
public class Token implements Serializable {

    private String accessToken; //token值
//    private String tokenType;  //token类型
////    private Long expiresIn; //过期时间
////    private String userId; //用户id
//    private String userName; //用户名
////    private Date issued;  //当前使用日期
    private Date expiresDate; //过期日期
//    private Integer optionType;//附加信息字段 0-第三方接口返回非json字符串 1-请求第三方接口报错 2-其他异常
//
//    private String  optionMsg;//请求第三方接口报错信息
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Date expiresDate) {
        this.expiresDate = expiresDate;
    }
}

