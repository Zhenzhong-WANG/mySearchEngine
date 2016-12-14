package com.wonggigi;

import com.wonggigi.entity.Document;
import com.wonggigi.entity.Spider;
import com.wonggigi.util.ParseDocument;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        Spider spider = (Spider) context.getBean("spiderBean");
        String s= spider.printId();
        ParseDocument pd=(ParseDocument)context.getBean("parseDocumentBean");
        Document d=pd.parse(s,"url");
    }
}
