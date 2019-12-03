package com.rh.netrock.util;

import com.rh.netrock.enums.ErrMsgEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

}
