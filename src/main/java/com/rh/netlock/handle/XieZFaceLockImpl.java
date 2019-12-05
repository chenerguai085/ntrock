package com.rh.netlock.handle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.Token;
import com.rh.netlock.entity.User;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.util.HttpUtil;
import com.rhcj.commons.DateHelper;
import com.rhcj.commons.JsonHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/3
 */
public class XieZFaceLockImpl {

    final static String tokenSpReg = ",";

    public static  String add(NetLock netLock) throws Exception {

        if(StringUtils.isBlank(netLock.getLockOption()) || StringUtils.isBlank(netLock.getLockId())
            ||  StringUtils.isBlank(netLock.getFileStr()) ||
           StringUtils.isBlank(netLock.getToken())){
            throw  new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        if (StringUtils.isBlank(netLock.getDomain())){
            netLock.setDomain(LockEnum.XIEZHU_DOMAIN.getMsg());
        }
        //封装成json数据
        String reqJson = buildAddFaceJson(netLock);

        String[] tokenArr = netLock.getToken().split(tokenSpReg);

        if (1 == ucamRockAddFace(netLock.getDomain() + LockEnum.FACE_ADD_API.getMsg(), reqJson, new Date(), netLock.getDomain()
            ,tokenArr[0] , tokenArr[1])) {

            return netLock.getLockData();
        }

        throw  new Exception(ErrMsgEnum.ERR.getMsg());
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
//        infoJson.put("CardNo", netRock.getIdCardNo());
//        infoJson.put("Mobile", netRock.getMobile());

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
    private static Integer ucamRockAddFace(String url, String jsonMap, Date now, String domain,
                                           String tokenType,String accessToken) throws Exception {
        //发送请求
        String respJson = HttpUtil.postXzAddFace(url, jsonMap, now, domain,tokenType,accessToken);

        Map<String, Object> map = JsonHelper.toMap(respJson);
        if (null != map && map.get("Result").toString().equals("true")) {

            //成功
            return 1;
        }

        //失败
        return 0;
    }

    public static Token getToken(User user) throws Exception {
        Map<String, Object> getTokenMap = new HashMap<>();


        if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword()))
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        getTokenMap.put("grant_type", "password");
        getTokenMap.put("username", user.getUserName());
        getTokenMap.put("password", user.getPassword());


        if (StringUtils.isBlank(user.getDomain())){
            user.setDomain(LockEnum.XIEZHU_DOMAIN.getMsg());
        }

        String result = HttpUtil.postGetToken(user.getDomain() + LockEnum.GET_TOKEN_API.getMsg(), getTokenMap);

        if (!JsonHelper.isJson(result)) {
            throw new Exception("获取tokne信息异常,返回token信息非json格式字符串");
        }

        Map jsonMap = JsonHelper.toMap(result);
        String access_token = jsonMap.get("access_token").toString();
        String token_type = jsonMap.get("token_type").toString();

        String expiresDateStr = jsonMap.get(".expires").toString();

        Date expiresDate = DateHelper.gmtToDate(expiresDateStr);
        if (null == expiresDate){

            throw new Exception("获取tokne信息异常,返回日期格式非约定格式");
        }

        Token token = new Token();

        token.setExpiresDate(expiresDate);
        token.setAccessToken(token_type + "," + access_token);

        return token;
    }
}
