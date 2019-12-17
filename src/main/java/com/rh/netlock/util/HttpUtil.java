package com.rh.netlock.util;

import com.alibaba.fastjson.JSONObject;
import com.rh.netlock.entity.Token;
import com.rh.netlock.enums.ErrMsgEnum;
import com.rh.netlock.enums.LockEnum;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * remark:
 *
 * @author:chenj
 * @date: 2019/11/15
 * @return
 */
public class HttpUtil {
    /**
     * remark:httpclient 发送post请求
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/12
     */
    public static String doJsonPost(String json, Map<String, String> headersMap, String url) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String res = "";
        try {
            Iterator<Map.Entry<String, String>> headersIt = null;
            if (null != headersMap) {
                headersIt = headersMap.entrySet().iterator();
                while (headersIt.hasNext()) {
                    Map.Entry<String, String> entry = headersIt.next();
                    post.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }
            post.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
            CloseableHttpResponse response = client.execute(post);

            HttpEntity entity = response.getEntity();
            res = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            response.close();
            client.close();
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ": " + e.getMessage());
        }

        return res;
    }

    public static String hongrCloudJsonPost(String json, String url) throws Exception {
        CloseableHttpClient httpClient = null;

        HttpPost httpPost = null;

        String res = "";
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost(url);
            httpClient.execute(httpPost);
            List<Cookie> cookies = cookieStore.getCookies();

            String crsfToken = null;
            Date expiryDate = null;
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("csrf_token")) {
                    crsfToken = cookies.get(i).getValue();
                }

            }
            httpPost.addHeader("Content-Type", "application/json");
            httpPost = new HttpPost(LockEnum.HONGRFACE_DOMAIN.getMsg().concat(LockEnum.HONGR_FACE_LOGIN_API.getMsg()));
            httpPost.addHeader("X-CSRFToken", crsfToken);
            httpPost.addHeader("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("company", LockEnum.HONGR_FACE_COMPANY.getMsg());
            jsonObject.put("name", LockEnum.HONGR_FACE_USERNAME.getMsg());
            jsonObject.put("password", LockEnum.HONGR_FACE_PASSWORD.getMsg());

            httpPost.setEntity(new StringEntity(jsonObject.toString(), Charset.forName("UTF-8")));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            res = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            if (!JsonHelper.isJson(res)) {
                throw new Exception("登入失败： " + ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
            }
            JSONObject respJson = JSONObject.parseObject(res);
            if (respJson.getString("code").equals("0")) {
                httpPost = new HttpPost(url);
                httpPost.addHeader("X-CSRFToken", crsfToken);
                httpPost.addHeader("Content-Type", "application/json");

                httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
                response = httpClient.execute(httpPost);
                entity = response.getEntity();

                res = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            }

            response.close();
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception(ErrMsgEnum.ERR.getMsg() + ": " + e.getMessage());
        }

        System.out.println(res);
        return res;
    }


    /**
     * remark: key value 的形式发送json请i去
     *
     * @return void
     * @author:chenj
     * @date: 2019/12/12
     */
    public static String doParamsPost(Map<String, Object> paramsMap, Map<String, Object> headersMap, String url) throws Exception {
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        String res = "";
        try {
            Iterator<Map.Entry<String, Object>> headersIt = null;
            if (null != headersMap) {
                headersIt = headersMap.entrySet().iterator();

                while (headersIt.hasNext()) {
                    Map.Entry<String, Object> entry = headersIt.next();

                    post.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }

            List<NameValuePair> params = new ArrayList<>();
            Iterator<Map.Entry<String, Object>> paramsIt = null;
            if (null != paramsMap) {
                paramsIt = paramsMap.entrySet().iterator();

                while (paramsIt.hasNext()) {
                    Map.Entry<String, Object> entry = paramsIt.next();

                    params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }

            post.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
            HttpResponse response = client.execute(post);
            res = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("请求异常");
        }

        return res;
    }


    /**
     * remark:
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/4
     */
    public static String yundingPost(String urlPath, String json) throws Exception {
        String result = "";
        BufferedReader reader = null;

        System.out.println(urlPath);
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
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("User-Agent", "ddingnet-3.0");
            // 往服务器里面发送数据
            if (json != null) {
                byte[] writebytes = json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(json.getBytes());
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
            throw new RuntimeException(ErrMsgEnum.REMOTE_ERRMSG.getMsg());
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
     * remark: httpClient 发送get请求
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/12/12
     */
    private static String httpClientGet(Map<String, Object> paramsMap, Map<String, Object> headersMap, String url) throws Exception {
        String result = null;
        //请求地址
        //获取请求参数
        List<NameValuePair> parame = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, Object>> paramsIt = null;
        if (null != paramsMap) {
            paramsIt = paramsMap.entrySet().iterator();

            while (paramsIt.hasNext()) {
                Map.Entry<String, Object> entry = paramsIt.next();

                parame.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String parameStr = null;
        try {
            parameStr = EntityUtils.toString(new UrlEncodedFormEntity(parame));
            //拼接参数
            StringBuffer sb = new StringBuffer();
            sb.append(url);
            sb.append("?");
            sb.append(parameStr);
            //创建get请求
            HttpGet httpGet = new HttpGet(sb.toString());
            // 设置请求和传输超时时间
//            RequestConfig requestConfig = RequestConfig.custom()
//                    .setSocketTimeout(2000).setConnectTimeout(2000).build();
//            httpGet.setConfig(requestConfig);
            // 提交参数发送请求
            response = httpclient.execute(httpGet);

            // 得到响应信息
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
            EntityUtils.consume(entity);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭所有资源连接
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }





    /**
     *remark: 虹软云端版本  登入  因为后续的接口都是需要登入之后才能访问的  登入过期12小时
     *@author:chenj
     *@date: 2019/12/17
     *@return com.rh.netlock.entity.Token
     */
    public static Token login(String json, String url) throws Exception {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String res = "";
        Token token = new Token();
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpPost = new HttpPost(url);
            httpClient.execute(httpPost);

            //cookieStore.getCookies().forEach(cookie -> Collection.);
            List<Cookie> cookies = cookieStore.getCookies();
            String crsfToken = null;
            Date expiryDate = null;
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("csrf_token")) {
                    crsfToken = cookies.get(i).getValue();
                }
                if (cookies.get(i).getName().equals("session")) {
                    expiryDate = cookies.get(i).getExpiryDate();
                }
            }
            httpPost = new HttpPost(url);
            httpPost.addHeader("X-CSRFToken", crsfToken);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            res = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            if (!JsonHelper.isJson(res)) {
                throw new Exception(ErrMsgEnum.REMOTE_RESP_ERRJSON.getMsg());
            }

            JSONObject respJson = JSONObject.parseObject(res);
            if (respJson.getString("code").equals("0")) {
                //登入成功 返回tonken 和过期时间
                token.setAccessToken(crsfToken);
                token.setExpiresDate(expiryDate);
            }
            response.close();
            httpClient.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }

        return token;
    }

}
