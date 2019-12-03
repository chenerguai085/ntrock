package com.rh.netrock.util;

import com.rh.netrock.entity.Token;
import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.RockEnum;
import com.rhcj.commons.JsonHelper;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *remark:
 *@author:chenj
 *@date: 2019/11/15
 *@return
 */
public class HttpUtil {
    /**
     * post请求（用于请求json格式的参数）
     * @param
     * @param
     * @return
     */
    public static String doJsonPost(String uplPath, String Json) {

        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(uplPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("code","hzrw-001");

            // 往服务器里面发送数据
            if (Json != null) {
                byte[] writebytes = Json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(Json.getBytes());
                outwritestream.flush();
                outwritestream.close();
            }

            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException(ErrMsgEnum.REMOTE_ERRMSG.getMsg());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }



    /**
     *remark: 携住添加人脸
     *@author:chenj
     *@date: 2019/11/28
     *@return java.lang.String
     */
    public static String postXzAddFace(String urlPath, String jsonMap, Date now,String domain,String tokenType,String accessToken) throws Exception {
        String result = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");


            conn.setRequestProperty("Authorization", tokenType + " " + accessToken);

            // 往服务器里面发送数据
            if (jsonMap != null) {
                byte[] writebytes = jsonMap.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(jsonMap.getBytes());
                outwritestream.flush();
                outwritestream.close();
            }

            if (conn.getResponseCode() == 200) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                result = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("请求人脸录入接口异常:" + e.getMessage() );
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }



    /**
     * remark:获取token请求
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/23
     */
    public static String postGetToken(String url, Map<String, Object> params) throws Exception {
        PostMethod postMethod = null;
        postMethod = new PostMethod(url);

        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            String value = (String) params.get(key);
            postMethod.addParameter(key, value);
        }

        HttpClient httpClient = new HttpClient();

        httpClient.executeMethod(postMethod);
        String response = postMethod.getResponseBodyAsString();

        return response;
    }


}
