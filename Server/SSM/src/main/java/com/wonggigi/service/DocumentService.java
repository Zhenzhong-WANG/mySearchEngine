package com.wonggigi.service;

import com.wonggigi.entity.Document;
import org.springframework.stereotype.Service;

/**
 * Created by handle on 17-1-17.
 */
@Service
public interface DocumentService {
    Document getDocunmentURLById(int docId);
    Double getDocunmentPRById(int docId);
}
