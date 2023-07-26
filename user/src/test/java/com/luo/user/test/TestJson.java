package com.luo.user.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: luoliang
 * @DATE: 2023/1/30/030
 * @TIME: 11:01
 */
public class TestJson {
    public static void main(String[] args) {
        List<String> rolesIds = new ArrayList<>();
        rolesIds.add("1");
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(rolesIds));
        System.out.println(array.toString());

        JSONArray jsonArray = JSONArray.parseArray(array.toString());
        for (Object o : jsonArray) {
            System.out.println(o.toString());
        }
    }
}
