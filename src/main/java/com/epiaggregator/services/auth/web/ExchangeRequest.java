package com.epiaggregator.services.auth.web;

import javax.validation.constraints.NotNull;

public class ExchangeRequest {
    @NotNull
    private String accessToken;
    @NotNull
    private String audience;

    public ExchangeRequest() {

    }

    public ExchangeRequest(String accessToken, String audience) {
        this.accessToken = accessToken;
        this.audience = audience;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }
}
