package com.luo.leetcode.maxPubilcPrefix;

/**
 编写一个函数来查找字符串数组中的最长公共前缀。
 如果不存在公共前缀，返回空字符串 ""。
 输入：strs = ["flower","flow","flight"]
 输出："fl"
 */
public class MaxPublicPrefix {
    public static void main(String[] args) {
        String[] strs = {"flower","flow","flight"};
        String s = longestCommonPrefix(strs);
        System.out.println(s);
    }
    public static String longestCommonPrefix(String[] strs) {
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j =0;
            for (;j<strs[i].length()&&j<prefix.length();j++){
                if (strs[i].charAt(j)!=prefix.charAt(j)){
                    break;
                }
            }
            prefix=prefix.substring(0,j);
        }
        return prefix;
    }
}
