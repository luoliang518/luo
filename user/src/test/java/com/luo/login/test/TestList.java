package com.luo.login.test;

import com.luo.model.user.UserDo;

import java.util.ArrayList;

/**
 * @author: luoliang
 * @DATE: 2023/2/3/003
 * @TIME: 16:58
 */
public class TestList {
    public static void main(String[] args) {
        ArrayList<UserDo> results = new ArrayList<UserDo>();
        for (int i = 0; i < 3; i++) {
            UserDo userDo = new UserDo();
            userDo.setUserId(String.valueOf(i));
            results.add(userDo);
        }
        results.removeIf(a->
            "2".equals(a.getUserId())||"1".equals(a.getUserId())
        );

        System.out.println(results.toString());
    }
}
