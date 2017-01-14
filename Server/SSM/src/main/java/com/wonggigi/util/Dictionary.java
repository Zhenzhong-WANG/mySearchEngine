package com.wonggigi.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hanoi on 2016/12/17.
 */
public class Dictionary {
    private static int mod=349;

    public static boolean search(String word){
        char code=' ';
        for (int j=0;j<word.length();j++){
            code+=word.charAt(j);
        }
        int fileId=code%mod;
      //  System.out.println(fileId+" , "+word);
        try {
            File file = new File("//home//Projects//SearchEngine//dictionary//dictionary"+fileId+".txt");
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
}
