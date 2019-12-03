package com.rh.netrock.handle.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rh.netrock.entity.NetRock;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.RockEnum;
import com.rh.netrock.handle.AbstractHandle;
import com.rh.netrock.util.HttpUtil;
import com.rhcj.commons.JsonHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/3
 */
public class XieZFaceRockImpl extends AbstractHandle {

    @Override
    public String add(NetRock netRock) throws Exception {

        if(StringUtils.isBlank(netRock.getProductModel()) || StringUtils.isBlank(netRock.getDeviceSerial())
            || StringUtils.isBlank(netRock.getName()) || StringUtils.isBlank(netRock.getIdCardNo()) ||
                StringUtils.isBlank(netRock.getMobile()) || StringUtils.isBlank(netRock.getCardData())){
            throw  new RuntimeException(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }
        //封装成json数据
        String reqJson = buildAddFaceJson(netRock);

        if (1 == ucamRockCommonReply(netRock.getDomain() + RockEnum.FACE_ADD_API.getMsg(), reqJson, new Date(), netRock.getDomain())) {

            return netRock.getIdCardNo();
        }

        throw  new RuntimeException(ErrMsgEnum.ERR.getMsg());
    }


    /**
     * remark: 构建人脸录入接口的json数据
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/21
     */
    private String buildAddFaceJson(NetRock netRock) {
        JSONObject jsonObject = new JSONObject();

        //设备信息
        jsonObject.put("PmsCode", RockEnum.PMS_CODE.getMsg());
        jsonObject.put("HotelId", netRock.getProductModel());
        jsonObject.put("RoomNo", netRock.getDeviceSerial());
        jsonObject.put("TimeStamp", new Date().getTime());

        //人员信息
        JSONObject infoJson = new JSONObject();
        infoJson.put("Name", netRock.getName());
        infoJson.put("CardNo", netRock.getIdCardNo());
        infoJson.put("Mobile", netRock.getMobile());
        infoJson.put("Base64Img", netRock.getCardData());
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
    private static Integer ucamRockCommonReply(String url, String jsonMap, Date now, String domain) throws Exception {
        //发送请求
        String respJson = HttpUtil.postXzAddFace(url, jsonMap, now, domain);

        Map<String, Object> map = JsonHelper.toMap(respJson);
        if (null != map && map.get("Result").toString().equals("true")) {

            //成功
            return 1;
        }

        //失败
        return 0;
    }

}
