package com.pfm.restapi.security;

public class AuthResponse {
    private String token;
    private String expiresIn;
    private String type;

    public AuthResponse(String token, String expiresIn, String type) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
