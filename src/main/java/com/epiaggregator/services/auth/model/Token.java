package com.epiaggregator.services.auth.model;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class Token {
    private ObjectId userId;
    private String token;
    private LocalDateTime expires;
    private String type;

    public Token(ObjectId userId, String token, LocalDateTime expires, String type) {
        this.userId = userId;
        this.token = token;
        this.expires = expires;
        this.type = type;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
