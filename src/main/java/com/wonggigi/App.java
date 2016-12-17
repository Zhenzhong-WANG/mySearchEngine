package com.wonggigi;

import com.wonggigi.entity.Document;
import com.wonggigi.entity.Spider;
import com.wonggigi.service.DocumentService;
import com.wonggigi.service.impl.DocumentServiceImpl;
import com.wonggigi.util.Dictionary;
import com.wonggigi.util.ParseDocument;
import org.springframework.beans.factory.annotation.Autowired;
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
        String url="https://www.zhihu.com/question/23246712";
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Spider spider = (Spider) context.getBean("spiderBean");
        Dictionary dictionary=(Dictionary)context.getBean("dictionaryBean");
        String s= spider.get(url);
        ParseDocument pd=(ParseDocument)context.getBean("parseDocumentBean");
        Document d=pd.parse(s,url);
      //  dictionary.build();
        DocumentServiceImpl ds=new DocumentServiceImpl();
        ds.addDocument(d);
        System.out.println(dictionary.search("沈阳"));

    }
}
