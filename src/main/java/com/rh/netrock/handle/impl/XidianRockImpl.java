package com.rh.netrock.handle.impl;

import com.alibaba.fastjson.JSONObject;
import com.rh.netrock.entity.NetRock;
import com.rh.netrock.entity.RockReqResult;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.RegEnum;
import com.rh.netrock.enums.RockEnum;
import com.rh.netrock.handle.AbstractHandle;
import com.rh.netrock.util.RockCommonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class XidianRockImpl extends AbstractHandle {

    @Override
    public String add(NetRock netRock) {
        paramsVersign(netRock);
        JSONObject jsonObject = new JSONObject();

        String result = "";
        if (!netRock.getStartTime().matches(RegEnum.TIME_REG.getMsg()) || !netRock.getEndTime().matches(RegEnum.TIME_REG.getMsg()))
            throw new RuntimeException("输入时间格式有误");

        jsonObject.put("deviceSerial", netRock.getDeviceSerial());
        jsonObject.put("productModel", netRock.getProductModel());

        jsonObject.put("startTime", netRock.getStartTime());
        jsonObject.put("endTime", netRock.getEndTime());
        if (3 == netRock.getOpenTypeEnum().getCode()) {
            if (!netRock.getCardData().matches(RegEnum.PWD_REG.getMsg()))
                throw new RuntimeException("输入密码格式有误");

            jsonObject.put("pwd", netRock.getCardData());

            result = buildReqAdd(jsonObject, netRock, netRock.getDomain() + RockEnum.ADD_PWD_API.getMsg());

        }

        if (1 == netRock.getOpenTypeEnum().getCode()) {
            jsonObject.put("cardNo", netRock.getCardData());

            result = buildReqAdd(jsonObject, netRock, netRock.getDomain() + RockEnum.ADD_CARD_API.getMsg());
        }
        return result;
    }


    @Override
    public void delete(NetRock netRock) {
        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isBlank(netRock.getDeviceSerial()) || StringUtils.isBlank(netRock.getProductModel())
          || StringUtils.isBlank(netRock.getCardData()))
            throw new RuntimeException(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());

        jsonObject.put("deviceSerial", netRock.getDeviceSerial());
        jsonObject.put("productModel", netRock.getProductModel());

        if (3 == netRock.getOpenTypeEnum().getCode()) {
            if (!netRock.getCardData().matches(RegEnum.PWD_REG.getMsg()))
                throw new RuntimeException("输入密码格式有误");

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

    @Override
    public String update(NetRock netRock) {

        //校验参数
        paramsVersign(netRock);
        JSONObject jsonObject = new JSONObject();

        //公共字段
        jsonObject.put("deviceSerial", netRock.getDeviceSerial());
        jsonObject.put("productModel", netRock.getProductModel());
        jsonObject.put("startTime", netRock.getStartTime());
        jsonObject.put("endTime", netRock.getEndTime());

        if (3 == netRock.getOpenTypeEnum().getCode()) {

            if (StringUtils.isBlank(netRock.getOldCardData())) {
                throw new RuntimeException("旧密码不能为空");
            }

            if (!netRock.getCardData().matches(RegEnum.PWD_REG.getMsg()) || !netRock.getOldCardData().matches(RegEnum.PWD_REG.getMsg()))
                throw new RuntimeException("输入密码格式有误");

            jsonObject.put("newPwd", netRock.getCardData());
            jsonObject.put("oldPwd", netRock.getOldCardData());

            RockReqResult rockReqResult = RockCommonUtil.rockCommonReply(netRock.getDomain() + RockEnum.UPDATE_PWD_API.getMsg(), jsonObject.toString());

            if (rockReqResult.getSuccess()) {

                return netRock.getCardData();
            }

        } else if (1 == netRock.getOpenTypeEnum().getCode()) {
            jsonObject.put("cardNo", netRock.getCardData());

            RockReqResult rockReqResult = RockCommonUtil.rockCommonReply(netRock.getDomain() + RockEnum.UPDATE_CARD_API.getMsg(), jsonObject.toString());

            if (rockReqResult.getSuccess()) {

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
    private void buildReqDelete(JSONObject jsonObject, String url) {

        RockCommonUtil.rockCommonReply(url, jsonObject.toString());
    }


    /**
     * remark: 增加开门请求
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    private String buildReqAdd(JSONObject jsonObject, NetRock netRock, String url) {

        RockReqResult rockReqResult = RockCommonUtil.rockCommonReply(url, jsonObject.toString());

        if (rockReqResult.getSuccess()) {

            return netRock.getCardData();
        }

        throw new RuntimeException(ErrMsgEnum.ERR.getMsg());
    }

    /**
     * remark: 参数验证
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private void paramsVersign(NetRock netRock) {
        if (StringUtils.isBlank(netRock.getDeviceSerial()) || StringUtils.isBlank(netRock.getProductModel())
                || StringUtils.isBlank(netRock.getCardData()) || StringUtils.isBlank(netRock.getStartTime()) ||
                StringUtils.isBlank(netRock.getEndTime()))
            throw new RuntimeException(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
    }


}
