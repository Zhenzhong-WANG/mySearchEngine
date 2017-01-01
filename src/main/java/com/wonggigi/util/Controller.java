package com.wonggigi.util;

import com.wonggigi.entity.Document;

import java.util.ArrayList;

/**
 * Created by handle on 17-1-1.
 */

public class Controller {
    private ArrayList<String> unvisitUrl=new ArrayList<String>();

    private int threadNum=10;
    private int docId=0;
    public void start(){
        unvisitUrl.add("https://tieba.baidu.com/index.html");
        unvisitUrl.add("http://www.ifeng.com/");
        unvisitUrl.add("http://www.sina.com.cn/");
        unvisitUrl.add("http://www.qq.com/");
        unvisitUrl.add("https://www.zhihu.com/explore");

        for (int i=0;i<threadNum;i++){
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true){
                        String url=getUrl();
                        if (url=="")break;
                        String s=Spider.get(url);
                        int docid=getDocId();
                        Document document=ParseDocument.parse(s,url,docid);
                    }
                }
            });
            t.start();
        }
    }
    public synchronized int getDocId(){
        return docId++;
    }
    public synchronized String getUrl(){
        if (unvisitUrl.size()==0)return "";
        String url=unvisitUrl.get(0);
        unvisitUrl.remove(0);
        return url;
    }
}
