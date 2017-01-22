package com.epiaggregator.services.auth.web;

import javax.validation.constraints.NotNull;

public class AuthenticateRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;

    public AuthenticateRequest() {
    }

    public AuthenticateRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
