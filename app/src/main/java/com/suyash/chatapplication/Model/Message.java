package com.suyash.chatapplication.Model;

public class Message {

    String message;
    String senderId;
    String receiverId;
    String time;

    public Message() {
    }

    public Message(String message, String senderId, String receiverId, String time) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
