package com.luo.auth.domain.messageAggergate.valueObject;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class PhoneMessage implements Serializable {
    private String phone;

    public PhoneMessage(String phone) {
        this.phone = phone;
    }
}
