package com.rh.netrock.util;


import com.rh.netrock.enums.ErrMsgEnum;
import com.rh.netrock.enums.RockEnum;
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

    public static Map<String, Object> websReq(String xml,String domain) {
        // 服务器地址
        StringBuffer sb = new StringBuffer();
        Map<String, Object> resultMap = new HashMap();

        InputStream is = null;

        String urlPath =  domain + RockEnum.HLS_URL_SUF.getMsg();

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
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(ErrMsgEnum.REMOTE_ERRMSG.getMsg());
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


    public static void main(String[] args) throws IOException {


        String string = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><NetLockWeb xmlns=\"http://tempuri.org/\"><recdatastr>\n";


        Map<String,Object> map = new HashMap<>();
        map.put("RoomId","331");
        map.put("CardType","3");
        map.put("CardData","111111");
        map.put("BeginTime","2019-11-28 11:00:00");

        map.put("EndTime","2019-12-03 11:00:00");

        String domain = "http://121.201.67.205:8007";

        String  str = ParseUtil.buildXml("Add_OpenUser_REQ", map);

        System.out.println("str" + str);
        WebServiceUtil.websReq(str,domain);
        String  s = "&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;&lt;Command name=\"Add_OpenUser_RESP\" sn=\"6\"";
      String a  =   s.replaceAll("&gt;",">").replaceAll("&lt;","<");
      String  t =    a.replaceAll("&lt;","<");

         System.out.println(t);

    }




}
