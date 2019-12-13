package com.rh.netlock.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author cj
 * @remark Json工具类
 */
public final class JsonHelper {

    /**
     *remark:判断是否是json字符串
     *@author:chenj
     *@date: 2019/11/20
     *@return java.lang.Boolean
     */
    public static Boolean isJson(String json) {
        try {
            if (StringUtils.isBlank(json))
                return false;

            JSONObject jsonStr = JSONObject.parseObject(json);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

}
