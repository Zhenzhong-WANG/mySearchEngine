package com.wonggigi.service.impl;

import com.wonggigi.dao.DocumentDao;
import com.wonggigi.entity.Document;
import com.wonggigi.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by handle on 17-1-17.
 */
@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentDao documentDao;

    public Document getDocunmentURLById(int docId){
     return documentDao.getDocunmentURLById(docId);
    }
    public Double getDocunmentPRById(int docId){
        return documentDao.getDocunmentPRById(docId);
    }
}
