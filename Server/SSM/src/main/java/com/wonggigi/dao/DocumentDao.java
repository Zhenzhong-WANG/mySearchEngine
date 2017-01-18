package com.wonggigi.dao;

import com.wonggigi.entity.Document;

/**
 * Created by handle on 17-1-17.
 */
public interface DocumentDao {
    Document getDocunmentURLById(int docId);
    Double getDocunmentPRById(int docId);
}
