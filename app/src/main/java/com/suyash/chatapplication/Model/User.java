package com.suyash.chatapplication.Model;

public class User {

    String name;
    String email;
    String searchName;

    public User(String name, String email, String searchName) {
        this.name = name;
        this.email = email;
        this.searchName = searchName;
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
