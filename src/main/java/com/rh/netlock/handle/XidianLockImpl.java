package com.rh.netlock.handle;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.RegEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.util.RockCommonUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class XidianLockImpl {


    public static String add(NetLock netLock) throws Exception {
        paramsVersign(netLock);

        if (StringUtils.isBlank(netLock.getDomain())){
            netLock.setDomain(LockEnum.XIDIAN_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();

        String result = "";
        if (!netLock.getStartTime().matches(RegEnum.TIME_REG.getMsg()) || !netLock.getEndTime().matches(RegEnum.TIME_REG.getMsg()))
            throw new Exception("输入时间格式有误");

        jsonObject.put("deviceSerial", netLock.getLockId());
        jsonObject.put("productModel", netLock.getLockOption());

        jsonObject.put("startTime", netLock.getStartTime());
        jsonObject.put("endTime", netLock.getEndTime());
        if (3 == netLock.getOpenTypeEnum().getCode()) {
            if (!netLock.getLockData().matches(RegEnum.PWD_REG.getMsg()))
                throw new Exception("输入密码格式有误");

            jsonObject.put("pwd", netLock.getLockData());

            result = buildReqAdd(jsonObject, netLock, netLock.getDomain() + LockEnum.ADD_PWD_API.getMsg());

        }

        if (1 == netLock.getOpenTypeEnum().getCode()) {
            jsonObject.put("cardNo", netLock.getLockData());

            result = buildReqAdd(jsonObject, netLock, netLock.getDomain() + LockEnum.ADD_CARD_API.getMsg());
        }
        return result;
    }


    public static void delete(DelLock delLock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isBlank(delLock.getDomain())){
            delLock.setDomain(LockEnum.XIDIAN_DOMAIN.getMsg());
        }

        if (StringUtils.isBlank(delLock.getLockId()) || StringUtils.isBlank(delLock.getLockOption())
          || StringUtils.isBlank(delLock.getLockData()))
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());

        jsonObject.put("deviceSerial", delLock.getLockId());
        jsonObject.put("productModel", delLock.getLockOption());


        if (delLock.getLockData().length() == 6){
            if (!delLock.getLockData().matches(RegEnum.PWD_REG.getMsg()))
                throw new Exception("输入密码格式有误");

            jsonObject.put("pwd", delLock.getLockData());

            buildReqDelete(jsonObject, delLock.getDomain() + LockEnum.DELETE_PWD_API.getMsg());
        }else{
            jsonObject.put("cardNo", delLock.getLockData());

            buildReqDelete(jsonObject, delLock.getDomain() + LockEnum.DELETE_CARD_API.getMsg());
        }

    }


    /**
     * remark: 更新
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String update(UpdateLock updateLock) throws Exception {
        //校验参数
        paramsVersign(updateLock);

        if (StringUtils.isBlank(updateLock.getDomain())){
            updateLock.setDomain(LockEnum.XIDIAN_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();

        //公共字段
        jsonObject.put("deviceSerial", updateLock.getLockId());
        jsonObject.put("productModel", updateLock.getLockOption());
        jsonObject.put("startTime", updateLock.getStartTime());
        jsonObject.put("endTime", updateLock.getEndTime());

        if (3 == updateLock.getOpenTypeEnum().getCode()) {

            if (StringUtils.isBlank(updateLock.getOldLockData())) {
                throw new Exception("旧密码不能为空");
            }

            if (!updateLock.getLockData().matches(RegEnum.PWD_REG.getMsg()) || !updateLock.getOldLockData().matches(RegEnum.PWD_REG.getMsg()))
                throw new Exception("输入密码格式有误");

            jsonObject.put("newPwd", updateLock.getLockData());
            jsonObject.put("oldPwd", updateLock.getOldLockData());

            int resp = RockCommonUtil.rockCommonReply(updateLock.getDomain() + LockEnum.UPDATE_PWD_API.getMsg(), jsonObject.toString());

            if (1 == resp) {

                return updateLock.getLockData();
            }

        } else if (1 == updateLock.getOpenTypeEnum().getCode()) {
            jsonObject.put("cardNo", updateLock.getLockData());

            int resp = RockCommonUtil.rockCommonReply(updateLock.getDomain() + LockEnum.UPDATE_CARD_API.getMsg(), jsonObject.toString());

            if (1==resp) {

                return updateLock.getLockData();
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
    private static String buildReqAdd(JSONObject jsonObject, NetLock netLock, String url) throws Exception {

        int resp = RockCommonUtil.rockCommonReply(url, jsonObject.toString());

        if (1 == resp) {

            return netLock.getLockData();
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
    private static void paramsVersign(NetLock netLock) throws Exception {
        if (StringUtils.isBlank(netLock.getLockId()) || StringUtils.isBlank(netLock.getLockOption())
                || StringUtils.isBlank(netLock.getLockData()) || StringUtils.isBlank(netLock.getStartTime()) ||
                StringUtils.isBlank(netLock.getEndTime()) || null == netLock.getOpenTypeEnum())
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
    }


}
