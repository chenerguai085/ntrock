package com.rh.netlock.handle.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.Token;
import com.rh.netlock.entity.User;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.util.DateHelper;
import com.rh.netlock.util.HttpUtil;
import com.rh.netlock.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/3
 */
public class XieZFaceLockReqApiImpl {

    final static String tokenSpReg = ",";

    public static String add(NetLock netLock) throws Exception {

        if (StringUtils.isBlank(netLock.getLockOption()) || StringUtils.isBlank(netLock.getLockId())
                || StringUtils.isBlank(netLock.getFileStr()) ||
                StringUtils.isBlank(netLock.getToken())) {
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        if (StringUtils.isBlank(netLock.getDomain())) {
            netLock.setDomain(LockEnum.XIEZHU_DOMAIN.getMsg());
        }

        String[] tokenArr = netLock.getToken().split(tokenSpReg);


        return ucamRockAddFace(netLock.getDomain() + LockEnum.FACE_ADD_API.getMsg(), buildAddFaceJson(netLock)
                , tokenArr[0], tokenArr[1]);
    }


    /**
     * remark: 构建人脸录入接口的json数据
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/21
     */
    private static String buildAddFaceJson(NetLock netLock) {
        JSONObject jsonObject = new JSONObject();
        //设备信息
        jsonObject.put("PmsCode", LockEnum.PMS_CODE.getMsg());
        jsonObject.put("HotelId", netLock.getLockOption());
        jsonObject.put("RoomNo", netLock.getLockId());
        jsonObject.put("TimeStamp", new Date().getTime());
        //人员信息
        JSONObject infoJson = new JSONObject();

        infoJson.put("Name", netLock.getLockData());
        infoJson.put("Base64Img", netLock.getFileStr());

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(infoJson);

        jsonObject.put("GuestInfo", jsonArray);

        return jsonObject.toString();
    }


    /**
     * remark: 统一请求接口返回数据
     *
     * @return com.rh.xzfacerock.RockReqResult
     * @author:chenj
     * @date: 2019/11/21
     */
    private static String ucamRockAddFace(String url, String json, String tokenType, String accessToken) throws Exception {
        //发送请求
        String respJsonStr = HttpUtil.doJsonPost(json, buildReqHeaders(tokenType, accessToken), url);

        if (!JsonHelper.isJson(respJsonStr)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject respJson = JSONObject.parseObject(respJsonStr);

        if (!respJson.get("Result").toString().equals("true")) {
            //失败
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ": " + respJson.get("Msg").toString());
        }
        //成功
        return respJsonStr;
    }


    /**
     * remark: 携住请求头设置
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author:chenj
     * @date: 2019/12/12
     */
    private static Map<String, String> buildReqHeaders(String tokenType, String accessToken) {
        Map<String, String> heardsMap = new HashMap<>();
        // 设置文件类型:
        heardsMap.put("Content-Type", "application/json; charset=UTF-8");
        heardsMap.put("Charset", "UTF-8");
        heardsMap.put("Authorization", tokenType + " " + accessToken);

        return heardsMap;
    }

    /**
     * remark: 获取token
     *
     * @return com.rh.netlock.entity.Token
     * @author:chenj
     * @date: 2019/12/12
     */
    public static Token getToken(User user) throws Exception {
        Map<String, Object> getTokenMap = new HashMap<>();

        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword()))
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        getTokenMap.put("grant_type", "password");
        getTokenMap.put("username", user.getUserName());
        getTokenMap.put("password", user.getPassword());

        if (StringUtils.isBlank(user.getDomain())) {
            user.setDomain(LockEnum.XIEZHU_DOMAIN.getMsg());
        }

        String respJsonStr = HttpUtil.doParamsPost(getTokenMap, null, user.getDomain() + LockEnum.GET_TOKEN_API.getMsg());

        if (!JsonHelper.isJson(respJsonStr)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject respJson = JSONObject.parseObject(respJsonStr);
        String access_token = respJson.get("access_token").toString();
        String token_type = respJson.get("token_type").toString();

        String expiresDateStr = respJson.get(".expires").toString();

        Date expiresDate = DateHelper.gmtToDate(expiresDateStr);
        if (null == expiresDate) {

            throw new Exception(ErrMsgEnum.ERR_MSG_DATE_FORMAT.getMsg());
        }

        Token token = new Token();

        token.setExpiresDate(expiresDate);
        token.setAccessToken(token_type + "," + access_token);

        return token;
    }
}
