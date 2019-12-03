package com.rh.netrock.handle;

import com.rh.netrock.entity.NetRock;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.RegEnum;
import com.rh.netrock.enums.RockEnum;
import com.rh.netrock.util.ParseUtil;
import com.rh.netrock.util.WebServiceUtil;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class HlisRockImpl {

    /**
     * remark:添加开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */

    public static String add(NetRock netRock) throws Exception {

        paramsVersign(netRock);

        if (StringUtils.isBlank(netRock.getDomain())) {
            netRock.setDomain(RockEnum.HLS_DOMAIN.getMsg());
        }

        if (3 == netRock.getOpenTypeEnum().getCode()) {
            if (!netRock.getCardData().matches(RegEnum.CARD_DATA_REG.getMsg()))
                throw new RuntimeException("输入密码格式有误");

            return buildReqAdd(netRock);
        }
        if (2 == netRock.getOpenTypeEnum().getCode()) {

            //身份证类型
            return buildReqAdd(netRock);
        }

        if (1 == netRock.getOpenTypeEnum().getCode()) {

            return buildReqAdd(netRock);
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

    public static void delete(NetRock netRock) throws Exception {

        buildReqDelete(netRock);
    }

    /**
     * remark:清空开门用户
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */


    public static void clearAll(NetRock netRock) throws Exception {
        if (StringUtils.isBlank(netRock.getDomain()))
            throw new Exception(ErrMsgEnum.ERR_DOMAIN_NULL.getMsg());

        buildReqClearAll(netRock);
    }


    /**
     * remark: 远程开门
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static void remoteOpen(NetRock netRock) throws Exception {
        if (StringUtils.isBlank(netRock.getDeviceSerial())) {
            throw new RuntimeException(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }
        buildReqRemoteOpen(netRock);
    }

    /**
     * remark:封装清空用户请求数据
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    private static void buildReqRemoteOpen(NetRock netRock) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netRock.getDeviceSerial());

        String xml = ParseUtil.buildXml(RockEnum.HL_OpenLock_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netRock.getDomain());

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
    private static void buildReqDelete(NetRock netRock) throws Exception {

        if (StringUtils.isBlank(netRock.getDeviceSerial()) || StringUtils.isBlank(netRock.getCardData())) {
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netRock.getDeviceSerial());
        map.put("CardType", netRock.getOpenTypeEnum().getCode().toString());
        map.put("CardData", netRock.getCardData());

        if (3 == netRock.getOpenTypeEnum().getCode()) {
            if (!netRock.getCardData().matches(RegEnum.CARD_DATA_REG.getMsg()))
                throw new Exception("输入密码格式有误");
        }

        String xml = ParseUtil.buildXml(RockEnum.HL_Delete_Openuser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netRock.getDomain());

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
    private static void buildReqClearAll(NetRock netRock) throws Exception {
        if (StringUtils.isBlank(netRock.getDeviceSerial())) {
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netRock.getDeviceSerial());

        String xml = ParseUtil.buildXml(RockEnum.HL_Clear_Openuser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netRock.getDomain());

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
    private static String buildReqAdd(NetRock netRock) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("RoomId", netRock.getDeviceSerial());
        map.put("CardType", netRock.getOpenTypeEnum().getCode().toString());
        map.put("CardData", netRock.getCardData());
        map.put("BeginTime", netRock.getStartTime());
        map.put("EndTime", netRock.getEndTime());

        String xml = ParseUtil.buildXml(RockEnum.HL_Add_OpenUser_REQ.getMsg(), map);

        Map<String, Object> resultMap = WebServiceUtil.websReq(xml, netRock.getDomain());

        if ("0".equals(resultMap.get("resultID").toString())) {
            return netRock.getCardData();
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
    private static void paramsVersign(NetRock netRock) throws Exception {
        if (StringUtils.isBlank(netRock.getDeviceSerial()) || null == netRock.getOpenTypeEnum()
                || StringUtils.isBlank(netRock.getCardData()) || StringUtils.isBlank(netRock.getStartTime()) ||
                StringUtils.isBlank(netRock.getEndTime()))
            throw new Exception(ErrMsgEnum.ERR_PARAMS_NULL.getMsg());
    }

}
