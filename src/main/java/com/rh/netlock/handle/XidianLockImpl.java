package com.rh.netlock.handle;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.enums.OpenTypeEnum;
import com.rh.netlock.util.DateHelper;
import com.rh.netlock.util.HttpUtil;
import com.rh.netlock.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class XidianLockImpl {
    static final String datePattern = "yyyy-MM-dd HH:mm:ss";
    static final String successCode = "100";

    public static String add(NetLock netLock) throws Exception {
        paramsVersign(netLock);

        if (StringUtils.isBlank(netLock.getDomain())) {
            netLock.setDomain(LockEnum.XIDIAN_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();

        String result = "";
        jsonObject.put("deviceSerial", netLock.getLockId());
        jsonObject.put("productModel", netLock.getLockOption());
        jsonObject.put("startTime", DateHelper.formatDate(netLock.getStartTime(), datePattern));
        jsonObject.put("endTime", DateHelper.formatDate(netLock.getEndTime(), datePattern));

        OpenTypeEnum openTypeEnum = netLock.getOpenTypeEnum();

        switch (openTypeEnum) {
            case ICCARD:
                jsonObject.put("cardNo", netLock.getLockData());
                result = commonReply(jsonObject.toString(), buildReqHeaders(), netLock.getDomain() + LockEnum.ADD_CARD_API.getMsg());
                return result;
            case PASSWORD:
                jsonObject.put("pwd", netLock.getLockData());
                result = commonReply(jsonObject.toString(), buildReqHeaders(), netLock.getDomain() + LockEnum.ADD_PWD_API.getMsg());
                return result;
            default:
                break;
        }

        return result;
    }


    public static String delete(DelLock delLock) throws Exception {
        JSONObject jsonObject = new JSONObject();

        if (StringUtils.isBlank(delLock.getLockId()) || StringUtils.isBlank(delLock.getLockOption())
                || StringUtils.isBlank(delLock.getLockData()) || null == delLock.getOpenTypeEnum())
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());

        if (StringUtils.isBlank(delLock.getDomain())) {
            delLock.setDomain(LockEnum.XIDIAN_DOMAIN.getMsg());
        }

        jsonObject.put("deviceSerial", delLock.getLockId());
        jsonObject.put("productModel", delLock.getLockOption());

        String result = "";
        OpenTypeEnum openTypeEnum = delLock.getOpenTypeEnum();

        switch (openTypeEnum) {
            case ICCARD:
                jsonObject.put("cardNo", delLock.getLockData());
                result = commonReply(jsonObject.toString(), buildReqHeaders(), delLock.getDomain() + LockEnum.DELETE_CARD_API.getMsg());
                return result;
            case PASSWORD:
                jsonObject.put("pwd", delLock.getLockData());
                result = commonReply(jsonObject.toString(), buildReqHeaders(), delLock.getDomain() + LockEnum.DELETE_PWD_API.getMsg());
                return result;
            default:
                break;
        }
        return result;
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
     * remark: 更新
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String update(UpdateLock updateLock) throws Exception {
        //校验参数
        paramsVersign(updateLock);

        if (StringUtils.isBlank(updateLock.getDomain())) {
            updateLock.setDomain(LockEnum.XIDIAN_DOMAIN.getMsg());
        }

        JSONObject jsonObject = new JSONObject();

        //公共字段
        jsonObject.put("deviceSerial", updateLock.getLockId());
        jsonObject.put("productModel", updateLock.getLockOption());
        jsonObject.put("startTime", DateHelper.formatDate(updateLock.getStartTime(), datePattern));
        jsonObject.put("endTime", DateHelper.formatDate(updateLock.getEndTime(), datePattern));

        String result = "";
        OpenTypeEnum openTypeEnum = updateLock.getOpenTypeEnum();
        switch (openTypeEnum) {
            case ICCARD:
                jsonObject.put("cardNo", updateLock.getLockData());

                result = commonReply(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.UPDATE_CARD_API.getMsg());
                return result;
            case PASSWORD:
                if (StringUtils.isBlank(updateLock.getOldLockData())) {
                    throw new Exception(ErrMsgEnum.ERR_MSG_OLDPWD_ISNULL.getMsg());
                }
                jsonObject.put("newPwd", updateLock.getLockData());
                jsonObject.put("oldPwd", updateLock.getOldLockData());

                result = commonReply(jsonObject.toString(), buildReqHeaders(), updateLock.getDomain() + LockEnum.UPDATE_PWD_API.getMsg());
                return result;
            default:
                break;
        }
        return result;
    }

    /**
     * remark: 返回信息
     *
     * @return
     * @author:chenj
     * @date: 2019/11/14
     */
    public static String commonReply(String json, Map<String, String> headersMap, String url) throws Exception {

        String result = HttpUtil.doJsonPost(json, headersMap, url);

        if (!JsonHelper.isJson(result)) {

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject resultJson = JSONObject.parseObject(result);

        if (!resultJson.getString("code").equals(successCode)) {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultJson.get("message"));
        }

        return result;
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
                || StringUtils.isBlank(netLock.getLockData()) || null == netLock.getStartTime() ||
                null == netLock.getEndTime() || null == netLock.getOpenTypeEnum())
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
    }


}
