package com.luo.auth.domain.utilAggergate.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PhoneMessage implements Serializable {
    private String phone;

    public PhoneMessage(String phone) {
        this.phone = phone;
    }
}
