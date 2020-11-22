package com.suyash.chatapplication.Model;

public class User {

    String id;
    String name;
    String email;
    String searchName;
    String profile;

    public User(){

    }

    public User(String id, String name, String email, String searchName, String profile) {
        this.name = name;
        this.email = email;
        this.searchName = searchName;
        this.id = id;
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
}
