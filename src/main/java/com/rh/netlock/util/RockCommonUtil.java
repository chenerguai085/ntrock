package com.rh.netlock.util;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.enums.ErrMsgEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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


    /**
     *remark: 全局校验参数
     *@author:chenj
     *@date: 2019/12/4
     *@return void
     */
    public static void paramsVerify(Object obj, String... args) throws Exception {
        Map<String, Integer> checkColumnMap = new HashMap<>();
        for (String filter : args) {
            checkColumnMap.put(filter, 1);
        }

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (!checkColumnMap.containsKey(fieldName)) {
                continue;
            }

            if(null == field.get(obj)){

                throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
            }

        }


    }



}
