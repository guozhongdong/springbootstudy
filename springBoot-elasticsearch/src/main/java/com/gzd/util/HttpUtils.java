package com.gzd.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gzd
 * @create 2018-12-24 20:04
 * @desc
 **/
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    private static int CON_TIME_OUT = 60000;
    private static int READ_TIME_OUT = 120000;

    /**
     * get 请求在url后拼接参数
     * @return 返回接口字符串
     * */
    public static String doGet(String url,Map<String,Object> params){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CON_TIME_OUT)
                .setConnectionRequestTimeout(READ_TIME_OUT)
                .setSocketTimeout(5000)
                //默认允许自动重定向
                .setRedirectsEnabled(true)
                .build();
        StringBuffer sb = new StringBuffer();
        if(params!=null){
            for (Map.Entry<String, Object> e : params.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
            sb.substring(0, sb.length() - 1);
        }
        if(!"".equals(sb.toString().trim())){
            url = url+"?"+sb.substring(0, sb.length() - 1).toString();
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String srtResult = "";
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println(httpResponse);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                //获得返回的结果
                srtResult = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(srtResult);
                return srtResult;
            }else if(httpResponse.getStatusLine().getStatusCode() == 400){
                logger.error("请求接口异常:------->"+httpResponse.getStatusLine().getStatusCode());
                throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());
            }else if(httpResponse.getStatusLine().getStatusCode() == 500){
                logger.error("请求接口异常:------->"+httpResponse.getStatusLine().getStatusCode());
                throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * get 请求在url后拼接参数
     * @return 返回接口字符串
     * */
    public static String sendGet(String url,String params){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                //设置连接超时时间
                .setConnectTimeout(CON_TIME_OUT)
                // 设置请求超时时间
                .setConnectionRequestTimeout(READ_TIME_OUT)
                .setSocketTimeout(5000)
                //默认允许自动重定向
                .setRedirectsEnabled(true)
                .build();
        url = url+params;
        System.out.println(url);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        String srtResult = "";
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            System.out.println(httpResponse);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                //获得返回的结果
                srtResult = EntityUtils.toString(httpResponse.getEntity());
                System.out.println(srtResult);
                return srtResult;
            }else if(httpResponse.getStatusLine().getStatusCode() == 400){
                logger.error("请求接口异常:------->"+httpResponse.getStatusLine().getStatusCode());
                throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());
            }else if(httpResponse.getStatusLine().getStatusCode() == 500){
                logger.error("请求接口异常:------->"+httpResponse.getStatusLine().getStatusCode());
                throw new ClientProtocolException("Unexpected response status: " + httpResponse.getStatusLine().getStatusCode());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    /**
     * 处理请求为key--value 格式的参数
     * @param url 请求地址url
     * @param params 请求参数
     * @return 返回接口字符串
     * */
    public static String doPost(String url, Map<String, Object> params){
        // 创建httpclient请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(CON_TIME_OUT)
                .setConnectionRequestTimeout(READ_TIME_OUT)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
        if(params!=null){
            for (Map.Entry<String, Object> e : params.entrySet()) {
                //请求参数
                list.add(new BasicNameValuePair(String.valueOf(e.getKey()), String.valueOf(e.getValue())));
            }
        }
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");

            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String strResult = "";
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
                return strResult;
            } else {
                return "Error Response: " + httpResponse.getStatusLine().toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            return "post failure :caused by-->" + e.getMessage();
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * post请求 ，请求参数为json字符串
     * */
    public static String sendPost(String url, String param) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        //创建httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(param, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;

        try {

            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString;
            }
            else{
                logger.error("请求返回:"+state+"("+url+")");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args){

        HashMap hashMap = new HashMap<>();
        hashMap.put("name","gzd");
        hashMap.put("age","123");
        //String s  = doGet("http://localhost:8090/strategy-preference/test3/restful",hashMap);
        //String s1  = doPost("http://localhost:8090/strategy-preference/test4",hashMap);
        JSON json = new JSONObject();
        String s3 = json.toJSONString(hashMap);
        System.out.println(s3);
        String s2  = sendPost("http://localhost:8081/test/test",s3);
        System.out.println(s2);
    }
}
