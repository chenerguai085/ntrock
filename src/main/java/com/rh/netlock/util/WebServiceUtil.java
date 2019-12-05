package com.rh.netlock.util;


import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import org.apache.commons.httpclient.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/26
 */
public class WebServiceUtil {

    public static Map<String, Object> websReq(String xml, String domain) throws Exception {
        // 服务器地址
        StringBuffer sb = new StringBuffer();
        Map<String, Object> resultMap = new HashMap();

        InputStream is = null;

        String urlPath = domain + LockEnum.HLS_URL_SUF.getMsg();

        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 拼接soap
            String soap = xml;
            byte[] buf = soap.getBytes("UTF-8");
            // 设置报头
            conn.setRequestProperty("Content-Length", String.valueOf(buf.length));
            conn.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream out = conn.getOutputStream();
            out.write(buf);
            out.close();
            // 获取响应状态码
            int code = conn.getResponseCode();

            if (code == HttpStatus.SC_OK) {
                is = conn.getInputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = is.read(b)) != -1) {
                    String s = new String(b, 0, len, "utf-8");
                    sb.append(s);
                }
                is.close();
            } else {
                throw new Exception(ErrMsgEnum.REMOTE_ERRMSG.getMsg() + "第三方返回http状态码" + code);
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception(ErrMsgEnum.REMOTE_ERRMSG.getMsg());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        resultMap = ParseUtil.parseXml(sb.toString());

        return resultMap;
    }


}
