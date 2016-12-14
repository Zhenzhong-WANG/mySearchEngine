package com.wonggigi;

import com.wonggigi.entity.Spider;
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
        System.out.println(s);
    }
}
