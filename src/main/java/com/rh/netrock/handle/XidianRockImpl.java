package com.rh.netrock.handle;

import com.alibaba.fastjson.JSONObject;
import com.rh.netrock.entity.NetRock;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.RegEnum;
import com.rh.netrock.enums.RockEnum;
import com.rh.netrock.util.RockCommonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class XidianRockImpl{


    public static String add(NetRock netRock) throws Exception {
        paramsVersign(netRock);

        if (StringUtils.isBlank(netRock.getDomain())){
            netRock.setDomain(RockEnum.XIDIAN_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();

        String result = "";
        if (!netRock.getStartTime().matches(RegEnum.TIME_REG.getMsg()) || !netRock.getEndTime().matches(RegEnum.TIME_REG.getMsg()))
            throw new Exception("输入时间格式有误");

        jsonObject.put("deviceSerial", netRock.getDeviceSerial());
        jsonObject.put("productModel", netRock.getProductModel());

        jsonObject.put("startTime", netRock.getStartTime());
        jsonObject.put("endTime", netRock.getEndTime());
        if (3 == netRock.getOpenTypeEnum().getCode()) {
            if (!netRock.getCardData().matches(RegEnum.PWD_REG.getMsg()))
                throw new Exception("输入密码格式有误");

            jsonObject.put("pwd", netRock.getCardData());

            result = buildReqAdd(jsonObject, netRock, netRock.getDomain() + RockEnum.ADD_PWD_API.getMsg());

        }

        if (1 == netRock.getOpenTypeEnum().getCode()) {
            jsonObject.put("cardNo", netRock.getCardData());

            result = buildReqAdd(jsonObject, netRock, netRock.getDomain() + RockEnum.ADD_CARD_API.getMsg());
        }
        return result;
    }


    public static void delete(NetRock netRock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isBlank(netRock.getDomain())){
            netRock.setDomain(RockEnum.XIDIAN_DOMAIN.getMsg());
        }

        if (StringUtils.isBlank(netRock.getDeviceSerial()) || StringUtils.isBlank(netRock.getProductModel())
          || StringUtils.isBlank(netRock.getCardData()))
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());

        jsonObject.put("deviceSerial", netRock.getDeviceSerial());
        jsonObject.put("productModel", netRock.getProductModel());

        if (3 == netRock.getOpenTypeEnum().getCode()) {
            if (!netRock.getCardData().matches(RegEnum.PWD_REG.getMsg()))
                throw new Exception("输入密码格式有误");

            jsonObject.put("pwd", netRock.getCardData());

            buildReqDelete(jsonObject, netRock.getDomain() + RockEnum.DELETE_PWD_API.getMsg());

        }

        if (1 == netRock.getOpenTypeEnum().getCode()) {
            jsonObject.put("cardNo", netRock.getCardData());

            buildReqDelete(jsonObject, netRock.getDomain() + RockEnum.DELETE_CARD_API.getMsg());
        }

    }


    /**
     * remark: 更新
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String update(NetRock netRock) throws Exception {
        //校验参数
        paramsVersign(netRock);

        if (StringUtils.isBlank(netRock.getDomain())){
            netRock.setDomain(RockEnum.XIDIAN_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();

        //公共字段
        jsonObject.put("deviceSerial", netRock.getDeviceSerial());
        jsonObject.put("productModel", netRock.getProductModel());
        jsonObject.put("startTime", netRock.getStartTime());
        jsonObject.put("endTime", netRock.getEndTime());

        if (3 == netRock.getOpenTypeEnum().getCode()) {

            if (StringUtils.isBlank(netRock.getOldCardData())) {
                throw new Exception("旧密码不能为空");
            }

            if (!netRock.getCardData().matches(RegEnum.PWD_REG.getMsg()) || !netRock.getOldCardData().matches(RegEnum.PWD_REG.getMsg()))
                throw new Exception("输入密码格式有误");

            jsonObject.put("newPwd", netRock.getCardData());
            jsonObject.put("oldPwd", netRock.getOldCardData());

            int resp = RockCommonUtil.rockCommonReply(netRock.getDomain() + RockEnum.UPDATE_PWD_API.getMsg(), jsonObject.toString());

            if (1 == resp) {

                return netRock.getCardData();
            }

        } else if (1 == netRock.getOpenTypeEnum().getCode()) {
            jsonObject.put("cardNo", netRock.getCardData());

            int resp = RockCommonUtil.rockCommonReply(netRock.getDomain() + RockEnum.UPDATE_CARD_API.getMsg(), jsonObject.toString());

            if (1==resp) {

                return netRock.getCardData();
            }
        }

        return "";
    }


    /**
     * remark: 封装删除请求
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void buildReqDelete(JSONObject jsonObject, String url) throws Exception {

        RockCommonUtil.rockCommonReply(url, jsonObject.toString());
    }


    /**
     * remark: 增加开门请求
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    private static String buildReqAdd(JSONObject jsonObject, NetRock netRock, String url) throws Exception {

        int resp = RockCommonUtil.rockCommonReply(url, jsonObject.toString());

        if (1 == resp) {

            return netRock.getCardData();
        }

        throw new Exception(ErrMsgEnum.ERR.getMsg());
    }

    /**
     * remark: 参数验证
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void paramsVersign(NetRock netRock) throws Exception {
        if (StringUtils.isBlank(netRock.getDeviceSerial()) || StringUtils.isBlank(netRock.getProductModel())
                || StringUtils.isBlank(netRock.getCardData()) || StringUtils.isBlank(netRock.getStartTime()) ||
                StringUtils.isBlank(netRock.getEndTime()) || null == netRock.getOpenTypeEnum())
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
    }


}
