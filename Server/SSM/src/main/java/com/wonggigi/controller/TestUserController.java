package com.wonggigi.controller;

import com.wonggigi.entity.TestUser;
import com.wonggigi.service.TestUserService;
import com.wonggigi.util.ObjectProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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

    @RequestMapping(value="/userlist.action",method = RequestMethod.GET)
    @ResponseBody
    public List<TestUser> getList(){
        System.out.println("GET ALL USER ");
        return testUserService.userList();
    }

    @RequestMapping(value="/update.action",method = RequestMethod.POST)
    @ResponseBody
    public void updateUser(@RequestBody TestUser testUser){
        System.out.println("UPDATE USER "+testUser.getUsername());
        testUserService.updateUser(testUser);
    }

    @RequestMapping(value="/delete.action",method = RequestMethod.POST)
    @ResponseBody
    public void deleteUser(@RequestBody TestUser testUser){
        System.out.println("DELETE USER "+testUser.getUsername());

        testUserService.deleteUser(testUser);
    }

    @RequestMapping(value="/adduser.action",method = RequestMethod.POST)
    @ResponseBody
    public void addUser(@RequestBody TestUser testUser){
        System.out.println("ADD  USER "+testUser.getUsername());
        objectProperty.getProperty(testUser);
        testUserService.addUser(testUser);
    }
}