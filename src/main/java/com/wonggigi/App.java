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

/**
 * Hello world!
 *
 */
public class App 
{
    private DocumentService documentService;

    public static void main( String[] args )
    {
        String url="http://news.ifeng.com/a/20161218/50435153_0.shtml";
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Spider spider = (Spider) context.getBean("spiderBean");
        Dictionary dictionary=(Dictionary)context.getBean("dictionaryBean");
        Word word=(Word)context.getBean("wordBean");
        ParseDocument pd=(ParseDocument)context.getBean("parseDocumentBean");
        DocumentServiceImpl documentServiceImpl=new DocumentServiceImpl();
     //   String s= spider.get(url);
     //    Document d=pd.parse(s,url);
       //   dictionary.build();
      //     documentServiceImpl.addDocument(d);
        System.out.println(dictionary.search("中华人民共和国"));

        Document document=documentServiceImpl.getDocumentById(7);
        word.segment(document);
    }
}
