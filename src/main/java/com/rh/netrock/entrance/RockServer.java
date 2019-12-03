package com.rh.netrock.entrance;

import com.rh.netrock.entity.NetRock;
import com.rh.netrock.entity.Token;
import com.rh.netrock.enums.CompanyBusEnum;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.handle.HlisRockImpl;
import com.rh.netrock.handle.XidianRockImpl;
import com.rh.netrock.handle.XieZFaceRockImpl;


/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/15
 */

public class RockServer {
    /**
     * remark: 新增密码/卡号
     *
     * @return
     * @author:chenj
     * @date: 2019/11/14
     */
    public static String add(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == netRockIn.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        CompanyBusEnum channel = netRockIn.getCompanyBusEnum();
        String result = "";
        switch (channel) {
            case XD_NETROCK:
                result = XidianRockImpl.add(netRockIn);

                return result;
            case HLS_NETROCK:
                result = HlisRockImpl.add(netRockIn);
                return result;
            case XIEZ_FACEROCK:
                result = XieZFaceRockImpl.add(netRockIn);
                return result;
            default:
                break;
        }


        return result;
    }


    /**
     * remark:删除
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static void delete(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw new Exception(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == netRockIn.getCompanyBusEnum())
            throw new Exception(ErrMsgEnum.EER_COMANY_NO.getMsg());

        CompanyBusEnum channel = netRockIn.getCompanyBusEnum();

        switch (channel) {
            case XD_NETROCK:
                XidianRockImpl.delete(netRockIn);
                break;
            case HLS_NETROCK:
                HlisRockImpl.delete(netRockIn);
                break;
            case XIEZ_FACEROCK:
                return;
            default:
                break;
        }

    }

    /**
     * remark: 更新门锁 悉点有接口
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String update(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == netRockIn.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        CompanyBusEnum channel = netRockIn.getCompanyBusEnum();
        String result = "";
        switch (channel) {
            case XD_NETROCK:
                result = XidianRockImpl.update(netRockIn);

                return result;
            case HLS_NETROCK:

                return "";
            case XIEZ_FACEROCK:
                return "";
            default:
                break;
        }


        return result;
    }


    /**
     * remark: 清空所有开门用户
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    public static void clearAll(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == netRockIn.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        CompanyBusEnum channel = netRockIn.getCompanyBusEnum();
        switch (channel) {
            case XD_NETROCK:
                break;
            case HLS_NETROCK:
                HlisRockImpl.clearAll(netRockIn);
                break;
            case XIEZ_FACEROCK:
                break;
            default:
                break;
        }
    }

    /**
     * remark: 获取token信息接口
     *
     * @return com.rh.netrock.entity.Token
     * @author:chenj
     * @date: 2019/12/3
     */
    public static Token getToken(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == netRockIn.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        CompanyBusEnum channel = netRockIn.getCompanyBusEnum();

        Token token = null;
        switch (channel) {
            case XD_NETROCK:

                break;
            case HLS_NETROCK:

                break;
            case XIEZ_FACEROCK:
                token = XieZFaceRockImpl.getToken(netRockIn);

                return token;
            default:
                break;
        }


        return token;
    }


    /**
     *remark: 远程开门
     *@author:chenj
     *@date: 2019/11/29
     *@return void
     */
//    public  void remoteOpen(NetRock netRockIn) throws Exception {
//        if (null == netRockIn)
//            throw  new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());
//        ManagerHandle managerHandle = new ManagerHandle();
//
//        if(StringUtils.isBlank(netRockIn.getDomain()))
//            throw  new RuntimeException(ErrMsgEnum.ERR_DOMAIN_NULL.getMsg());
//        if (null == netRockIn.getCompanyBusEnum())
//            throw  new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());
//
//        if (netRockIn.getCompanyBusEnum().getCode() == 1)
//            throw  new RuntimeException(ErrMsgEnum.ERR_API_UNSUPPORT.getMsg());
//
//        managerHandle.remoteOpen(netRockIn.getCompanyBusEnum().getCode().toString(), netRockIn);
//    }


}
