package com.wonggigi.util;

import com.wonggigi.entity.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hanoi on 2016/12/14.
 */
public class ParseDocument {
    private int id;
    private static String basePath=".//documents//";
    public  void setId(int id){
        this.id=id;
    }

    private static void createFile(String filePath,String documentStr){
        try{
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);


            String content=documentStr.replaceAll("<script[^>]*?>.*?</script>","").replaceAll("<style[^>]*?>.*?</style>","").replaceAll("<!--[^-]*?-->","");
            content=content.replaceAll("<.*?>","");
            content=content.replaceAll("[\\]\\(\\)\\+\\[\\$\\?\\{\\}\\*\\|\\^\\&\\.\\t\\n\\r\\s]","");

            System.out.println("content: "+content);
            bw.write(content);
            bw.newLine();

            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static Document parse(String documentStr,String url,int docId){
        Pattern pattern = Pattern.compile("<title>(.*?)</title>");
        Matcher matcher = pattern.matcher(documentStr);
        String title="";
        if (matcher.find( )) {
            title=matcher.group(1);
          //  System.out.println("1: "+title);
        }
        Document d=new Document();
        String filePath=basePath+docId+".txt";
        createFile(filePath,documentStr);
        d.setPath(filePath);
        d.setTitle(title);
        d.setUrl(url);
        d.setId(docId);
        return d;
    }
}
