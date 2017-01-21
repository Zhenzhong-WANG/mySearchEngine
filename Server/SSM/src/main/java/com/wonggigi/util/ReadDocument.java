package com.wonggigi.util;

import com.wonggigi.entity.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by handle on 17-1-17.
 */
public class ReadDocument {
    private static String filePath="/home/Projects/SearchEngine/documents/";

    public static Document getContent(Document document,String[] keyWords){
        try {
            String result="";
            File file=new File(filePath+document.getId()+".txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String content="";
                while((lineTxt = bufferedReader.readLine()) != null){
                    content+=lineTxt;
                }

                String title=document.getTitle();
                for (String word:keyWords){
                    content=content.replaceAll(word,"<em>"+word+"</em>");
                    title=title.replaceAll(word,"<em>"+word+"</em>");
                }
                document.setTitle(title);
                document.setContent(content);
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return document;
    }
}
