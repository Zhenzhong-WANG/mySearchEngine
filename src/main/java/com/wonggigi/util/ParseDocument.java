package com.wonggigi.util;

import com.wonggigi.entity.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
    private static String getProtocol(String url){
        return url.split(":")[0];
    }
    private static String getDomainName(String url){
        return url.split("//")[1].split("/")[0];
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
        }
        Document d=new Document();
        String filePath=basePath+docId+".txt";
        System.out.println(docId+" , "+url);
        createFile(filePath,documentStr);

        pattern=Pattern.compile("href=\"(.*?)\"");
        matcher=pattern.matcher(documentStr);
        int i=0;
        ArrayList<String> link=new ArrayList<String>();
        while (matcher.find()&&i<30){
            String tempLink=matcher.group(1);
            Pattern tempPattern=Pattern.compile("\\.png|javascript:|\\.xml|\\.ico|\\.css|\\.svg|\\+|>|\\.jpg|\\.apk|\\.gif|\\{");
            Matcher tempMatcher=tempPattern.matcher(tempLink);
            if ((!tempMatcher.find())&&tempLink.length()>2){
                if (tempLink.indexOf(":")==-1)
                    tempLink=getProtocol(url)+"://"+getDomainName(url)+tempLink;
                if (tempLink.charAt(tempLink.length()-1)!='/')
                    tempLink=tempLink+'/';
                link.add(tempLink);
                i++;
            }

        }
        d.setLink(link);
        d.setPath(filePath);
        d.setTitle(title);
        d.setUrl(url);
        d.setId(docId);
        return d;
    }
}
