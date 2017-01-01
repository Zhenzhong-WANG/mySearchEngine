package com.wonggigi;

import com.wonggigi.entity.Document;
import com.wonggigi.util.*;
import com.wonggigi.service.DocumentService;
import com.wonggigi.service.impl.DocumentServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Hello world!
 *
 */



public class App 
{
    private DocumentService documentService;

    public static void main( String[] args )
    {
        ArrayList<String> urlArray=new ArrayList<String> ();

        urlArray.add("https://www.zhihu.com/question/53542412/answer/137031732");
        urlArray.add("http://news.ifeng.com/a/20161222/50458109_0.shtml");
        urlArray.add("http://news.qq.com/a/20161228/024762.htm");
        urlArray.add("http://liaoning.news.163.com/16/1228/10/C9C819HC03501DN3.html");
        urlArray.add("http://tieba.baidu.com/p/3908872988");
        urlArray.add("https://www.lagou.com/zhaopin/Java/?labelWords=label");


        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Spider spider = (Spider) context.getBean("spiderBean");
        Dictionary dictionary=(Dictionary)context.getBean("dictionaryBean");
        //   dictionary.build();
        Word word=(Word)context.getBean("wordBean");
        ParseDocument pd=(ParseDocument)context.getBean("parseDocumentBean");
        DocumentServiceImpl documentServiceImpl=new DocumentServiceImpl();
        Controller controller=new Controller();
        controller.start();
/*
        Iterator urlIt=urlArray.iterator();
        int docId=1;
        while (urlIt.hasNext()){
            String url=(String) urlIt.next();
            String s= spider.get(url);
            Document document=pd.parse(s,url,docId);
           // documentServiceImpl.addDocument(document);
           //  word.segment(document);
            docId++;
        }
*/

       // Document document=documentServiceImpl.getDocumentById(7);
    }
}
