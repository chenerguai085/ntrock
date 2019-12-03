package com.rh.netrock.util;

import com.alibaba.fastjson.JSONObject;
import com.rh.netrock.enums.ErrMsgEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/18
 */
public class RockCommonUtil {



    /**
     * remark: 返回信息
     *
     * @return
     * @author:chenj
     * @date: 2019/11/14
     */
    public static Integer  rockCommonReply(String url, String json) throws Exception {
        System.out.println(url);
        String result = HttpUtil.doJsonPost(url, json);

        if (!isJson(result) || StringUtils.isBlank(result)) {

            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject resultJson = JSONObject.parseObject(result);

        if (!resultJson.getString("code").equals("100")) {

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultJson.get("message"));
        }

        return 1;
    }

    /**
     * remark: 判断字符串是否为json
     *
     * @return boolean
     * @author:chenj
     * @date: 2019/11/18
     */
    public static boolean isJson(String json) {
        try {
            JSONObject jsonStr = JSONObject.parseObject(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
