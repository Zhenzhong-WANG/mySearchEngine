package com.wonggigi.service.impl;


import com.wonggigi.dao.DocumentDao;
import com.wonggigi.entity.Document;
import com.wonggigi.service.DocumentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Created by Hanoi on 2016/12/17.
 */
@Service
public class DocumentServiceImpl implements DocumentService{

    private DocumentDao documentDao;
    private ApplicationContext context;

    public void addDocument(Document document) {
        context = new ClassPathXmlApplicationContext("spring-spider-bean.xml");
        documentDao =(DocumentDao) context.getBean("documentDao");
        documentDao.addDocument(document);
    }
    public Document getDocumentById(int id){
        return documentDao.getDocumentById(id);
    }
    public Document getDocumentByUrl(String url){
        return documentDao.getDocumentByUrl(url);
    }
}
