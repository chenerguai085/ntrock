package com.rh.netlock.handle;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.*;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.util.HttpUtil;
import com.rh.netlock.util.RockCommonUtil;
import com.rhcj.commons.DateHelper;
import com.rhcj.commons.JsonHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/4
 */
public  class YunDingLockImpl {


    /**
     *remark: 获取token信息
     *@author:chenj
     *@date: 2019/12/4
     *@return com.rh.netrock.entity.Token
     */
    public static Token getToken(User user) throws Exception {
        RockCommonUtil.paramsVerify(user,"userName","password");

        if(StringUtils.isBlank(user.getDomain())){

            user.setDomain(LockEnum.YUNDING_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("client_id", user.getUserName());
        jsonObject.put("client_secret", user.getPassword());

        String result = HttpUtil.yundingPost(user.getDomain() + LockEnum.YUND_TOKEN_API.getMsg(), jsonObject.toString());

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

    public static String add(NetLock netLock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (!netLock.getLockData().contains(","))
            throw new Exception("未传入正确格式开门数据");

        //与前端调用返回个约定  lockData 字段 是密码+ "," + 手机号拼接而成
        String[] dataArr = netLock.getLockData().split(",");

        jsonObject.put("access_token", netLock.getToken());
        jsonObject.put("home_id", netLock.getLockOption());
        jsonObject.put("room_id", netLock.getLockId());
        jsonObject.put("phonenumber", dataArr[1]);
        jsonObject.put("is_default",0);
        jsonObject.put("password", dataArr[0]);
        jsonObject.put("name","在线密码" + dataArr[0]);
        jsonObject.put("permission_begin",DateHelper.parseSecond(netLock.getStartTime()));
        jsonObject.put("permission_end",DateHelper.parseSecond(netLock.getEndTime()));
        String result = HttpUtil.yundingPost(netLock.getDomain() + LockEnum.YUND_ADD_PASSWORD.getMsg(), jsonObject.toString());

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
    public static String update(UpdateLock updateLock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (!updateLock.getLockData().contains(","))
            throw new Exception("未传入正确格式开门数据");

        //与前端调用返回个约定  lockData 字段 是密码+ "," + 手机号拼接而成
        String[] dataArr = updateLock.getLockData().split(",");

        jsonObject.put("access_token", updateLock.getToken());
        jsonObject.put("home_id", updateLock.getLockOption());
        jsonObject.put("room_id", updateLock.getLockId());
        jsonObject.put("phonenumber", dataArr[1]);
        jsonObject.put("password_id", updateLock.getLockData());
        jsonObject.put("password", dataArr[0]);
        jsonObject.put("permission_begin",DateHelper.parseSecond(updateLock.getStartTime()));
        jsonObject.put("permission_end",DateHelper.parseSecond(updateLock.getEndTime()));
        String result = HttpUtil.yundingPost(updateLock.getDomain() + LockEnum.YUND_ADD_PASSWORD.getMsg(), jsonObject.toString());

        Map jsonMap = JsonHelper.toMap(result);

        if(null == jsonMap){

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        if(null != jsonMap && jsonMap.get("ErrNo").equals("0")){


            return updateLock.getLockData();
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
    public static void delete(DelLock delLock) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access_token", delLock.getToken());
        jsonObject.put("home_id", delLock.getLockOption());
        jsonObject.put("room_id", delLock.getLockId());
        jsonObject.put("password_id", delLock.getLockData());

        String result = HttpUtil.yundingPost(delLock.getDomain() + LockEnum.YUND_DELETE_PASSWORD.getMsg(), jsonObject.toString());

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


    }


}
