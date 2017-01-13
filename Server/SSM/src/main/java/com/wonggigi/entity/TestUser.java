package com.wonggigi.entity;

/**
 * Created by Hanoi on 2016/12/8.
 */
public class TestUser {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public TestUser(){

    }
    public  TestUser(String userName,String password){
        this.username=userName;
        this.password=password;
    }
}
