package com.wonggigi;

import com.wonggigi.entity.Document;
import com.wonggigi.util.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/**
 * Hello world!
 *
 */

public class App 
{
    private static ArrayList<String> unvisitUrl=new ArrayList<String>();
    private static Hashtable<String,Integer> hashtable=new Hashtable<String,Integer>(); //url,docId
    private static Hashtable<String,ArrayList<Integer>> parentPageHashtable=new Hashtable<String,ArrayList<Integer>>(); //url indegreeDocs
    private static int threadNum=3;
    private static int limitNum=10;
    private static int docId=0;

    public static void main ( String[] args ) throws InterruptedException
    {
        //Dictionary.build();
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
                            if (docid>limitNum-1)break;
                            hashtable.put(url,docid);
                            System.out.println(docid+" , "+url+" : "+content);
                            ParseDocument.createFile(document,docid);
                            addToDb(document,docid);
                            Word.segment(document,docid);
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
        System.out.println("Gene Transfer Matrix...");
        geneTransferMatrix();
    }
    private static void geneTransferMatrix(){
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
                    if (arrayList.indexOf(hashtable.get(key))==-1)
                        arrayList.add(hashtable.get(key));
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
            System.out.println(limitNum+" "+1.0/limitNum);
            double probability=1.0/limitNum;
            keys=tempHashtable.keySet();
            for (String key:keys){
                bw.write(probability+" "+key+" "+tempHashtable.get(key));
                bw.newLine();
            }
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public static synchronized int DispatchDocId(){
        return docId++;
    }
    public static synchronized String getUrl(){
        if (unvisitUrl.size()==0)return "";
        String url=unvisitUrl.get(0);
        unvisitUrl.remove(0);
        return url;
    }
    public static synchronized void addToDb(Document document,int id){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/SearchEngine?useUnicode=true&characterEncoding=UTF-8";    //JDBC的URL
            Connection conn;
            conn = DriverManager.getConnection(url,"root","908868432");
            String sql = "INSERT INTO Documents VALUES(?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,id);;
            pst.setString(2,document.getUrl());
            pst.setString(3,document.getTitle());
            pst.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.out.println("出错");
            e.printStackTrace();
        }
    }
}
