package com.rh.netrock.util;

import com.alibaba.fastjson.JSONObject;
import com.rh.netrock.entity.RockReqResult;
import com.rh.netrock.enums.ErrMsgEnum;
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
     * remark: 验证参数方法
     *
     * @return com.rh.netrock.resp.RockReqResult
     * @author:chenj
     * @date: 2019/11/18
     */
    public static RockReqResult paramsVerify(Object obj, String... args) throws Exception {
        RockReqResult rockReqResult = new RockReqResult();
        StringBuilder sb = new StringBuilder();
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

            String pwdReg = "^\\d{6}$";
            String cardNoReg = "^[0-9]+$";
            String timeReg = "^(\\d{4})(\\-)(\\d{2})(\\-)(\\d{2})(\\s+)(\\d{2})(\\:)(\\d{2})(\\:)(\\d{2})$";

            Boolean b = false;


            try {
                if (fieldName.equals("pwd")) {
                    b = field.get(obj).toString().matches(pwdReg);

                    if (!b) {
                        sb.append("pwd传入正确格式密码 ");
                    }

                }
                if (fieldName.equals("newPwd")) {
                    b = field.get(obj).toString().matches(pwdReg);

                    if (!b) {
                        sb.append("newPwd传入正确格式密码 ");

                    }

                }
                if (fieldName.equals("oldPwd")) {
                    b = field.get(obj).toString().matches(pwdReg);

                    if (!b) {
                        sb.append("oldPwd传入正确格式密码 ");

                    }

                }
                if (fieldName.equals("cardNo")) {
                    b = field.get(obj).toString().matches(cardNoReg);

                    if (!b) {

                        sb.append("cardNo传入正确格式卡号 ");

                    }
                }
                if (fieldName.equals("deviceSerial") && StringUtils.isBlank((CharSequence) field.get(obj))) {
                    sb.append("deviceSerial门锁序列号不能为空 ");

                }
                if (fieldName.equals("productModel") && StringUtils.isBlank((CharSequence) field.get(obj))) {
                    sb.append("productModel门锁型号能为空 ");

                }

                if (fieldName.equals("startTime")) {
                    b = field.get(obj).toString().matches(timeReg);

                    if (!b) {
                        sb.append("startTime传入yyyy-MM-dd HH:mm:ss格式时间串 ");

                    }

                }

                if (fieldName.equals("endTime")) {

                    b = field.get(obj).toString().matches(timeReg);

                    if (!b) {
                        sb.append("endTime传入yyyy-MM-dd HH:mm:ss格式时间串 ");

                    }
                }

            } catch (IllegalAccessException e) {

                rockReqResult.setLocalErrMsg(e.getMessage());
            }
        }

        if (StringUtils.isNotBlank(sb.toString())) {
            rockReqResult.setCode("-100");
            rockReqResult.setSuccess(false);
            rockReqResult.setLocalErrMsg(sb.toString());
        }


        return rockReqResult;
    }

    /**
     *remark: 全局参数校验
     *@author:chenj
     *@date: 2019/12/2
     *@return void
     */
    public static void globalParamsVerify(Object obj, String... args) throws Exception {
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

            String pwdReg = "^\\d{6}$";
            String cardNoReg = "^[0-9]+$";
            String timeReg = "^(\\d{4})(\\-)(\\d{2})(\\-)(\\d{2})(\\s+)(\\d{2})(\\:)(\\d{2})(\\:)(\\d{2})$";

            Boolean b = false;

            try {


                if (fieldName.equals("deviceSerial") && StringUtils.isBlank((CharSequence) field.get(obj))) {
                    throw new RuntimeException("非空参数不能为空");
                }
                if (fieldName.equals("productModel") && StringUtils.isBlank((CharSequence) field.get(obj))) {

                    throw new RuntimeException("非空参数不能为空");
                }
                if (fieldName.equals("cardData") && StringUtils.isBlank((CharSequence) field.get(obj))) {

                    throw new RuntimeException("非空参数不能为空");
                }

                if (fieldName.equals("startTime")) {

                    if (fieldName.equals("startTime") && StringUtils.isBlank((CharSequence) field.get(obj))) {

                        throw new RuntimeException("传入日期参数不能为空");
                    }

                    b = field.get(obj).toString().matches(timeReg);

                    if (!b) {
                        throw new RuntimeException("传入时间串格式有误");
                    }

                }

                if (fieldName.equals("endTime")) {

                    if (fieldName.equals("endTime") && StringUtils.isBlank((CharSequence) field.get(obj))) {

                        throw new RuntimeException("传入日期参数不能为空");
                    }
                    b = field.get(obj).toString().matches(timeReg);

                    if (!b) {
                        throw new RuntimeException("传入时间串格式有误");

                    }
                }

            } catch (IllegalAccessException e) {

                throw new RuntimeException(e.getMessage());
            }
        }

    }


    /**
     * remark: 返回信息
     *
     * @return
     * @author:chenj
     * @date: 2019/11/14
     */
    public static RockReqResult  rockCommonReply(String url, String json) {
        RockReqResult rockReqResult = new RockReqResult();

        System.out.println(url);
        String result = HttpUtil.doJsonPost(url, json);

        if (!isJson(result) || StringUtils.isBlank(result)) {

            throw new RuntimeException(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
        }

        JSONObject resultJson = JSONObject.parseObject(result);

        if (!resultJson.getString("code").equals("100")) {

            throw new RuntimeException(ErrMsgEnum.ERR.getMsg() + ":" + resultJson.get("message"));
        }

        rockReqResult.setSuccess(true);

        return rockReqResult;
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
