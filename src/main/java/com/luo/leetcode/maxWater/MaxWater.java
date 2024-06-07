package com.luo.leetcode.maxWater;

/**
 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 返回容器可以储存的最大水量。
 */
public class MaxWater {
    public static void main(String[] args) {
        Integer[] bucket = {1,8,6,2,5,4,8,3,7};
        Integer maxWater = getMaxWater(bucket);
        System.out.println(maxWater);

    }

    private static Integer getMaxWater(Integer[] bucket) {
        // 滑动窗口
        int maxWater = 0;
        for (int i = 0; i < bucket.length; i++) {
            for (int j = i+1; j < bucket.length; j++) {
                Integer shortBoard = bucket[i]<bucket[j]?bucket[i]:bucket[j];
                maxWater = Math.max(maxWater, (j - i) * shortBoard);
            }
        }
        return maxWater;
    }
}
