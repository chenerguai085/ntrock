package com.rh.netrock.util;

import com.rh.netrock.enums.RockEnum;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:chenj
 * @remark:
 * @date: 2019/11/29
 */
public class ParseUtil {


    /**
     * remark: 封装webservice 请求xml数据
     *
     * @return java.lang.String
     * @author:chenj
     * @date: 2019/11/26
     */
    public static String buildXml(String action, Map<String, Object> map) {

        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body>\n" +
                " <NetLockWeb xmlns=\"http://tempuri.org/\"><recdatastr>");

        sb.append("&lt;Command name=\"" + action + "\" " + " " + " sn=\"1\" version=\"1.0.0\"&gt;");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            sb.append("&lt;" + key + "&gt;" + value + "&lt;/" + key + "&gt;");

        }

        sb.append("&lt;/Command&gt;");
        sb.append("</recdatastr>\n" +
                "  </NetLockWeb>\n" +
                "  </soap:Body>\n" +
                "  </soap:Envelope>");

        return sb.toString();
    }

    /**
     * remark: 解析返回的xml 字符串
     *
     * @return java.util.Map
     * @author:chenj
     * @date: 2019/11/29
     */
    public static Map parseXml(String soapXml) {

        //将&gt; 替换成>
        String xmlStr = soapXml.replaceAll("&gt;", ">").replaceAll("&lt;", "<");

        String xmlResult = xmlStr.substring(xmlStr.indexOf(RockEnum.XMLPARSE_PRE.getMsg()) - 1, xmlStr.indexOf(RockEnum.XMLPARSE_SUF.getMsg()) + RockEnum.XMLPARSE_SUF.getMsg().length());

        Map<String, Object> map = new HashMap<>();
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlResult);
        } catch (DocumentException e) {
            map.put("resultID", "1");
            map.put("description", "解析异常");
            e.printStackTrace();
        }
        Element root = doc.getRootElement();// 指向根节点
        Element resultId = root.element("ResultID");
        Element description = root.element("Description");

        map.put("resultID", resultId.getTextTrim());
        map.put("description", description.getTextTrim());

        return map;
    }
}
