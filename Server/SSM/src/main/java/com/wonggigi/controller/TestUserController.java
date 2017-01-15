package com.wonggigi.controller;

import com.wonggigi.entity.Index;
import com.wonggigi.service.IndexService;
import com.wonggigi.util.ObjectProperty;
import com.wonggigi.util.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;


/**
 * Created by Hanoi on 2016/12/9.
 */
@Controller
@RequestMapping(value = "/")
public class TestUserController {

    @Autowired
    private IndexService indexService;
    @Autowired
    private ObjectProperty objectProperty;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String getListPage(){
        return "index";
    }

    @RequestMapping(value="/search.action",method = RequestMethod.GET)
    @ResponseBody
    public String search(HttpServletRequest request){
        String query=request.getParameter("query");
        System.out.println("Query is "+query);
        String segmentWord=Word.segment(query);
        System.out.println(segmentWord);
        Index index=indexService.getInvertedIndex("上都");
        System.out.println(index.getWord()+" : "+index.getDf()+" : "+index.getList());
        //ArrayList<Integer> docList=InvertedIndex.getDocList(segmentWord);
        return "success";
    }
}