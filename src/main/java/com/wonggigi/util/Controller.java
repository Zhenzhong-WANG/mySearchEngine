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
                        String documentStr=Spider.get(url);
                        Document document=ParseDocument.parse(documentStr,url);
                        String content=document.getContent();
                        if (content.length()>220&&!hashtable.containsKey(url)){
                            int docid=DispatchDocId();
                            if (docid>220)break;
                            hashtable.put(url,docid);
                            System.out.println(docid+" , "+url+content);
                            ParseDocument.createFile(document,docid);
                            ArrayList<String> linkList=document.getLink();

                            for (int j=0;j<linkList.size();j++){
                                String link=linkList.get(j);
                                if (!hashtable.containsKey(link)) {
                                    unvisitUrl.add(link);
                                }
                            }
                        }
                    }
                }
            });
            t.start();
        }
    }
    public synchronized int DispatchDocId(){
        return docId++;
    }
    public synchronized String getUrl(){
        if (unvisitUrl.size()==0)return "";
        String url=unvisitUrl.get(0);
        unvisitUrl.remove(0);
        return url;
    }
}
