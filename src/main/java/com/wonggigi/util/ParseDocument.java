package com.wonggigi.util;

import com.wonggigi.entity.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hanoi on 2016/12/14.
 */
public class ParseDocument {
    private int id;
    public  void setId(int id){

        this.id=id;
    }
    public Document parse(String documentStr,String url){

        Pattern pattern = Pattern.compile("<title>(.*?)</title>");
        Matcher matcher = pattern.matcher(documentStr);
        String title="";
        if (matcher.find( )) {
            title=matcher.group(1);
            System.out.println("1: "+title);
        }
        Document d=new Document();
        d.setContent(documentStr);
        d.setTitle(title);
        d.setUrl(url);
        return d;
    }
}
