package com.rh.netlock.handle;

import com.rh.netlock.entity.ClearLock;
import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.entrance.LockServer;
import com.rh.netlock.enums.*;
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

        if (3 == netLock.getOpenTypeEnum().getCode()) {
            if (!netLock.getLockData().matches(RegEnum.CARD_DATA_REG.getMsg()))
                throw new RuntimeException("输入密码格式有误");

            return buildReqAdd(netLock);
        }
        if (2 == netLock.getOpenTypeEnum().getCode()) {

            //身份证类型
            return buildReqAdd(netLock);
        }

        if (1 == netLock.getOpenTypeEnum().getCode()) {

            return buildReqAdd(netLock);
        }
        throw new Exception(ErrMsgEnum.ERR.getMsg());
    }


    /**
     * remark: 删除开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */

    public static void delete(DelLock delLock) throws Exception {

        buildReqDelete(delLock);
    }

    /**
     * remark:清空开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */


    public static void clearAll(ClearLock clearLock) throws Exception {
        if (StringUtils.isBlank(clearLock.getDomain()))
            throw new Exception(ErrMsgEnum.ERR_DOMAIN_NULL.getMsg());

        buildReqClearAll(clearLock);
    }


    /**
     * remark: 远程开门
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static void remoteOpen(NetLock netLock) throws Exception {
        if (StringUtils.isBlank(netLock.getLockId())) {
            throw new RuntimeException(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }
        buildReqRemoteOpen(netLock);
    }

    /**
     * remark:封装清空用户请求数据
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void buildReqRemoteOpen(NetLock netLock) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netLock.getLockId());

        String xml = ParseUtil.buildXml(LockEnum.HL_OpenLock_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netLock.getDomain());

        if (!"0".equals(resultMap.get("resultID")))
            throw new RuntimeException(ErrMsgEnum.ERR + ":" + resultMap.get("description").toString());
    }


    /**
     * remark:封装删除请求数据
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void buildReqDelete(DelLock delLock) throws Exception {

        if (StringUtils.isBlank(delLock.getLockId()) || StringUtils.isBlank(delLock.getLockData()) || null == delLock.getOpenTypeEnum() ) {
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", delLock.getLockId());
        map.put("CardType", delLock.getOpenTypeEnum().getCode().toString());
        map.put("CardData", delLock.getLockData());

        if (3 == delLock.getOpenTypeEnum().getCode()) {
            if (!delLock.getLockData().matches(RegEnum.CARD_DATA_REG.getMsg()))
                throw new Exception("输入密码格式有误");
        }

        String xml = ParseUtil.buildXml(LockEnum.HL_Delete_Openuser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, delLock.getDomain());

        if (!"0".equals(resultMap.get("resultID")))
            throw new Exception(ErrMsgEnum.ERR + ":" + resultMap.get("description").toString());
    }

    /**
     * remark:封装清空用户请求数据
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void buildReqClearAll(ClearLock clearLock) throws Exception {
        if (StringUtils.isBlank(clearLock.getLockId())) {
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", clearLock.getLockId());

        String xml = ParseUtil.buildXml(LockEnum.HL_Clear_Openuser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, clearLock.getDomain());

        if (!"0".equals(resultMap.get("resultID")))
            throw new Exception(ErrMsgEnum.ERR + ":" + resultMap.get("description").toString());
    }

    /**
     * remark: 封装添加请求数据
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    private static String buildReqAdd(NetLock netLock) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netLock.getLockId());
        map.put("CardType", netLock.getOpenTypeEnum().getCode().toString());
        map.put("CardData", netLock.getLockData());
        map.put("BeginTime", netLock.getStartTime());
        map.put("EndTime", netLock.getEndTime());

        String xml = ParseUtil.buildXml(LockEnum.HL_Add_OpenUser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netLock.getDomain());

        if ("0".equals(resultMap.get("resultID").toString())) {
            return netLock.getLockData();
        } else {
            throw new Exception(ErrMsgEnum.ERR.getMsg() + ":" + resultMap.get("description").toString());
        }
    }


    /**
     * remark: 参数验证
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void paramsVersign(NetLock netLock) throws Exception {
        if (StringUtils.isBlank(netLock.getLockId()) || null == netLock.getOpenTypeEnum()
                || StringUtils.isBlank(netLock.getLockData()) || StringUtils.isBlank(netLock.getStartTime()) ||
                StringUtils.isBlank(netLock.getEndTime()))
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
    }


    public static void main(String[] args) {
        int companyType = 2; //1-悉点 2-豪力士
        int openType =  3; //1-IC卡 2-身份证（悉点不支持） 3-密码

        CompanyBusEnum companyBusEnum = CompanyBusEnum.getEnumByCode(companyType);

        OpenTypeEnum openTypeEnum = OpenTypeEnum.getEnumByCode(openType);

        NetLock netLock = new NetLock();
        netLock.setCompanyBusEnum(companyBusEnum);
        netLock.setOpenTypeEnum(openTypeEnum);
        netLock.setLockId("330");
        // netLock.setLockOption("S-L2");
        netLock.setLockData("444555");
        //netLock.setDomain("http://gateway.seedien.com");//悉点接口地址
        netLock.setDomain("http://121.201.67.205:8007"); //豪力士接口地址
        netLock.setStartTime("2019-11-29 17:17:00");
        netLock.setEndTime("2019-12-30 17:17:00");


        UpdateLock updateLock = new UpdateLock();

        updateLock.setOldLockData("444555");
        updateLock.setLockData("555666");
        updateLock.setCompanyBusEnum(companyBusEnum);
        updateLock.setOpenTypeEnum(openTypeEnum);
        updateLock.setLockId("D44132916");
        updateLock.setLockOption("S-L2");

        updateLock.setStartTime("2019-11-29 17:17:00");
        updateLock.setEndTime("2019-12-30 17:17:00");


        DelLock delLock = new DelLock();
        delLock.setLockData("555666");
        delLock.setCompanyBusEnum(companyBusEnum);
        delLock.setOpenTypeEnum(openTypeEnum);
        delLock.setLockId("D44132916");
        delLock.setLockOption("S-L2");
        String result = null;
        try {
            result = LockServer.add(netLock);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(result);

    }




}
