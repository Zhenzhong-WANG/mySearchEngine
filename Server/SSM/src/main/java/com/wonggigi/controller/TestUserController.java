package com.wonggigi.controller;

import com.wonggigi.entity.Index;
import com.wonggigi.service.IndexService;
import com.wonggigi.util.ObjectProperty;
import com.wonggigi.util.ThreeTuple;
import com.wonggigi.util.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.*;


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
        long startTime=System.currentTimeMillis();
        String query=request.getParameter("query");
        String []words= Word.segment(query).split("/");
        ArrayList<Index> arrayListIndex=new ArrayList<Index>();
        for (String word:words){
            arrayListIndex.add(indexService.getInvertedIndex(word));
        }

        ArrayList<ThreeTuple> threeTupleArrayList=new ArrayList<ThreeTuple>();
        for (int i=0;i<arrayListIndex.size();i++){
            Index index=arrayListIndex.get(i);
            String list=index.getList();
            String[] terms=list.split("/");
            HashMap<Integer,Integer> hashMap=new HashMap<Integer, Integer>();
            for (String term:terms){
                String[] tempTerm=term.split(",");
                hashMap.put(Integer.parseInt(tempTerm[0]),Integer.parseInt(tempTerm[1]));
            }
            ThreeTuple<String,Integer,HashMap<Integer,Integer>> twoTuple=new ThreeTuple<String, Integer, HashMap<Integer, Integer>>(index.getWord(),index.getDf(),hashMap);
            threeTupleArrayList.add(twoTuple);
        }

        Set<Integer> intersectionSet=new HashSet<Integer>();
        for (int i=0;i<threeTupleArrayList.size();i++){
            HashMap<Integer,Integer> hashMap=(HashMap<Integer, Integer>) threeTupleArrayList.get(i).third;
            Set<Integer> keys=hashMap.keySet();
            if (i==0){
                for(Integer key:keys){
                    intersectionSet.add(key);
                }
            }
            else {
                Iterator it=intersectionSet.iterator();
                while (it.hasNext()){
                    Integer docid=(Integer)it.next();
                    if (!hashMap.containsKey(docid)){
                        intersectionSet.remove(docid);
                        it=intersectionSet.iterator();
                    }
                }
            }
        }

        Iterator it=intersectionSet.iterator();
        while (it.hasNext()){
            Integer docid=(Integer)it.next();
            System.out.println(docid);
        }
        System.out.println("执行耗时 : "+(System.currentTimeMillis()-startTime)/1000f+" 秒 ");
        return "success";
    }
}