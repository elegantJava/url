package com.wangz.url.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName WeatherUtil
 * @Auther: wangz
 * @Date: 2019/6/8 11:22
 * @Description: TODO
 */
public class WeatherUtil {
//    private static final String SERVICES_HOST = "www.webxml.com.cn";// 主机
    private static final String WEATHER_SERVICES_URL = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx/";
    // 获取天气
    private static final String WEATHER_QUERY_URL = WEATHER_SERVICES_URL+ "getWeather?theUserID=&theCityCode=";


    public static List<String> getWeathers(int cityCode) {
        //定义一个集合装载添加数据
        List<String> weatherList = new ArrayList<String>();
        /*获取xml文档对象*/
        Document document;
        /*获取解析工厂*/
        DocumentBuilderFactory documentBF = DocumentBuilderFactory.newInstance();
        //设定命名空间
        documentBF.setNamespaceAware(true);
        try {
            //初始xml工厂
            DocumentBuilder db = documentBF.newDocumentBuilder();
            //获取天气数据接口返回的数据
            URL url = new URL(WEATHER_QUERY_URL + cityCode);
            InputStream inputStream = url.openStream();
            //开始解析
            document = db.parse(inputStream);
            //解析string节点
            NodeList nl = document.getElementsByTagName("string");

            int len = nl.getLength();
            for (int i = 0; i < len; i++) {
                Node n = nl.item(i);//获取每一个string节点

                String weather = n.getFirstChild().getNodeValue();
                weatherList.add(weather);
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherList;
    }


    public static void main(String[] args) {
        List<String> strings = WeatherUtil.getWeathers(1566);
        System.out.println(strings);
    }
}
