package com.luo.login.test;

import java.util.ArrayList;
import java.util.List;

public class TestLeetCode {
    /**
     * 原价130，目前有3张券，A折扣券为4折券，折扣上限20，B折扣券为7折券，折扣上限30，C折扣券为6折券，折扣上限45；折扣券的使用顺序不一样，会造成最终结果不一样
     * 时间复杂度较小能计算最高优惠的方法
     * 作者：谁能凭爱意要富士山私
     * 链接：https://leetcode.cn/circle/discuss/kZtieQ/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */

    public static void main(String[] args) {
        DiscountTicket A = new DiscountTicket(0.4, 20);
        DiscountTicket B = new DiscountTicket(0.6, 45);
        DiscountTicket C = new DiscountTicket(0.7, 30);
        List<DiscountTicket> discountTickets = new ArrayList<>();
        discountTickets.add(A);
        discountTickets.add(B);
        discountTickets.add(C);
        double price = getPrice(discountTickets);

    }
    public static double getPrice(List<DiscountTicket> discountTickets){
        int p=0;
        int q=discountTickets.size()-1;
        ArrayList<List<DiscountTicket>> lists = new ArrayList<>();

        for (int i = 0; i < discountTickets.size(); i++) {
            for (int j = 0; j < discountTickets.size()-i; j++) {

            }
        }
        return 0;
    }

    public static void swap(List<DiscountTicket> arr,int i,int j){
        // 交换函数
        DiscountTicket temp = arr.get(i);
        arr.set(i,arr.get(j));
        arr.set(j,temp);
    }

    static class DiscountTicket{
        private double discount;
        private double upper;

        @Override
        public String toString() {
            return "DiscountTicket{" +
                    "discount=" + discount +
                    ", upper=" + upper +
                    '}';
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public double getUpper() {
            return upper;
        }

        public void setUpper(double upper) {
            this.upper = upper;
        }

        public DiscountTicket(double discount, double upper) {
            this.discount = discount;
            this.upper = upper;
        }
    }
}
