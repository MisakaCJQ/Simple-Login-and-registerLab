package com.cjq.database;

public class User {
    private String username;
    private String password;

    public User(String aUsername,String aPassword){
        username = aUsername;
        password = aPassword;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void reset(String aUsername,String aPassword){
        username = aUsername;
        password = aPassword;
    }
}
