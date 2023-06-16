package com.luo.utils.juc.threadImpl;

import java.util.Arrays;
import java.util.List;

/**
 * 了解并行流
 */
public class ParallelStreamTest {
    public static List<String> list;
    static {
        list = Arrays.asList("a", "b", "c", "d", "e");
    }
    public static void main(String[] args) {
        // 串行计算
        list.forEach(System.out::print);
        System.out.println();
        // 并行计算
        list.parallelStream().forEach(System.out::print);
    }
}
