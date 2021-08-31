package com.felixin.random.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtTokenDTO implements Serializable {
    private String token;

    public JwtTokenDTO(String token) {
        this.token = token;
    }

    public JwtTokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
