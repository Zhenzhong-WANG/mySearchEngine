package com.wonggigi.util;

import com.wonggigi.entity.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


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
            File file = new File(".//InvertedFile//input.txt");
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

    public void segment(Document document){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Dictionary dictionary=(Dictionary)context.getBean("dictionaryBean");
        String input=document.getTitle();
        input=input.replaceAll(" |\\(|\\)","");

        System.out.println(input);
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
        inputFile(result,document.getId());
    }
}
