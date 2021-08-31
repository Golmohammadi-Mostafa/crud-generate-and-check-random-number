package com.felixin.random.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

    private String message;

    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}