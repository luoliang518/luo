package com.luo.spring.bean;

public interface LuoOrdered {
    /**
     * 最高优先级
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;
    /**
     * 最低优先级
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * 获取优先级
     * @return
     */
    int getOrder();
}
