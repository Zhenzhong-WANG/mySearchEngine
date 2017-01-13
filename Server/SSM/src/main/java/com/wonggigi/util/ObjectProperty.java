package com.wonggigi.util;

import org.springframework.stereotype.Component;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hanoi on 2016/12/10.
 */
@Component
public class ObjectProperty {
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public List getProperty(Object o){
        Field[] fields=o.getClass().getDeclaredFields();

        List list = new ArrayList();
        Map infoMap=null;
        for(int i=0;i<fields.length;i++){
            infoMap = new HashMap();
            infoMap.put("Type", fields[i].getType().toString());
            System.out.println("Type: "+fields[i].getType().toString()+" Name: "+fields[i].getName()+" Value: "+getFieldValueByName(fields[i].getName(), o));
            infoMap.put("Name", fields[i].getName());
            infoMap.put("Value", getFieldValueByName(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }
}
