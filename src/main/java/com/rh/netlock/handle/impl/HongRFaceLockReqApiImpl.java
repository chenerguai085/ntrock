package com.rh.netlock.handle.impl;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.LockBase;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.Token;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.util.HttpUtil;
import com.rh.netlock.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/16
 */
public class HongRFaceLockReqApiImpl {

    final static String successCode = "0";

    public static String add(NetLock netLock) throws Exception {
        //
        netLock.setDomain(StringUtils.isBlank(netLock.getDomain()) ? LockEnum.HONGRFACE_DOMAIN.getMsg() : netLock.getDomain());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("img_format", "jpg");
        jsonObject.put("img", netLock.getFileStr());

        String upImgResult = HttpUtil.hongrCloudJsonPost(jsonObject.toString(), netLock.getDomain().concat(LockEnum.HONGR_FACE_IMAGEUPLOAD_API.getMsg()));

        if (!JsonHelper.isJson(upImgResult)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + upImgResult);
        }

        //上传成功 取出返回图片url 新增员工接口使用
        JSONObject upImgRespJson = JSONObject.parseObject(upImgResult);
        if (upImgRespJson.getString("code").equals(successCode)) {
            //上传图片成功  继续添加员工
            String dataStr = upImgRespJson.getString("data");

            JSONObject upImgDataJson = JSONObject.parseObject(dataStr);

            String imgUrl = upImgDataJson.getString("img_url");

            String addPersonResult = HttpUtil.hongrCloudJsonPost(buildAddJson(netLock, imgUrl), netLock.getDomain().concat(LockEnum.HONGR_FACE_ADD_API.getMsg()));

            if (!JsonHelper.isJson(addPersonResult)) {
                throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + addPersonResult);
            }

            JSONObject addPsRespJson = JSONObject.parseObject(addPersonResult);

            if (addPsRespJson.getString("code").equals(successCode)) {

                System.out.println("addPersonResult: " + addPersonResult);
                return addPersonResult;
            }

        }
        return null;
    }


    /**
     * remark: 删除
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/17
     */
    public static String delete(DelLock delLock) throws Exception {
        //
        delLock.setDomain(StringUtils.isBlank(delLock.getDomain()) ? LockEnum.HONGRFACE_DOMAIN.getMsg() : delLock.getDomain());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("staff_id",Integer.parseInt(delLock.getLockData()) );

        String delResult = HttpUtil.hongrCloudJsonPost(jsonObject.toString(), delLock.getDomain().concat(LockEnum.HONGR_FACE_DEL_API.getMsg()));

        if (!JsonHelper.isJson(delResult)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + delResult);
        }

        JSONObject addPsRespJson = JSONObject.parseObject(delResult);

        if (addPsRespJson.getString("code").equals(successCode)) {

            return delResult;
        }

        return null;
    }


    /**
     *remark: 清除设备所有人脸信息
     *@author:chenj
     *@date: 2019/12/19
     *@return java.lang.String
     */
    public static String clearAll(LockBase lockBase) throws Exception {
        lockBase.setDomain(StringUtils.isBlank(lockBase.getDomain()) ? LockEnum.HONGRFACE_DOMAIN.getMsg() : lockBase.getDomain());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dev_sno",lockBase.getLockId());

        String clearAllResult = HttpUtil.hongrCloudJsonPost(jsonObject.toString(), lockBase.getDomain().concat(LockEnum.HONGR_FACE_DEVICE_DELALL_API.getMsg()));

        if (!JsonHelper.isJson(clearAllResult)) {
            throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg() + ": " + clearAllResult);
        }

        JSONObject addPsRespJson = JSONObject.parseObject(clearAllResult);

        if (addPsRespJson.getString("code").equals(successCode)) {

            return clearAllResult;
        }

        return null;
    }


    /**
     * remark: 封装新增人脸接口
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/17
     */
    private static String buildAddJson(NetLock netLock, String imgUrl) {
        JSONObject addJson = new JSONObject();

        String[] arrInfo = netLock.getLockData().split(",");
        addJson.put("name", arrInfo[0]);
        addJson.put("workNo", arrInfo[1]);
        //相当于酒店标记 56
        addJson.put("department", Integer.parseInt(netLock.getLockOption()));  //部门 睿沃酒店
        addJson.put("job", 69);    //职业 房客
        //相当于锁的标识  27
       // int[] groupArr = {Integer.parseInt(netLock.getLockId())};  //设备分组 S组

        int[] groupArr = {33,34};

        String[] imgArr = {imgUrl};  //设备分组 S组

        addJson.put("groups", groupArr);
        addJson.put("templateImg", imgArr);
        return addJson.toString();
    }


//    /**
//     *remark: 封装 清除设备所有人员信息
//     *@author:chenj
//     *@date: 2019/12/19
//     *@return java.lang.String
//     */
//    private static String buildClearAllJson(LockBase lockBase) {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("dev_sno",lockBase.getLockId());
//
//        return jsonObject.toString();
//    }


    public static void main(String[] args) throws Exception {
        //79
         DelLock delLock = new DelLock();
         delLock.setLockData("79");
         delete(delLock);
    }

}
