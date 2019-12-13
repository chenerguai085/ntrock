package com.rh.netlock.handle;

import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.LockBase;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.enums.*;
import com.rh.netlock.util.DateHelper;
import com.rh.netlock.util.ParseUtil;
import com.rh.netlock.util.WebServiceUtil;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class HlisLockImpl {
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
        paramsVersign(netLock);

        if (StringUtils.isBlank(netLock.getDomain())) {
            netLock.setDomain(LockEnum.HLS_DOMAIN.getMsg());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netLock.getLockId());
        map.put("CardType", netLock.getOpenTypeEnum().getCode().toString());
        map.put("CardData", netLock.getLockData());
        map.put("BeginTime", DateHelper.formatDate(netLock.getStartTime(),datePattern));
        map.put("EndTime", DateHelper.formatDate(netLock.getEndTime(),datePattern));

        String xml = ParseUtil.buildXml(LockEnum.HLS_Add_OpenUser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netLock.getDomain());

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
        if (StringUtils.isBlank(delLock.getLockId()) || StringUtils.isBlank(delLock.getLockData()) || null == delLock.getOpenTypeEnum() ) {
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        if (StringUtils.isBlank(delLock.getDomain())) {
            delLock.setDomain(LockEnum.HLS_DOMAIN.getMsg());
        }

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
        if (StringUtils.isBlank(lockBase.getDomain())) {
            lockBase.setDomain(LockEnum.HLS_DOMAIN.getMsg());
        }

        if (StringUtils.isBlank(lockBase.getLockId())) {
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", lockBase.getLockId());

        String xml = ParseUtil.buildXml(LockEnum.HLS_Clear_Openuser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, lockBase.getDomain());

        if (!resultId.equals(resultMap.get("resultID").toString()))
            throw new Exception(ErrMsgEnum.ERR + ":" + resultMap.get("description").toString());

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


    /**
     * remark: 参数验证
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void paramsVersign(NetLock netLock) throws Exception {
        if (StringUtils.isBlank(netLock.getLockId()) || null == netLock.getOpenTypeEnum()
                || StringUtils.isBlank(netLock.getLockData()) || null == netLock.getStartTime() ||
                null == netLock.getEndTime())
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
    }

}
