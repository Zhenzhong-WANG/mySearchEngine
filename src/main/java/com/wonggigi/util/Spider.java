package com.wonggigi.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hanoi on 2016/12/14.
 */
public class Spider {
    private int id;
    public  void setId(int id){
        this.id=id;
    }
    public static String get(String url){
        String result = "";
        try {
            //获取httpclient实例
            CloseableHttpClient httpclient = HttpClients.createDefault();
            //获取方法实例。GET
            HttpGet httpGet = new HttpGet(url);
            //执行方法得到响应
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                //如果正确执行而且返回值正确，即可解析
                if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    HttpEntity entity = response.getEntity();
                    //从输入流中解析结果

                    String contentType=entity.getContentType().toString();
                    Pattern pattern = Pattern.compile("charset=(.*)");
                    Matcher matcher = pattern.matcher(contentType);
                    String charset="utf-8";

                    if (matcher.find( )) {
                        charset = matcher.group(1);
                    }

                    System.out.println(response.getStatusLine()+" Charset :"+charset);

                    result = readResponse(entity,charset);
                }
            } finally {
                httpclient.close();
                response.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    private static String readResponse(HttpEntity resEntity, String charset) {
        StringBuffer res = new StringBuffer();
        BufferedReader reader = null;
        try {
            if (resEntity == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(resEntity.getContent(), charset));
            String line = null;

            while ((line = reader.readLine()) != null) {
                res.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

}
