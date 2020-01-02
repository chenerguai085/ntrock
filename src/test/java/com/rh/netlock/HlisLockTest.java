package com.rh.netlock;

import com.rh.netlock.entity.DelLock;
import com.rh.netlock.entity.NetLock;
import com.rh.netlock.entrance.LockServer;
import com.rh.netlock.enums.CompanyBusEnum;
import com.rh.netlock.enums.OpenTypeEnum;
import com.rh.netlock.util.DateHelper;

import java.util.Date;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/12/23
 */
public class HlisLockTest {


    private static String add(){
        int companyType = 2; //1-悉点 2-豪力士
        int openType = 1; //1-IC卡 2-身份证（悉点不支持） 3-密码
        CompanyBusEnum companyBusEnum = CompanyBusEnum.getEnumByCode(companyType);
        OpenTypeEnum openTypeEnum = OpenTypeEnum.getEnumByCode(openType);

        NetLock netLock = new NetLock();
        netLock.setCompanyBusEnum(companyBusEnum);
        netLock.setOpenTypeEnum(openTypeEnum);
        //  netLock.setLockId("D44132916");
        // 3627031273
        netLock.setLockId("330");
        //  netLock.setLockId("D44132916");
      //  netLock.setLockOption("S-L2");
        //7854147   //41E25B0499529D7F    //D639ECD4D2
        //3572251094  147569
        netLock.setLockData("D639ECD4D2");
        // netLock.setDomain("http://gateway.seedien.com");//悉点接口地址
        netLock.setDomain("http://121.201.67.205:8007"); //豪力士接口地址
        netLock.setStartTime(new Date());

        netLock.setEndTime(DateHelper.getDiffDate(2));

        String result = "";
        try {
            result = LockServer.add(netLock);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    private static String delete() throws Exception {
        int companyType = 2; //1-悉点 2-豪力士
        int openType = 1; //1-IC卡 2-身份证（悉点不支持） 3-密码
        CompanyBusEnum companyBusEnum = CompanyBusEnum.getEnumByCode(companyType);
        OpenTypeEnum openTypeEnum = OpenTypeEnum.getEnumByCode(openType);

        DelLock delLock = new DelLock();
        delLock.setLockData("D639ECD4D2");
        delLock.setCompanyBusEnum(companyBusEnum);
        delLock.setOpenTypeEnum(openTypeEnum);
        //   delLock.setLockId("D44132916");

        delLock.setLockId("330");


        return LockServer.delete(delLock);

    }


    public static void main(String[] args) throws Exception {

      //  String add = ;
       // System.out.println(add());

        System.out.println(delete());
    }
}
