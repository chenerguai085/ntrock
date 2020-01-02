package com.rh.netlock.handle.service;

import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.enums.LockEnum;
import com.rh.netlock.enums.OpenTypeEnum;
import com.rh.netlock.handle.impl.XidianLockReqApiImpl;
import com.rh.netlock.util.ParamsVerifyUtil;
import org.apache.commons.lang3.StringUtils;


/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/17
 */
public class XidianService {
    /**
     *remark: 新增
     *@author:chenj
     *@date: 2019/12/18
     *@return java.lang.String
     */
    public static String add(NetLock netLock) throws Exception {
        ParamsVerifyUtil.paramsVerify(netLock,"lockId","lockOption","lockData","startTime","endTime","openTypeEnum");

        netLock.setDomain(StringUtils.isBlank(netLock.getDomain()) ? LockEnum.XIDIAN_DOMAIN.getMsg() : netLock.getDomain());

        OpenTypeEnum openTypeEnum = netLock.getOpenTypeEnum();

        switch (openTypeEnum) {
            case ICCARD:

                return XidianLockReqApiImpl.addCard(netLock);
            case PASSWORD:

                return XidianLockReqApiImpl.addPwd(netLock);
            default:
                break;
        }

        return null;
    }

    /**
     *remark: 删除
     *@author:chenj
     *@date: 2019/12/18
     *@return java.lang.String
     */
    public static String delete(DelLock delLock) throws Exception {
        ParamsVerifyUtil.paramsVerify(delLock,"lockId","lockOption","lockData","openTypeEnum");

        delLock.setDomain(StringUtils.isBlank(delLock.getDomain()) ? LockEnum.XIDIAN_DOMAIN.getMsg() : delLock.getDomain());

        OpenTypeEnum openTypeEnum = delLock.getOpenTypeEnum();

        switch (openTypeEnum) {
            case ICCARD:

                return XidianLockReqApiImpl.deleteCard(delLock);
            case PASSWORD:


                return XidianLockReqApiImpl.deletePwd(delLock);
            default:
                break;
        }
        return null;
    }

    /**
     *remark: 更新
     *@author:chenj
     *@date: 2019/12/18
     *@return java.lang.String
     */
    public static String update(UpdateLock updateLock) throws Exception {
        //校验参数
        ParamsVerifyUtil.paramsVerify(updateLock,"lockId","lockOption","lockData","openTypeEnum","endTime");

        updateLock.setDomain(StringUtils.isBlank(updateLock.getDomain()) ? LockEnum.XIDIAN_DOMAIN.getMsg() : updateLock.getDomain());

        OpenTypeEnum openTypeEnum = updateLock.getOpenTypeEnum();

        boolean flag = false;
        if (StringUtils.isBlank(updateLock.getOldLockData()) || updateLock.getOldLockData().equals(updateLock.getLockData())) {
            //表示只修改过期时间  先删除后新增
            flag = true;
        } else {
            //old new 都存在
            //修改密码 时间也可能被修改
            flag = false;
        }
        String result = null;
        switch (openTypeEnum) {
            case ICCARD:
                if (flag) {
                    result = XidianLockReqApiImpl.updateCard(updateLock);
                } else {
                    result = XidianLockReqApiImpl.delAddCard(updateLock);
                }
                return result;
            case PASSWORD:
                if (flag) {
                    result = XidianLockReqApiImpl.delAddPwd(updateLock);
                } else {
                    result = XidianLockReqApiImpl.updatePwd(updateLock);
                }

                return result;
            default:
                break;
        }

        return null;
    }
}
