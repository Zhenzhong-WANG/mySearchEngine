package com.wonggigi.util;

import com.wonggigi.entity.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by handle on 17-1-1.
 */

public class Controller {
    private ArrayList<String> unvisitUrl=new ArrayList<String>();
    private Hashtable<String,Integer> hashtable=new Hashtable<String,Integer>();

    private int threadNum=10;
    private int docId=0;
    private void genePRmatrix(int id,ArrayList<Integer> linkList){
        try{
            File file = new File(".//PRmatrix//PRmatrix.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(id+"/");
            for (int i=0;i<linkList.size();i++){
                bw.write(linkList.get(i)+" ");
            }
            bw.newLine();
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void start(){
    //    unvisitUrl.add("https://tieba.baidu.com/index.html");
    //   unvisitUrl.add("http://www.ifeng.com/");
      //  unvisitUrl.add("http://www.sina.com.cn/");
   //     unvisitUrl.add("http://www.qq.com/");
//        unvisitUrl.add("https://www.zhihu.com/explore");

        for (int i=0;i<unvisitUrl.size();i++){
            hashtable.put(unvisitUrl.get(i),getDocId());
        }

        for (int i=0;i<threadNum;i++){
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true){
                        String url=getUrl();
                        if (url=="")break;
                        int docid=hashtable.get(url);
                        if (docid>40)break;
                        String s=Spider.get(url);
                        Document document=ParseDocument.parse(s,url,docid);

                        ArrayList<String> link=document.getLink();
                        ArrayList<Integer> linkList=new ArrayList<Integer>();
                        for (int j=0;j<link.size();j++){
                            String tempUrl=link.get(j);
                            if (!hashtable.containsKey(tempUrl)){
                                hashtable.put(tempUrl,getDocId());
                                unvisitUrl.add(tempUrl);
                            }
                            linkList.add(hashtable.get(tempUrl));
                        }
                        genePRmatrix(docid,linkList);
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
