package com.rh.netrock.handle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.rh.netrock.entity.NetRock;
import com.rh.netrock.entity.Token;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.RockEnum;
import com.rh.netrock.util.HttpUtil;
import com.rh.netrock.util.RockCommonUtil;
import com.rhcj.commons.DateHelper;
import com.rhcj.commons.JsonHelper;
import org.apache.commons.lang3.StringUtils;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Date;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/4
 */
public  class YunDingRockImpl {


    /**
     *remark: 获取token信息
     *@author:chenj
     *@date: 2019/12/4
     *@return com.rh.netrock.entity.Token
     */
    public static Token getToken(NetRock netRock) throws Exception {
        RockCommonUtil.paramsVerify(netRock,"userName","password");

        if(StringUtils.isBlank(netRock.getDomain())){

            netRock.setDomain(RockEnum.YUNDING_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("client_id",netRock.getUserName());
        jsonObject.put("client_secret",netRock.getPassword());

        String result = HttpUtil.yundingPost(netRock.getDomain() + RockEnum.YUND_TOKEN_API.getMsg(), jsonObject.toString());

        Map jsonMap = JsonHelper.toMap(result);

        Token token = null;

        if(null == jsonMap){

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        if(null != jsonMap && jsonMap.get("ErrNo").equals("0")){

            token = new Token();

            token.setAccessToken(jsonMap.get("access_token").toString());

            Date expiresDate = DateHelper.secondToDate(Long.valueOf(jsonMap.get("expires_time").toString()));

            token.setExpiresDate(expiresDate);
        }else if(null != jsonMap && !jsonMap.get("ErrNo").equals("0")){

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + jsonMap.get("ErrMsg").toString());
        }

        return token;
    }


    /**
     *remark: 添加密码
     *@author:chenj
     *@date: 2019/12/4
     *@return java.lang.String
     */

    public static String add(NetRock netRock) throws Exception {


        RockCommonUtil.paramsVerify(netRock,"userName","password");


        JSONObject jsonObject = new JSONObject();

        jsonObject.put("access_token",netRock.getToken());
        jsonObject.put("home_id",netRock.getProductModel());
        jsonObject.put("room_id",netRock.getDeviceSerial());
        jsonObject.put("phonenumber",netRock.getMobile());
        jsonObject.put("is_default",0);
        jsonObject.put("password",netRock.getCardData());
        jsonObject.put("name","在线密码" + netRock.getCardData());
        jsonObject.put("permission_begin",DateHelper.parseSecond(netRock.getStartTime()));
        jsonObject.put("permission_end",DateHelper.parseSecond(netRock.getEndTime()));
        String result = HttpUtil.yundingPost(netRock.getDomain() + RockEnum.YUND_ADD_PASSWORD.getMsg(), jsonObject.toString());

        Map jsonMap = JsonHelper.toMap(result);

        if(null == jsonMap){

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        if(null != jsonMap && jsonMap.get("ErrNo").equals("0")){

            result = jsonMap.get("id").toString();

            return result;
        }else if(null != jsonMap && !jsonMap.get("ErrNo").equals("0")){

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + jsonMap.get("ErrMsg").toString());
        }

        return "";
    }


    /**
     *remark:
     *@author:chenj
     *@date: 2019/12/4
     *@return java.lang.String
     */
    public static String update(NetRock netRock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("access_token",netRock.getToken());
        jsonObject.put("home_id",netRock.getProductModel());
        jsonObject.put("room_id",netRock.getDeviceSerial());
        jsonObject.put("phonenumber",netRock.getMobile());
        jsonObject.put("password_id",netRock.getPassword());
        jsonObject.put("password",netRock.getCardData());
        jsonObject.put("permission_begin",DateHelper.parseSecond(netRock.getStartTime()));
        jsonObject.put("permission_end",DateHelper.parseSecond(netRock.getEndTime()));
        String result = HttpUtil.yundingPost(netRock.getDomain() + RockEnum.YUND_ADD_PASSWORD.getMsg(), jsonObject.toString());

        Map jsonMap = JsonHelper.toMap(result);

        if(null == jsonMap){

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        if(null != jsonMap && jsonMap.get("ErrNo").equals("0")){


            return netRock.getCardData();
        }else if(null != jsonMap && !jsonMap.get("ErrNo").equals("0")){

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + jsonMap.get("ErrMsg").toString());
        }

        return "";
    }



    /**
     *remark: 删除
     *@author:chenj
     *@date: 2019/12/4
     *@return java.lang.String
     */
    public static void delete(NetRock netRock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token",netRock.getToken());
        jsonObject.put("home_id",netRock.getProductModel());
        jsonObject.put("room_id",netRock.getDeviceSerial());
        jsonObject.put("password_id",netRock.getPassword());

        String result = HttpUtil.yundingPost(netRock.getDomain() + RockEnum.YUND_DELETE_PASSWORD.getMsg(), jsonObject.toString());

        Map jsonMap = JsonHelper.toMap(result);

        if(null == jsonMap){

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        if(null != jsonMap && jsonMap.get("ErrNo").equals("0")){



        }else if(null != jsonMap && !jsonMap.get("ErrNo").equals("0")){

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + jsonMap.get("ErrMsg").toString());
        }

    }








    public static void main(String[] args) throws Exception {
        NetRock netRock = new NetRock();
        YunDingRockImpl yunDingRock = new YunDingRockImpl();

        yunDingRock.getToken(netRock);

    }


}
