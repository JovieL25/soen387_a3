package com.example.model;

import java.util.HashSet;

public class User {

    private String userId;
    private String name;
    private String email;
    private String password;
    private HashSet<String> groupNames;
    private boolean admin;

    public User() {

    }

    public User(String userId,String name, String email,String password, HashSet<String> groupNames) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.groupNames = groupNames;
        this.admin = groupNames.contains("admins");
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<String> getGroupNames() {
        return groupNames;
    }

    public boolean isAdmin() { return admin; }

    @Override
    public String toString() {
        return "[userId:"+userId+", name:"+name+", email:"+email
                + ", password:"+password+", groupName:"+groupNames
                +", admin:"+admin;
    }

}
