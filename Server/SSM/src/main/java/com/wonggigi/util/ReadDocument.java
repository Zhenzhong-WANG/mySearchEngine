package com.wonggigi.util;

import com.wonggigi.entity.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by handle on 17-1-17.
 */
public class ReadDocument {
    private static String filePath="/home/Projects/SearchEngine/documents/";

    public static Document getContent(Document document){
        try {
            File file=new File(filePath+document.getId()+".txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                String result="";
                while((lineTxt = bufferedReader.readLine()) != null){
                    result+=lineTxt;
                }
                document.setContent(result.substring(0,110));
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
