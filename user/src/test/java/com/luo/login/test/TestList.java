package com.luo.login.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: luoliang
 * @DATE: 2023/2/3/003
 * @TIME: 16:58
 */
public class TestList {
    public static void main(String[] args) {
        ArrayList<Luo> luos = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Luo luo = new Luo();
            luo.setUserMainType(String.valueOf(i));
            luo.setOrganizationType(String.valueOf(i));
            luos.add(luo);
        }
        Luo luo = new Luo();
        luo.setUserMainType("0");
        luo.setOrganizationType("1");
        luos.add(luo);

        Map<String, Luo> collect = luos.stream().filter(luoBean -> luoBean.getOrganizationType().equals("1")).collect(Collectors.toMap(Luo::getUserMainType, Function.identity()));
        System.out.println(collect.toString());
        luos.removeAll(luos.stream().filter(luoBean -> luoBean.getOrganizationType().equals("1")).collect(Collectors.toList()));
        Map<String, Luo> userSignerBeanMap =
                luos .stream()
                        .collect(Collectors.toMap(Luo::getUserMainType, Function.identity()));
        System.out.println(userSignerBeanMap.toString());
    }

//    private static ProcessAllApiRequest getOrg(ArrayList<Luo> data) {
//        for (Luo luo : data) {
//            if (luo.getUserMainType().equals("1")){
//                if (luo.getOrganizationType().equals("1")){
//                    System.out.println("");
//                }else {
//                    request = getOrg(luo.getLuoList());
//                }
//            }
//        }
//        return request;
//    }
    public static String createToken(String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("作者", "罗亮");
        map.put("name", name);
        return JWT.create()
                .withHeader(map)
                .withClaim("name",name)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000))
                .sign(Algorithm.HMAC512("jkhfghcv1nbxxdx"));
    }
    /**
     * 流程参与者表
     * @author hanxuan
     * @TableName doc_flow_actor
     */
    @Data
    public static class DocFlowActorDO implements Serializable {
        private static final long serialVersionUID = 1L;

        /**
         * 自增主键
         */
        private Long id;

        /**
         * doc_flow表主键id
         */
        private Long docFlowId;

        /**
         * 签署人code
         */
        private String userCode;

        /**
         * 签署人姓名
         */
        private String userName;

        /**
         * 部门名称
         */
        private String departmentName;

        /**
         * 部门编码
         */
        private String departmentCode;

    }
}
