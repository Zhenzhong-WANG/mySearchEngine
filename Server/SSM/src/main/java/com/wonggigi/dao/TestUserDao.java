package com.wonggigi.dao;

import com.wonggigi.entity.TestUser;

import java.util.List;

/**
 * Created by Hanoi on 2016/12/8.
 */
public interface TestUserDao {
    List<TestUser> getUser();
    void addUser(TestUser testUser);
    void updateUser(TestUser testUser);
    void deleteUser(TestUser testUser);
}
