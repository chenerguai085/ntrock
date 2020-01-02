package com.rh.netlock.handle.impl;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.util.DateHelper;
import com.rh.netlock.util.HttpUtil;
import com.rh.netlock.util.JsonHelper;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class XidianLockReqApiImpl {
    static final String datePattern = "yyyy-MM-dd HH:mm:ss";
    static final String successCode = "100";

    /**
     * remark: 新增卡接口
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */

    public static String addCard(NetLock netLock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        buildCommonAddJson(jsonObject,netLock);
        jsonObject.put("cardNo", netLock.getLockData());
        return commonReply(jsonObject.toString(), buildReqHeaders(), netLock.getDomain() + LockEnum.ADD_CARD_API.getMsg());
    }

    /**
     * remark: 新增密码接口
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */
    public static String addPwd(NetLock netLock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        buildCommonAddJson(jsonObject,netLock);
        jsonObject.put("pwd", netLock.getLockData());

        return commonReply(jsonObject.toString(), buildReqHeaders(), netLock.getDomain() + LockEnum.ADD_PWD_API.getMsg());
    }

    /**
     *remark: 封装add请求公共json
     *@author:chenj
     *@date: 2019/12/18
     *@return com.alibaba.fastjson.JSONObject
     */
    private static JSONObject buildCommonAddJson(JSONObject jsonObject,NetLock netLock){
        jsonObject.put("deviceSerial", netLock.getLockId());
        jsonObject.put("productModel", netLock.getLockOption());
        jsonObject.put("startTime", DateHelper.formatDate(netLock.getStartTime(), datePattern));
        jsonObject.put("endTime", DateHelper.formatDate(netLock.getEndTime(), datePattern));

        return jsonObject;
    }

    /**
     * remark:删除卡接口
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */
    public static String deleteCard(DelLock delLock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        buildCommonDelJson(jsonObject,delLock);

        jsonObject.put("cardNo", delLock.getLockData());

        return commonReply(jsonObject.toString(), buildReqHeaders(), delLock.getDomain() + LockEnum.DELETE_CARD_API.getMsg());
    }

    /**
     * remark:
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */
    public static String deletePwd(DelLock delLock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        buildCommonDelJson(jsonObject,delLock);
        jsonObject.put("pwd", delLock.getLockData());

        return commonReply(jsonObject.toString(), buildReqHeaders(), delLock.getDomain() + LockEnum.DELETE_PWD_API.getMsg());
    }

    private static JSONObject buildCommonDelJson(JSONObject jsonObject,DelLock delLock){
        jsonObject.put("deviceSerial", delLock.getLockId());
        jsonObject.put("productModel", delLock.getLockOption());

        return jsonObject;
    }


    /**
     * remark:
     *
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author:chenj 封装悉点请求header数据
     * @date: 2019/12/12
     */
    public static Map<String, String> buildReqHeaders() {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json; charset=UTF-8");
        headersMap.put("code", "hzrw-001");

        return headersMap;
    }


    /**
     *remark: 更新卡
     *@author:chenj
     *@date: 2019/12/18
     *@return java.lang.String
     */

    public static String updateCard(UpdateLock updateLock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        buildCommonUpdateJson(jsonObject,updateLock);

        jsonObject.put("cardNo",updateLock.getLockData());
        String result = "";
        //只更新卡权限时间
        result = HttpUtil.doJsonPost(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.UPDATE_CARD_API.getMsg());
        if (!JsonHelper.isJson(result)) {

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + result);
        }

        JSONObject addResultJson = JSONObject.parseObject(result);

        if (!addResultJson.getString("code").equals(successCode)) {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + addResultJson.get("message"));
        }

        return result;
    }

    /**
     *remark: 删除旧数据 新加新数据
     *@author:chenj
     *@date: 2019/12/18
     *@return java.lang.String
     */
    public static String delAddCard(UpdateLock updateLock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        buildCommonUpdateJson(jsonObject,updateLock);
        jsonObject.put("cardNo", updateLock.getOldLockData());
        //删除老的一张卡
        String result = HttpUtil.doJsonPost(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.DELETE_CARD_API.getMsg());
        if (!JsonHelper.isJson(result))
            throw new Exception("请求删除接口时: " + ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + result);

        JSONObject delResultJson = JSONObject.parseObject(result);

        if (!delResultJson.getString("code").equals(successCode))
            throw new Exception("请求删除接口返回不成功");

        //新增新卡
        jsonObject.put("cardNo", updateLock.getLockData());
        //删除成功  调用新增接口
        result = HttpUtil.doJsonPost(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.ADD_CARD_API.getMsg());
        if (!JsonHelper.isJson(result))
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + result);

        JSONObject addResultJson = JSONObject.parseObject(result);

        if (!addResultJson.getString("code").equals(successCode))
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + addResultJson.get("message"));

        return result;
    }


    /**
     *remark: 更新密码
     *@author:chenj
     *@date: 2019/12/18
     *@return java.lang.String
     */
    public static String updatePwd(UpdateLock updateLock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        //公共字段
        buildCommonUpdateJson(jsonObject,updateLock);
        jsonObject.put("newPwd", updateLock.getLockData());
        jsonObject.put("oldPwd", updateLock.getOldLockData());

        //密码时间都会更新
        String result = HttpUtil.doJsonPost(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.UPDATE_PWD_API.getMsg());
        if (!JsonHelper.isJson(result)) {

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + result);
        }

        JSONObject addResultJson = JSONObject.parseObject(result);

        if (!addResultJson.getString("code").equals(successCode)) {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + addResultJson.get("message"));
        }

        return result;
    }


    /**
     *remark: 先删除后新增
     *@author:chenj
     *@date: 2019/12/18
     *@return java.lang.String
     */
    public static String delAddPwd(UpdateLock updateLock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        buildCommonUpdateJson(jsonObject,updateLock);
        jsonObject.put("pwd", updateLock.getLockData());

        //只更新最后过期时间  先删除后添加
        String result = HttpUtil.doJsonPost(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.DELETE_PWD_API.getMsg());
        if (!JsonHelper.isJson(result)) {

            throw new Exception("请求删除接口时: " + ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + result);
        }

        JSONObject delResultJson = JSONObject.parseObject(result);

        if (!delResultJson.getString("code").equals(successCode)) {
            throw new Exception("请求删除接口返回不成功");
        }

        //删除成功  调用新增接口
        result = HttpUtil.doJsonPost(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.ADD_PWD_API.getMsg());
        if (!JsonHelper.isJson(result)) {

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + result);
        }

        JSONObject addResultJson = JSONObject.parseObject(result);

        if (!addResultJson.getString("code").equals(successCode)) {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + addResultJson.get("message"));
        }

        return result;

    }

    /**
     *remark: 封装更新公共json串
     *@author:chenj
     *@date: 2019/12/18
     *@return com.alibaba.fastjson.JSONObject
     */
    private  static JSONObject buildCommonUpdateJson(JSONObject jsonObject,UpdateLock updateLock){
        jsonObject.put("deviceSerial", updateLock.getLockId());
        jsonObject.put("productModel", updateLock.getLockOption());
        jsonObject.put("startTime", DateHelper.formatDate(new Date(), datePattern));
        jsonObject.put("endTime", DateHelper.formatDate(updateLock.getEndTime(), datePattern));


        return jsonObject;
    }

    /**
     * remark: 返回信息
     *
     * @return
     * @author:chenj
     * @date: 2019/11/14
     */
    private static String commonReply(String json, Map<String, String> headersMap, String url) throws Exception {

        String result = HttpUtil.doJsonPost(json, headersMap, url);

        if (!JsonHelper.isJson(result)) {

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + result);
        }

        JSONObject resultJson = JSONObject.parseObject(result);

        if (!resultJson.getString("code").equals(successCode)) {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultJson.get("message"));
        }

        return result;
    }
}
