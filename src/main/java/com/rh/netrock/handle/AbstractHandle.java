package com.rh.netrock.handle;

import com.rh.netrock.entity.NetRock;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/20
 */
public  abstract class AbstractHandle {

    public  String  add(NetRock netRock) throws Exception {
        return  "";
    };



    public  void  delete(NetRock netRock) throws Exception {

    };


    public  void   clearAll(NetRock netRock) throws Exception {

    };

    public  void remoteOpen(NetRock netRock) throws Exception{};

    public  String update(NetRock netRock) throws Exception{return "";};
}
