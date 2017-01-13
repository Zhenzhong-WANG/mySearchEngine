package com.wonggigi.service;

import com.wonggigi.entity.TestUser;

import org.springframework.stereotype.Service;


import java.util.List;

/**
 * Created by Hanoi on 2016/12/9.
 */
@Service
public interface TestUserService  {
    List<String> getAllUserName();
    List<TestUser> userList();
    String addUser(TestUser testUser);
    void updateUser(TestUser testUser);
    void deleteUser(TestUser testUser);
}