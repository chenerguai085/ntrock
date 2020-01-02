package com.rh.netlock.util;

import com.rh.netlock.enums.ErrMsgEnum;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/13
 */
public class ParamsVerifyUtil {

    /**
     * remark: 全局校验参数
     *
     * @return void
     * @author:chenj
     * @date: 2019/12/4
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

            if (null == field.get(obj)) {

                throw new Exception(fieldName + ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
            }

        }
    }
}
