package com.rh.netrock.util;


import com.rh.netrock.entity.Token;
import com.rh.netrock.enums.RockEnum;
import com.rhcj.commons.DateHelper;
import com.rhcj.commons.JsonHelper;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/20
 */
public class CacheToken {
    private static ConcurrentHashMap<String, String> cacheTokenMap = new ConcurrentHashMap<>();

    volatile  static  int reqCount = 0;

    /**
     * remark: 获取缓存对象
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/20
     */
    public static Token getCacheToken(String userName, Map<String, Object> map, Date nowDate,String domain) throws Exception {

        // 如果缓冲中有该账号，则返回value
        if (cacheTokenMap.containsKey(userName)) {
            String tokenStr = cacheTokenMap.get(userName);

            Token cacheToken = getToken(tokenStr);
            //判断当前时间是否在 缓存中的过期日期之前 如果在之后那么清空缓存
            if (nowDate.after(cacheToken.getExpiresDate())){
                //清空缓存token信息 重新获取token
                removeCache(userName);
                //重新初始化token
                Token token = initCache(map,domain);

                return token;
            }

            return cacheToken;
        }

        // 如果缓存中没有该账号，把该帐号对象缓存到concurrentHashMap中
        Token initToken = initCache(map,domain);

        return initToken;
    }

    /**
     * 获取token
     * remark:
     *
     * @return com.rh.xzfacerock.Token
     * @author:chenj
     * @date: 2019/11/21
     */
    private static Token getToken(String tokenStr) {
        Token token = new Token();
        String[] tokenArr = tokenStr.split(",");

        token.setTokenType(tokenArr[0]);
        token.setAccessToken(tokenArr[1]);
        token.setExpiresDate(DateHelper.strToDate(tokenArr[2]));
        return token;
    }



    /**
     * 初始化缓存token
     *
     * @param
     */
    private static Token initCache(Map<String, Object> map,String domain) throws Exception {
        String  userName = map.get("username").toString();
        String password = map.get("password").toString();

        Token token = new Token();
        //调用获取token接口 把token缓存到
        String result = HttpUtil.postGetToken(domain + RockEnum.GET_TOKEN_API.getMsg(), map,userName, password);

        if (!JsonHelper.isJson(result)) {
            throw new RuntimeException("获取tokne信息异常,返回token信息非json格式字符串");
        }

        Map jsonMap = JsonHelper.toMap(result);
        String access_token = jsonMap.get("access_token").toString();
        String token_type = jsonMap.get("token_type").toString();
         userName = jsonMap.get("userName").toString();
        String expiresDateStr = jsonMap.get(".expires").toString();

        Date expiresDate = DateHelper.gmtToDate(expiresDateStr);
        if (null == expiresDate){

            throw new RuntimeException("获取tokne信息异常,返回日期格式非约定格式");
        }

        cacheTokenMap.put(userName, token_type + "," + access_token + "," + expiresDate);

        token.setTokenType(token_type);
        token.setAccessToken(access_token);
        token.setUserName(userName);

        token.setExpiresDate(expiresDate);
        return token;
    }

    /**
     * 移除缓存信息
     *
     * @param userName
     */
    public static void removeCache(String userName) {
        cacheTokenMap.remove(userName);
    }


}
