package com.rh.netlock.handle.service;

import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.LockBase;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entity.UpdateLock;
import com.rh.netlock.enums.CompanyBusEnum;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/17
 */
public class LockCommonService {


    public static String add(NetLock netLock) throws Exception {

        CompanyBusEnum channel = netLock.getCompanyBusEnum();
        String result = "";
        switch (channel) {
            case XD_NETROCK:
                result = XidianService.add(netLock);

                return result;
            case HLS_NETROCK:
                result = HlisService.add(netLock);

                return result;
            case XIEZ_FACEROCK:

                return result;
            case YUNDING_NETROCK:

                return result;
            default:
                break;
        }

        return result;
    }

    public static String update(UpdateLock updateLock) throws Exception {
        CompanyBusEnum channel = updateLock.getCompanyBusEnum();

        switch (channel) {
            case XD_NETROCK:

                return  XidianService.update(updateLock);
            case HLS_NETROCK:

                return HlisService.update(updateLock);
            case XIEZ_FACEROCK:

                break;
            case YUNDING_NETROCK:

                break;
            default:
                break;
        }

        return null;
    }


    public static String delete(DelLock delLock) throws Exception {

        CompanyBusEnum channel = delLock.getCompanyBusEnum();

        switch (channel) {
            case XD_NETROCK:


                return XidianService.delete(delLock);
            case HLS_NETROCK:


                return HlisService.delete(delLock);
            case XIEZ_FACEROCK:

                break;
            case YUNDING_NETROCK:

               break;
            default:
                break;
        }


        return null;
    }

    public static String clearAll(LockBase lockBase) throws Exception {
        CompanyBusEnum channel = lockBase.getCompanyBusEnum();

        switch (channel) {
            case XD_NETROCK:

                 break;
            case HLS_NETROCK:


                return HlisService.clearAll(lockBase);
            case XIEZ_FACEROCK:

               break;
            case YUNDING_NETROCK:

                break;
            default:
                break;
        }

        return null;
    }



}
