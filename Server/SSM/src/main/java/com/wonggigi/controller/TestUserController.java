package com.wonggigi.controller;

import com.wonggigi.entity.Document;
import com.wonggigi.entity.Index;
import com.wonggigi.service.DocumentService;
import com.wonggigi.service.IndexService;
import com.wonggigi.util.ObjectProperty;
import com.wonggigi.util.ReadDocument;
import com.wonggigi.util.ThreeTuple;
import com.wonggigi.util.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.lang.Math;

/**
 * Created by Hanoi on 2016/12/9.
 */
@Controller
@RequestMapping(value = "/")
public class TestUserController {
    private int N=100;
    private double k1=1.2;
    private double k2=200.0;
    private double b=0.75;
    private double K=1.5;

    @Autowired
    private IndexService indexService;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ObjectProperty objectProperty;

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String getListPage(){
        return "index";
    }

    @RequestMapping(value="/search.action",method = RequestMethod.GET)
    public String search(HttpServletRequest request){
        long startTime=System.currentTimeMillis();
        String query=request.getParameter("query");
        String []words= Word.segment(query).split("/");
        ArrayList<Index> arrayListIndex=new ArrayList<Index>();
        ArrayList<Document> documentArrayList=new ArrayList<Document>();
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
                if (Integer.parseInt(tempTerm[0])!=0)
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

        ArrayList<ThreeTuple> intersectionIndexList=new ArrayList<ThreeTuple>();
        for (int i=0;i<threeTupleArrayList.size();i++){
            HashMap<Integer,Integer> hashMap=new HashMap<Integer, Integer>();
            ThreeTuple<String,Integer,HashMap<Integer,Integer>> threeTuple=threeTupleArrayList.get(i);
            Iterator it=intersectionSet.iterator();
            while (it.hasNext()){
                Integer docid=(Integer)it.next();
                hashMap.put(docid,(threeTuple.third).get(docid));
            }
            ThreeTuple<String,Integer,HashMap<Integer,Integer>> temptuple=new ThreeTuple<String, Integer, HashMap<Integer, Integer>>(threeTuple.first,threeTuple.second,hashMap);
            intersectionIndexList.add(temptuple);
        }


        threeTupleArrayList.clear();
        if (intersectionIndexList.size()!=0){
            HashMap<Integer,Integer> hashMap=(HashMap<Integer, Integer>) intersectionIndexList.get(0).third;
            Set<Integer> keys=hashMap.keySet();     // key:文档ID  value:TF;
            for(Integer key:keys){
                Double bm25=0.0;
                for(int i=0;i<intersectionIndexList.size();i++){  //计算docID:key的BM25
                    int freqOfWord=((HashMap<Integer,Integer>) intersectionIndexList.get(i).third).get(key);
                    int numDocOfWord=(Integer) intersectionIndexList.get(i).second;
                    Double Bim=Math.log((N-numDocOfWord+0.5)/(numDocOfWord+0.5));
                    bm25=bm25+Bim*(k1+1)*freqOfWord/(K+freqOfWord)*(k2+1)/(k2+1);
                }
                Document document=documentService.getDocunmentURLById(key);
                document.setPr(documentService.getDocunmentPRById(key));
                document.setBm25(bm25);
                document= ReadDocument.getContent(document);
                documentArrayList.add(document);
            }
        }


        float duration=(System.currentTimeMillis()-startTime)/1000f;
        request.setAttribute("time",duration);
        request.setAttribute("list",documentArrayList);
        return "result";
    }
}