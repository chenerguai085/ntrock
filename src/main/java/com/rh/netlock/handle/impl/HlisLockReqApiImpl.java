package com.rh.netlock.handle.impl;

import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.LockBase;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.enums.*;
import com.rh.netlock.util.DateHelper;
import com.rh.netlock.util.ParseUtil;
import com.rh.netlock.util.WebServiceUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class HlisLockReqApiImpl {
    static final String datePattern = "yyyy-MM-dd HH:mm:ss";
    static final String resultId = "0";

    /**
     * remark:添加开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String add(NetLock netLock) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netLock.getLockId());
        map.put("CardType", netLock.getOpenTypeEnum().getCode().toString());
        map.put("CardData", netLock.getLockData());
        map.put("BeginTime", DateHelper.formatDate(netLock.getStartTime(), datePattern));
        map.put("EndTime", DateHelper.formatDate(netLock.getEndTime(), datePattern));

        String xml = ParseUtil.buildXml(LockEnum.HLS_Add_OpenUser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netLock.getDomain());

        if (!resultId.equals(resultMap.get("resultID").toString()))
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultMap.get("description").toString());

        return resultMap.get("resultXml").toString();
    }

    /**
     * remark:添加开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String update(UpdateLock updateLock) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", updateLock.getLockId());
        map.put("CardType", updateLock.getOpenTypeEnum().getCode().toString());
        map.put("BeginTime", DateHelper.formatDate(new Date(), datePattern));
        map.put("EndTime", DateHelper.formatDate(updateLock.getEndTime(), datePattern));
        map.put("CardData", updateLock.getLockData());
        String xml = null;
        Map<String, Object> resultMap = null;

        //前端只传一个i密码或者两密码相同  表示只修改过期时间 直接调新增接口覆盖原来密码
        xml = ParseUtil.buildXml(LockEnum.HLS_Add_OpenUser_REQ.getMsg(), map);
        resultMap = WebServiceUtil.websReq(xml, updateLock.getDomain());

        if (!resultId.equals(resultMap.get("resultID").toString()))
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultMap.get("description").toString());

        return resultMap.get("resultXml").toString();

    }


    public static String delAdd(UpdateLock updateLock) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", updateLock.getLockId());
        map.put("CardType", updateLock.getOpenTypeEnum().getCode().toString());
        map.put("BeginTime", DateHelper.formatDate(new Date(), datePattern));
        map.put("EndTime", DateHelper.formatDate(updateLock.getEndTime(), datePattern));

        String xml = null;
        Map<String, Object> resultMap = null;

        map.put("CardData", updateLock.getOldLockData());
        //前端传两个不相同密码  删除旧密码 新加新密码
        xml = ParseUtil.buildXml(LockEnum.HLS_Delete_Openuser_REQ.getMsg(), map);
        resultMap = WebServiceUtil.websReq(xml, updateLock.getDomain());
        if (resultId.equals(resultMap.get("resultID").toString())) {
            //删除成功  再新增
            map.put("CardData", updateLock.getLockData());
            xml = ParseUtil.buildXml(LockEnum.HLS_Add_OpenUser_REQ.getMsg(), map);
            resultMap = WebServiceUtil.websReq(xml, updateLock.getDomain());
        }
        if (!resultId.equals(resultMap.get("resultID").toString()))
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultMap.get("description").toString());

        return resultMap.get("resultXml").toString();
    }


    /**
     * remark: 删除开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String delete(DelLock delLock) throws Exception {
//        if (StringUtils.isBlank(delLock.getLockId()) || StringUtils.isBlank(delLock.getLockData()) || null == delLock.getOpenTypeEnum()) {
//            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
//        }
//
//        if (StringUtils.isBlank(delLock.getDomain())) {
//            delLock.setDomain(LockEnum.HLS_DOMAIN.getMsg());
//        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", delLock.getLockId());
        map.put("CardType", delLock.getOpenTypeEnum().getCode().toString());
        map.put("CardData", delLock.getLockData());

        String xml = ParseUtil.buildXml(LockEnum.HLS_Delete_Openuser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, delLock.getDomain());

        if (!resultId.equals(resultMap.get("resultID").toString()))
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultMap.get("description").toString());

        return resultMap.get("resultXml").toString();
    }

    /**
     * remark:清空开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String clearAll(LockBase lockBase) throws Exception {
//        if (StringUtils.isBlank(lockBase.getDomain())) {
//            lockBase.setDomain(LockEnum.HLS_DOMAIN.getMsg());
//        }
//
//        if (StringUtils.isBlank(lockBase.getLockId())) {
//            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
//        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", lockBase.getLockId());

        String xml = ParseUtil.buildXml(LockEnum.HLS_Clear_Openuser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, lockBase.getDomain());

        if (!resultId.equals(resultMap.get("resultID").toString()))
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultMap.get("description").toString());

        return resultMap.get("resultXml").toString();
    }


//    /**
//     * remark: 远程开门
//     *
//     * @return java.lang.String
//     * @author:chenj
//     * @date: 2019/11/29
//     */
//    public static String remoteOpen(NetLock netLock) throws Exception {
//        if (StringUtils.isBlank(netLock.getLockId())) {
//            throw new RuntimeException(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
//        }
//        Map<String, Object> map = new HashMap<>();
//        map.put("RoomId", netLock.getLockId());
//
//        String xml = ParseUtil.buildXml(LockEnum.HLS_OpenLock_REQ.getMsg(), map);
//
//        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netLock.getDomain());
//
//        if (!resultId.equals(resultMap.get("resultID").toString()))
//            throw new Exception(ErrMsgEnum.ERR + ":" + resultMap.get("description").toString());
//
//        return resultMap.get("resultXml").toString();
//    }

}
