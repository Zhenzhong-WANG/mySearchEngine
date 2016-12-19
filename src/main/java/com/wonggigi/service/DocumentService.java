package com.wonggigi.service;

import com.wonggigi.entity.Document;
import org.springframework.stereotype.Service;

/**
 * Created by Hanoi on 2016/12/17.
 */
@Service
public interface DocumentService {
    void addDocument(Document document);
    Document getDocumentById(int id);
}
