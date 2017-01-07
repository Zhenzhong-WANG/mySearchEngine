package com.wonggigi.util;

import com.wonggigi.entity.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;



/**
 * Created by Hanoi on 2016/12/18.
 */
public class Word {
    private int maxWordLength=5;
    public void reverseSegment(Document document){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Dictionary dictionary=(Dictionary)context.getBean("dictionaryBean");
        String input=document.getTitle();
        input=input.replaceAll(" ","");
        System.out.println(input);
        int i=0;
        String result="";
        while (input.length()>0){
            if (dictionary.search(input.substring(i,input.length()))){
                System.out.println(input.substring(i,input.length())+"/");
                result+=input.substring(i,input.length())+"/";
                input=input.substring(0,i);
                i=0;
            }else{
                if (input.length()-1==i){
                    //        System.out.println(input.substring(0,i)+"/");
                    result+=input.substring(i,input.length())+"/";
                    input=input.substring(0,i);
                    i=0;
                }
                else{
                    i++;
                }
            }
        }
        System.out.println(result);
    }
    private void inputFile(String input,int id){
        try{
            File file = new File(".//InvertedFile//input"+id+".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(id+" "+input);
            bw.newLine();
            bw.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    private String getContentFile(int docId){
        String result="";
        String filePath=".//documents//"+docId+".txt";
        try {
            String encoding="GBK";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file));//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                   result+=lineTxt;
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return  result;
    }
    public void segment(Document document,int docid){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Dictionary dictionary=(Dictionary)context.getBean("dictionaryBean");

        String title=document.getTitle();
        String content=getContentFile(docid);
        String input=title+"。"+content;
        input=input.replaceAll("[\\]\\(\\)\\+\\[\\$\\?\\{\\}\\*\\|\\^\\&\\.\\t\\n\\r\\s\\\\]","");

      //  System.out.println(input);
        int i=input.length()-1;
        String result="";
        while (input.length()>0){
            if (i<maxWordLength&&dictionary.search(input.substring(0,i))){
           //     System.out.println(input.substring(0,i)+"/");
                result+=input.substring(0,i)+"/";
                input=input.substring(i);
                i=input.length();
            }else{
                if (1==i){
            //        System.out.println(input.substring(0,i)+"/");
                    result+=input.substring(0,i)+"/";
                    input=input.substring(i);
                    i=input.length();
                }
                else{
                    i--;
                }
            }
        }
        //System.out.println("Doc Id:"+document.getId()+"  "+result);
        inputFile(result,docid);
    }
}
