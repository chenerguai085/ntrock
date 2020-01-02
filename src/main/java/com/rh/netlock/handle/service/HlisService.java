package com.rh.netlock.handle.service;

import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.LockBase;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.handle.impl.HlisLockReqApiImpl;
import com.rh.netlock.util.ParamsVerifyUtil;
import org.apache.commons.lang3.StringUtils;
import sun.security.krb5.internal.crypto.NullEType;

import java.util.Date;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/18
 */
public class HlisService {
    /**
     * remark: 新增
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */
    public static String add(NetLock netLock) throws Exception {
        ParamsVerifyUtil.paramsVerify(netLock,"lockId","openTypeEnum","lockData","startTime","endTime");


        if (StringUtils.isBlank(netLock.getDomain())) {
            netLock.setDomain(LockEnum.HLS_DOMAIN.getMsg());
        }

        return HlisLockReqApiImpl.add(netLock);
    }

    /**
     * remark: 删除
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */
    public static String delete(DelLock delLock) throws Exception {
        ParamsVerifyUtil.paramsVerify(delLock,"lockId","lockData","openTypeEnum");

        delLock.setDomain(StringUtils.isBlank(delLock.getDomain()) ? LockEnum.HLS_DOMAIN.getMsg() : delLock.getDomain());


        return HlisLockReqApiImpl.delete(delLock);
    }


    /**
     * remark:清空
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */
    public static String clearAll(LockBase lockBase) throws Exception {
        ParamsVerifyUtil.paramsVerify(lockBase,"lockId");

        lockBase.setDomain(StringUtils.isBlank(lockBase.getDomain()) ? LockEnum.HLS_DOMAIN.getMsg() : lockBase.getDomain());

        return HlisLockReqApiImpl.clearAll(lockBase);
    }


    /**
     * remark: 更新
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/18
     */
    public static String update(UpdateLock updateLock) throws Exception {
        ParamsVerifyUtil.paramsVerify(updateLock,"lockId","openTypeEnum","endTime","lockData");

        updateLock.setDomain(StringUtils.isBlank(updateLock.getDomain()) ? LockEnum.HLS_DOMAIN.getMsg() : updateLock.getDomain());

        if (StringUtils.isBlank(updateLock.getOldLockData()) || updateLock.getLockData().equals(updateLock.getOldLockData())) {
            ////前端只传一个i密码或者两密码相同  表示只修改过期时间 直接调新增接口覆盖原来密码
            return HlisLockReqApiImpl.update(updateLock);
        } else {
            ////前端传两个不相同密码  删除旧密码 新加新密码

            return HlisLockReqApiImpl.delAdd(updateLock);
        }
    }

}
