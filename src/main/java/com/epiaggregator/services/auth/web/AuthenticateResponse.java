package com.epiaggregator.services.auth.web;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Duration;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.NUMBER;

public class AuthenticateResponse {
    private String token;
    @JsonFormat(shape = NUMBER)
    private Duration expiresIn;
    private String type;

    public AuthenticateResponse() {
    }

    public AuthenticateResponse(String token, Duration expiresIn, String type) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Duration getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Duration expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
