package com.felixin.random.dto;

import java.io.Serializable;

public class NumberDTO implements Serializable {
    private Long value;

    public NumberDTO(Long value) {
        this.value = value;
    }

    public NumberDTO() {
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}