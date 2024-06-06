package com.luo.auth.leetcode;

import java.util.Objects;

public class MaxStrTest {
    public static void main(String[] args) {
        String maxStr = getMaxStr("cbbd");
        System.out.println(maxStr);
    }

    private static String getMaxStr(String str) {
        int maxStrNum = 0;
        String maxStr= "";
        String[] strArr = str.split("");
        for (int i = 0; i < str.length(); i++) {
            for (int j = i+1; j < str.length(); j++) {
                if (Objects.equals(strArr[i], strArr[j])&&
                j-i>=maxStrNum){
                    maxStrNum = j-i;
                    maxStr = str.substring(i,j+1);
                }
            }
        }
        return maxStr;
    }
}
