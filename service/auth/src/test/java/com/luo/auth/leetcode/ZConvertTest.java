package com.luo.auth.leetcode;

import java.sql.Array;
import java.util.ArrayList;

/**
 * 设置flag进行变调
 */
public class ZConvertTest {
    public static void main(String[] args) {
        String newStr = zConvert("PAYPALISHIRING",4);
        System.out.println(newStr);
    }

    private static String zConvert(String str,int rowNum) {
        ArrayList<String> stringList = new ArrayList<>();
        for (int i = 0; i < rowNum; i++) {
            String newStr = new String();
            stringList.add(newStr);
        }
        int row = 0;
        int flag = -1;
        String newStr = new String();
        for (int j = 0; j < str.length(); j++) {
            String s = stringList.get(row);
            s += String.valueOf(str.charAt(j));
            stringList.set(row,s);
            if(row==0||row==rowNum-1){
                flag = -flag;
            }
            row = row+flag;
        }
        for (String s : stringList) {
            newStr+=s;
        }
        return newStr;
    }
}
