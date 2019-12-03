package com.rh.netrock.handle;

import com.rh.netrock.enums.CompanyBusEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/20
 */
public class HandleSingleton {

    /*
     * 单例模式
     * */
    private static HandleSingleton instance=null;

    private HandleSingleton(){
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new HandleSingleton();
        }
    }

    public static HandleSingleton getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    //使用map 存放 参数-类路径  的信息
    private static Map<String,String> strategyMap = new HashMap<>();
    //使用静态块存放
    static{

        for (CompanyBusEnum t : CompanyBusEnum.values()){
            strategyMap.put(t.getCode().toString(), t.getClazz());}
    }
    public String strategy(String type){
        return strategyMap.get(type);
    }


}
