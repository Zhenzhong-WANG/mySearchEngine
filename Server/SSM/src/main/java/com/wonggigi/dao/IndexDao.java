package com.wonggigi.dao;

import com.wonggigi.entity.Index;

/**
 * Created by handle on 17-1-15.
 */
public interface IndexDao {
    Index getInvertedIndex(String word);
}
