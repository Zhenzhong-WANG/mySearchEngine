package com.wonggigi.util;


/**
 * Created by Hanoi on 2016/12/18.
 */
public class Word {
    private static int maxWordLength=5;
    public static void reverseSegment(String input){
        input=input.replaceAll(" ","");
        System.out.println(input);
        int i=0;
        String result="";
        while (input.length()>0){
            if (Dictionary.search(input.substring(i,input.length()))){
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

    public static String segment(String input){
        input=input.replaceAll("[\\]\\(\\)\\+\\[\\$\\?\\{\\}\\*\\|\\^\\&\\.\\t\\n\\r\\s\\\\]","");
      //  System.out.println(input);
        int i=input.length()-1;
        String result="";
        while (input.length()>0){
            if (i<maxWordLength&&Dictionary.search(input.substring(0,i))){
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
        return result;
    }
}
