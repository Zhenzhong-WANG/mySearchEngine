package com.wonggigi.util;

import com.wonggigi.entity.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * Created by handle on 17-1-1.
 */

public class Controller {
    private ArrayList<String> unvisitUrl=new ArrayList<String>();
    private Hashtable<String,Integer> hashtable=new Hashtable<String,Integer>();
    private Hashtable<String,ArrayList<Integer>> parentPageHashtable=new Hashtable<String,ArrayList<Integer>>();
    private int threadNum=3;
    private int limitNum=50;
    private int docId=0;
    private void geneTransferMatrix(){
        try {
            Set<String> keys=parentPageHashtable.keySet();
            Hashtable<String,String> tempHashtable=new Hashtable<String, String>();
            File file = new File(".//TransferMatrix//TransferMatrix.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String key:keys){
                if (hashtable.get(key)!=null){
                    ArrayList<Integer> arrayList=parentPageHashtable.get(key);
                    for (int i=0;i<arrayList.size();i++){
                        String tempKey=arrayList.get(i)+"";
                        if (!tempHashtable.containsKey(tempKey))
                            tempHashtable.put(tempKey,hashtable.get(key)+"");
                        else{
                            String tempList=tempHashtable.get(tempKey);
                            tempList=tempList+"/"+hashtable.get(key);
                            tempHashtable.put(tempKey,tempList);
                        }
                    }
                }
            }
            keys=tempHashtable.keySet();
            for (String key:keys){
                bw.write(key+" "+tempHashtable.get(key));
                bw.newLine();
            }
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void start()  throws InterruptedException{

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        final Word word=(Word)context.getBean("wordBean");
     //   unvisitUrl.add("https://tieba.baidu.com/index.html");
     //   unvisitUrl.add("http://www.ifeng.com/");
     //   unvisitUrl.add("http://www.sina.com.cn/");
     //   unvisitUrl.add("http://www.qq.com/");
        unvisitUrl.add("https://www.zhihu.com/explore/recommendations");


        for (int i=0;i<threadNum;i++){
            Thread threadCrawler = new Thread(new Runnable() {
                public void run() {
                    while (true){
                        String url=getUrl();
                        if (url=="")break;
                        String documentStr=Spider.get(url);
                        Document document=ParseDocument.parse(documentStr,url);
                        String content=document.getContent();
                        if (content.length()>220&&!hashtable.containsKey(url)){
                            int docid=DispatchDocId();
                            if (docid>limitNum)break;
                            hashtable.put(url,docid);
                            System.out.println(docid+" , "+url+" : "+content);
                            ParseDocument.createFile(document,docid);
                            word.segment(document,docid);
                            ArrayList<String> linkList=document.getLink();
                            for (int j=0;j<linkList.size();j++){
                                String link=linkList.get(j);
                                if (!hashtable.containsKey(link)) {
                                    unvisitUrl.add(link);
                                }
                                if (parentPageHashtable.containsKey(link)){
                                    ArrayList<Integer> parentList=parentPageHashtable.get(link);
                                    if (parentList.indexOf(docid)==-1)
                                        parentList.add(docid);
                                    parentPageHashtable.put(link,parentList);
                                }else{
                                    ArrayList<Integer> parentList=new ArrayList<Integer>();
                                    parentList.add(docid);
                                    parentPageHashtable.put(link,parentList);
                                }
                            }
                        }
                    }
                }
            });
            threadCrawler.start();
            threadCrawler.join();
        }
        System.out.println("Gene Transfer Matrix");
        geneTransferMatrix();
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
