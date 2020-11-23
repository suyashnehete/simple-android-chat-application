package com.suyash.chatapplication.Model;

public class ChatList {

    String id;
    String name;
    String profile;

    public ChatList() {
    }

    public ChatList(String id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
