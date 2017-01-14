package com.wonggigi.controller;

import com.wonggigi.entity.TestUser;
import com.wonggigi.service.TestUserService;
import com.wonggigi.util.InvertedIndex;
import com.wonggigi.util.ObjectProperty;
import com.wonggigi.util.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hanoi on 2016/12/9.
 */
@Controller
@RequestMapping(value = "/")
public class TestUserController {

    @Autowired
    private TestUserService testUserService;
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
        //ArrayList<Integer> docList=InvertedIndex.getDocList(segmentWord);
        return "success";
    }
}