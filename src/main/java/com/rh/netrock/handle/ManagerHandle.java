package com.rh.netrock.handle;


import com.rh.netrock.entity.NetRock;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/20
 */
public class ManagerHandle<T> {

    /**
     * 添加开门用户
     *
     * @param type
     */
    public String add(String type, NetRock netRock) throws Exception {
        //调用单例类 获得类反射路径
        String classPath = HandleSingleton.getInstance().strategy(type);
        String result = "";

        Class classProcess = Class.forName(classPath);

        AbstractHandle d = (AbstractHandle) classProcess.newInstance();

        result = d.add(netRock);

        return result;
    }


    /**
     * remark:删除
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public void delete(String type, NetRock netRock) throws Exception {
        //调用单例类 获得类反射路径
        String classPath = HandleSingleton.getInstance().strategy(type);

        Class classProcess = Class.forName(classPath);

        AbstractHandle d = (AbstractHandle) classProcess.newInstance();

        d.delete(netRock);


    }

    /**
     * remark:清空
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public void clearAll(String type, NetRock netRock) throws Exception {
        //调用单例类 获得类反射路径
        String classPath = HandleSingleton.getInstance().strategy(type);

        Class classProcess = Class.forName(classPath);

        AbstractHandle d = (AbstractHandle) classProcess.newInstance();

        d.clearAll(netRock);

    }


    /**
     * remark:远程开门
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/29
     */
    public void remoteOpen(String type, NetRock netRock) throws Exception {
        //调用单例类 获得类反射路径
        String classPath = HandleSingleton.getInstance().strategy(type);

        Class classProcess = Class.forName(classPath);

        AbstractHandle d = (AbstractHandle) classProcess.newInstance();

        d.remoteOpen(netRock);

    }

    /**
     *remark: 更新
     *@author:chenj
     *@date: 2019/11/29
     *@return void
     */
    public String update(String type, NetRock netRock) throws Exception {
        //调用单例类 获得类反射路径
        String classPath = HandleSingleton.getInstance().strategy(type);

        Class classProcess = Class.forName(classPath);

        AbstractHandle d = (AbstractHandle) classProcess.newInstance();

        return  d.update(netRock);
    }

}
