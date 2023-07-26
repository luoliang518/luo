package com.luo.user.test;

import lombok.Data;

import java.util.List;
@Data
public class Luo {
    private String userMainType;
    private String organizationType;
    List<Luo> luoList;
}
