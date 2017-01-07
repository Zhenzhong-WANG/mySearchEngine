package com.wonggigi.util;

import java.io.*;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hanoi on 2016/12/17.
 */
public class Dictionary {
    private String chineseFilePath=".//Chinese.txt";
    private String englishFilePath=".//English.txt";
    private int mod=349;
    private void  inputFile(int fileId,String word){
        System.out.println(word);
        try{
            File file = new File(".//dictionary//dictionary"+fileId+".txt");

            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(word+" ");
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public boolean search(String word){
        char code=' ';
        for (int j=0;j<word.length();j++){
            code+=word.charAt(j);
        }
        int fileId=code%mod;
      //  System.out.println(fileId+" , "+word);
        try {
            File file = new File(".//dictionary//dictionary"+fileId+".txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    Pattern pattern = Pattern.compile(word);
                    Matcher matcher = pattern.matcher(lineTxt);
                 //   System.out.println(lineTxt);
                    if (matcher.find( )) {
                      //  System.out.println("find: "+matcher.group(0)+"  in fileId"+fileId);
                        return true;
                    }
                }
                read.close();
                return false;
            }else{
                System.out.println("找不到指定的文件");
                return false;
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return  false;
    }
    public void build(){
        Hashtable<String,Boolean> hashtable=new Hashtable<String, Boolean>();
        try {
            String encoding="GBK";
            File file=new File(chineseFilePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    lineTxt = lineTxt.replaceAll("\\d", " ");
                    String words[]=lineTxt.split("-");
                    for (int i=0;i<words.length;i++){
                        if (hashtable.containsKey(words[i]))continue;
                        else hashtable.put(words[i],true);
                        char code=' ';
                        for (int j=0;j<words[i].length();j++){
                            String str=words[i];
                            code+=str.charAt(j);
                        }
                        inputFile(code%mod,words[i]);
                    }
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        try {
            String encoding="UTF-8";
            File file=new File(englishFilePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    String words[]=lineTxt.split(" ");
                    char code=' ';
                    String str=words[0];
                    for (int j=0;j<str.length();j++){
                        code+=str.charAt(j);
                    }
                    inputFile(code%mod,str);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
}
