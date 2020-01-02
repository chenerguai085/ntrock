package com.rh.netlock.entrance;

import com.rh.netlock.entity.*;
import com.rh.netlock.enums.CompanyBusEnum;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.handle.impl.XieZFaceLockReqApiImpl;
import com.rh.netlock.handle.impl.YunDingLockReqApiImpl;
import com.rh.netlock.handle.service.LockCommonService;

/**
 * @author:chenj
 * @remark:  对外暴露的方法
 * @date: 2019/11/15
 */
public class LockServer {
    /**
     * remark: 新增密码/卡号
     *
     * @return
     * @author:chenj
     * @date: 2019/11/14
     */
    public static String add(NetLock netLockIn) throws Exception {
        if (null == netLockIn)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == netLockIn.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());


        return LockCommonService.add(netLockIn);
    }


    /**
     * remark:删除
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String delete(DelLock delLock) throws Exception {
        if (null == delLock)
            throw new Exception(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == delLock.getCompanyBusEnum())
            throw new Exception(ErrMsgEnum.EER_COMANY_NO.getMsg());


        return LockCommonService.delete(delLock);
    }

    /**
     * remark: 更新门锁 悉点有接口
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String update(UpdateLock updateLock) throws Exception {
        if (null == updateLock)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == updateLock.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        return LockCommonService.update(updateLock);
    }


    /**
     * remark: 清空所有开门用户
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
     */
    public static String clearAll(LockBase lockBase) throws Exception {
        if (null == lockBase)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == lockBase.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        return LockCommonService.clearAll(lockBase);
    }

    /**
     * remark: 获取token信息接口
     *
     * @return com.rh.netrock.entity.Token
     * @author:chenj
     * @date: 2019/12/3
     */
    public static Token getToken(User user) throws Exception {
        if (null == user)
            throw new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if (null == user.getCompanyBusEnum())
            throw new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        CompanyBusEnum channel = user.getCompanyBusEnum();

        Token token = null;
        switch (channel) {
            case XD_NETROCK:

                break;
            case HLS_NETROCK:

                break;
            case XIEZ_FACEROCK:
                token = XieZFaceLockReqApiImpl.getToken(user);

                return token;
            case YUNDING_NETROCK:
                token = YunDingLockReqApiImpl.getToken(user);

                return token;
            default:
                break;
        }

        return token;
    }


    /**
     * remark: 远程开门
     *
     * @return void
     * @author:chenj
     * @date: 2019/11/29
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
