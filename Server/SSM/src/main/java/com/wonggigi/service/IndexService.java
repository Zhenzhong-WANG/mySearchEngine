package com.wonggigi.service;

import com.wonggigi.entity.Index;
import org.springframework.stereotype.Service;

/**
 * Created by handle on 17-1-15.
 */
@Service
public interface IndexService {
    Index getInvertedIndex(String word);
}
