package com.wonggigi;

import com.wonggigi.entity.Document;
import com.wonggigi.util.Spider;
import com.wonggigi.service.DocumentService;
import com.wonggigi.service.impl.DocumentServiceImpl;
import com.wonggigi.util.Dictionary;
import com.wonggigi.util.ParseDocument;
import com.wonggigi.util.Word;
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
        urlArray.add("https://www.zhihu.com/question/40501921");
        urlArray.add("http://news.ifeng.com/a/20161222/50457394_0.shtml");
        urlArray.add("http://news.ifeng.com/a/20161220/50446200_0.shtml");
        urlArray.add("http://sy.house.ifeng.com/detail/2016_12_22/50965792_0.shtml");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Spider spider = (Spider) context.getBean("spiderBean");
        Dictionary dictionary=(Dictionary)context.getBean("dictionaryBean");
        //   dictionary.build();
        Word word=(Word)context.getBean("wordBean");
        ParseDocument pd=(ParseDocument)context.getBean("parseDocumentBean");
        DocumentServiceImpl documentServiceImpl=new DocumentServiceImpl();

        Iterator urlIt=urlArray.iterator();
        int docId=1;
        while (urlIt.hasNext()){
            String url=(String) urlIt.next();
            String s= spider.get(url);
            Document document=pd.parse(s,url,docId);
            documentServiceImpl.addDocument(document);
            word.segment(document);
            docId++;
        }

       // System.out.println(dictionary.search("中华"));
       // Document document=documentServiceImpl.getDocumentById(7);
       // word.reverseSegment(document);
    }
}
