package com.rh.netrock.entrance;

import com.rh.netrock.entity.NetRock;
import com.rh.netrock.enums.CompanyBusEnum;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.OpenTypeEnum;
import com.rh.netrock.handle.ManagerHandle;
import org.apache.commons.lang3.StringUtils;


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
    public  String add(NetRock netRockIn) throws Exception {
        ManagerHandle managerHandle = new ManagerHandle();
        if (null == netRockIn)
            throw  new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if(StringUtils.isBlank(netRockIn.getDomain()))
            throw  new RuntimeException(ErrMsgEnum.ERR_DOMAIN_NULL.getMsg());
        if (null == netRockIn.getCompanyBusEnum())
            throw  new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());
        if (null == netRockIn.getOpenTypeEnum())
            throw  new RuntimeException(ErrMsgEnum.ERR_OPENTRPE.getMsg());
        if (netRockIn.getOpenTypeEnum().getCode() == 2 && netRockIn.getCompanyBusEnum().getCode() == 1)
            return "";

        String result = managerHandle.add(netRockIn.getCompanyBusEnum().getCode().toString(), netRockIn);


        return result;
    }


    /**
     *remark:删除
     *@author:chenj
     *@date: 2019/11/29
     *@return java.lang.String
     */
    public  void delete(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw  new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());

        if(StringUtils.isBlank(netRockIn.getDomain()))
            throw  new RuntimeException(ErrMsgEnum.ERR_DOMAIN_NULL.getMsg());

        ManagerHandle managerHandle = new ManagerHandle();

        if (null == netRockIn.getCompanyBusEnum())
            throw  new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());
        if (null == netRockIn.getOpenTypeEnum())
            throw  new RuntimeException(ErrMsgEnum.ERR_OPENTRPE.getMsg());
        if (netRockIn.getOpenTypeEnum().getCode() == 2 && netRockIn.getCompanyBusEnum().getCode() == 1)
            return;

         managerHandle.delete(netRockIn.getCompanyBusEnum().getCode().toString(), netRockIn);
    }

    /**
     *remark: 更新门锁 悉点有接口
     *@author:chenj
     *@date: 2019/11/29
     *@return void
     */
    public  String update(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw  new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());
        ManagerHandle managerHandle = new ManagerHandle();

        if(StringUtils.isBlank(netRockIn.getDomain()))
            throw  new RuntimeException(ErrMsgEnum.ERR_DOMAIN_NULL.getMsg());
        if (null == netRockIn.getCompanyBusEnum())
            throw  new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        if (netRockIn.getCompanyBusEnum().getCode() == 2)
            return "";
        if (netRockIn.getOpenTypeEnum().getCode() == 2 && netRockIn.getCompanyBusEnum().getCode() == 1)
            return "";

        String result = managerHandle.update(netRockIn.getCompanyBusEnum().getCode().toString(), netRockIn);

        return result;
    }


    /**
     *remark: 清空所有开门用户
     *@author:chenj
     *@date: 2019/11/29
     *@return void
     */
    public  void clearAll(NetRock netRockIn) throws Exception {
        if (null == netRockIn)
            throw  new RuntimeException(ErrMsgEnum.ERR_OBJECTISNULL.getMsg());
        ManagerHandle managerHandle = new ManagerHandle();

        if(StringUtils.isBlank(netRockIn.getDomain()))
            throw  new RuntimeException(ErrMsgEnum.ERR_DOMAIN_NULL.getMsg());
        if (null == netRockIn.getCompanyBusEnum())
            throw  new RuntimeException(ErrMsgEnum.EER_COMANY_NO.getMsg());

        if (netRockIn.getCompanyBusEnum().getCode() == 1)
            return;


        managerHandle.clearAll(netRockIn.getCompanyBusEnum().getCode().toString(), netRockIn);
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
