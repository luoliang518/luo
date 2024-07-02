package com.luo.common.enums;

import com.luo.common.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
@Getter
@AllArgsConstructor
public enum CertStatus implements BaseEnum<Integer>,Serializable {
    ACTIVE(0,"有效"),
    EXPIRED(1,"过期"),
    ;
    private final Integer code;
    private final String value;
}
