package com.wonggigi.service.impl;

import com.wonggigi.dao.DocumentDao;
import com.wonggigi.entity.Document;
import com.wonggigi.service.DocumentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Hanoi on 2016/12/17.
 */

public class DocumentServiceImpl implements DocumentService{

    private DocumentDao documentDao;

    public void addDocument(Document document) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        documentDao =(DocumentDao) context.getBean("documentDao");
        documentDao.addDocument(document);
    }

    public Document getDocumentById(int id){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        documentDao =(DocumentDao) context.getBean("documentDao");
        return documentDao.getDocumentById(id);
    }
}
