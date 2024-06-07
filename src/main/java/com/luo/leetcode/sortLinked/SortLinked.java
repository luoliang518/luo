package com.luo.leetcode.sortLinked;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 l1 = [1,2,4], l2 = [1,3,4]
 */
public class SortLinked {
    public static void main(String[] args) {
        Integer[] l1 = {};
        Integer[] l2 = {0};
        List l3 = sortLinked(l1,l2);
        System.out.println(l3);
    }

    private static List sortLinked(Integer[] l1, Integer[] l2) {
        LinkedList<Integer> integers = new LinkedList<>();
        integers.addAll(List.of(l1));
        integers.addAll(List.of(l2));
        List<Integer> collect = integers.stream().sorted().collect(Collectors.toList());
        return collect;
    }
}
