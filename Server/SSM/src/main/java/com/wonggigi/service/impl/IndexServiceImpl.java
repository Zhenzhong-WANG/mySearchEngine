package com.wonggigi.service.impl;

import com.wonggigi.dao.IndexDao;
import com.wonggigi.entity.Index;
import com.wonggigi.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by handle on 17-1-15.
 */
@Service
public class IndexServiceImpl implements IndexService{
    @Autowired
    private IndexDao indexDao;

    public Index getInvertedIndex(String word){
        return indexDao.getInvertedIndex(word);
    }

}

