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
    public static void createFile(Document document,int id){
        String filePath=basePath+id+".txt";
        try{
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            String content=document.getContent();
            bw.write(content);
            bw.newLine();
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static Document parse(String documentStr,String url){
        int i=0;
        Pattern pattern = Pattern.compile("<title[^>]*?>(.*?)</title>");
        Matcher matcher = pattern.matcher(documentStr);
        String title="";
        if (matcher.find( )) {
            title=matcher.group(1);
        }
        Document d=new Document();
        pattern=Pattern.compile("href=\"(.*?)\"");
        matcher=pattern.matcher(documentStr);
        ArrayList<String> link=new ArrayList<String>();
        while (matcher.find()&&i<30){
            String tempLink=matcher.group(1);
            Pattern tempPattern=Pattern.compile("mailto:|:\\d|\\.png|javascript:|\\.xml|\\.ico|\\.css|\\.svg|\\+|>|\\.jpg|\\.apk|\\.gif|\\{|##|\\.pdf|microsoft|google");
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

        String content=documentStr.replaceAll("<script[^>]*?>.*?</script>","").replaceAll("<style[^>]*?>.*?</style>","").replaceAll("<!--[^-]*?-->","");
        content=content.replaceAll("<.*?>|br|\\&lt|\\&amp|\\&gt|\\&quot|\\&bull","");
        content=content.replaceAll("[\\]\\(\\)\\+\\[\\$\\?\\{\\}\\*\\|\\^\\&\\.\\t\\n\\r\\s]","");

        d.setContent(content);
        d.setLink(link);
        d.setTitle(title);
        d.setUrl(url);
        return d;
    }
}
