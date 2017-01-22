package com.epiaggregator.services.auth.web;

public class ExchangeResponse {
    private String jwtUser;

    public ExchangeResponse(String jwtUser) {
        this.jwtUser = jwtUser;
    }

    public String getJwtUser() {
        return jwtUser;
    }

    public void setJwtUser(String jwtUser) {
        this.jwtUser = jwtUser;
    }
}
