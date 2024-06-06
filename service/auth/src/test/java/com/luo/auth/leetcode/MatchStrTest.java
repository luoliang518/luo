package com.luo.auth.leetcode;

public class MatchStrTest {
    public static void main(String[] args) {
        boolean flag = isMatch("aa", "a");
    }

    private static boolean isMatch(String str, String matchStr) {
        if (str.equals(matchStr)) {
            return true;
        } else if (!matchStr.contains("*") ||
                matchStr.contains(".")) {
            return false;
        }
        // 记录前一个元素
        char previous;
        int aIndex ;
        for (int i = 0; i < matchStr.length(); i++) {
            if (matchStr.charAt(i) == '*') {

            }
        }
        return false;
    }
}
