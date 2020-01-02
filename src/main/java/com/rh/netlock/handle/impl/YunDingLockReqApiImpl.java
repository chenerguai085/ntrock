package com.rh.netlock.handle.impl;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.*;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.util.*;
import org.apache.commons.lang3.StringUtils;
import java.util.Date;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/4
 */
public class YunDingLockReqApiImpl {

    static final int thousand = 1000;

    /**
     * remark: 获取token信息
     *
     * @return com.rh.netrock.entity.Token
     * @author:chenj
     * @date: 2019/12/4
     */
    public static Token getToken(User user) throws Exception {
        ParamsVerifyUtil.paramsVerify(user, "userName", "password");

        if (StringUtils.isBlank(user.getDomain())) {

            user.setDomain(LockEnum.YUNDING_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("client_id", user.getUserName());
        jsonObject.put("client_secret", user.getPassword());

        String result = HttpUtil.yundingPost(user.getDomain() + LockEnum.YUND_TOKEN_API.getMsg(), jsonObject.toString());

        if (!JsonHelper.isJson(result)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject respJson = JSONObject.parseObject(result);

        Token token = null;

        if (respJson.get("ErrNo").toString().equals("0")) {
            token = new Token();
            token.setAccessToken(respJson.get("access_token").toString());
            Date expiresDate = DateHelper.secondToDate(Long.valueOf(respJson.get("expires_time").toString()));

            token.setExpiresDate(expiresDate);
        } else if (null != respJson && !respJson.get("ErrNo").toString().equals("0")) {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + respJson.get("ErrMsg").toString());
        }

        return token;
    }

    /**
     * remark: 添加密码
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/4
     */
    public static String add(NetLock netLock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (!netLock.getLockData().contains(","))
            throw new Exception("未传入正确格式开门数据");

        if (StringUtils.isBlank(netLock.getDomain())) {
            netLock.setDomain(LockEnum.YUNDING_DOMAIN.getMsg());
        }

        //与前端调用返回个约定  lockData 字段 是密码+ "," + 手机号拼接而成
        String[] dataArr = netLock.getLockData().split(",");

        jsonObject.put("access_token", netLock.getToken());
        jsonObject.put("home_id", netLock.getLockOption());
        jsonObject.put("uuid", netLock.getLockId());
        jsonObject.put("phonenumber", dataArr[1]);
        jsonObject.put("is_default", 0);
        jsonObject.put("password", dataArr[0]);
        jsonObject.put("name", "在线密码" + dataArr[0]);
        jsonObject.put("permission_begin", netLock.getStartTime().getTime() / thousand);
        jsonObject.put("permission_end", netLock.getEndTime().getTime() / thousand);

        String result = HttpUtil.yundingPost(netLock.getDomain() + LockEnum.YUND_ADD_PASSWORD.getMsg(), jsonObject.toString());

        if (!JsonHelper.isJson(result)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject respJson = JSONObject.parseObject(result);

        if (respJson.get("ErrNo").toString().equals("0")) {

            String id = respJson.get("id").toString();
            String serviceid = respJson.get("serviceid").toString();


            System.out.println("id ,serviceid " + id + "," + serviceid);
            return result;
        } else {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + respJson.get("ErrMsg").toString());
        }

    }

    /**
     * remark:
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/4
     */
    public static String update(UpdateLock updateLock) throws Exception {
        ParamsVerifyUtil.paramsVerify(updateLock, "oldLockData", "lockId", "lockOption", "lockData");

        JSONObject jsonObject = new JSONObject();

        if (!updateLock.getLockData().contains(","))
            throw new Exception("未传入正确格式开门数据");

        if (StringUtils.isBlank(updateLock.getDomain())) {

            updateLock.setDomain(LockEnum.YUNDING_DOMAIN.getMsg());
        }
        //与前端调用返回个约定  lockData 字段 是密码+ "," + 手机号拼接而成
        String[] dataArr = updateLock.getLockData().split(",");

        jsonObject.put("access_token", updateLock.getToken());
        jsonObject.put("home_id", updateLock.getLockOption());
        jsonObject.put("uuid", updateLock.getLockId());
        jsonObject.put("phonenumber", dataArr[1]);
        jsonObject.put("password_id", updateLock.getOldLockData());
        jsonObject.put("password", dataArr[0]);
        jsonObject.put("permission_begin", updateLock.getStartTime().getTime() / thousand);
        jsonObject.put("permission_end", updateLock.getEndTime().getTime() / thousand);


        String result = HttpUtil.yundingPost(updateLock.getDomain() + LockEnum.YUND_UPDATE_PASSWORD.getMsg(), jsonObject.toString());

        if (!JsonHelper.isJson(result)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject respJson = JSONObject.parseObject(result);

        if (respJson.get("ErrNo").toString().equals("0")) {

            String serviceid = respJson.get("serviceid").toString();


            System.out.println("serviceid " + serviceid);

            return result;
        } else {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + respJson.get("ErrMsg").toString());
        }
    }

    /**
     * remark: 删除
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/4
     */
    public static String delete(DelLock delLock) throws Exception {
        ParamsVerifyUtil.paramsVerify(delLock, "lockId", "lockOption", "lockData", "token");

        if (StringUtils.isBlank(delLock.getDomain())) {

            delLock.setDomain(LockEnum.YUNDING_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", delLock.getToken());
        jsonObject.put("home_id", delLock.getLockOption());
        jsonObject.put("uuid", delLock.getLockId());
        jsonObject.put("password_id", delLock.getLockData());

        String result = HttpUtil.yundingPost(delLock.getDomain() + LockEnum.YUND_DELETE_PASSWORD.getMsg(), jsonObject.toString());


        if (!JsonHelper.isJson(result)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject respJson = JSONObject.parseObject(result);

        if (respJson.get("ErrNo").toString().equals("0")) {

            return result;
        } else {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + respJson.get("ErrMsg").toString());
        }

    }
}
