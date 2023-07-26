package com.luo.user.test.leetCode;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Date;

//@SpringBootTest
public class TestTableSizeFor {
    private Date date;
    @BeforeSuite
    protected void testBeforeSuite() {
        date=new Date();
        System.out.println("��ʼʱ��"+date);
    }
    @Test
    @Parameters("cap")
    public void tableSizeFor(int cap){
        int num = 1;
        while ((num)<cap){
            num=num*2;
        }
        System.out.println(num);
    }
    @Test
    @Parameters("cap")
    public void tableSizeFor1(int cap){
        cap=cap-1;
        cap=cap|cap>>>1;
        cap=cap|cap>>>2;
        cap=cap|cap>>>4;
        cap=cap|cap>>>8;
        cap=cap|cap>>>16;
        System.out.println(cap+1);
    }
    @AfterSuite
    protected void testAfterSuite() {
        Date date1=new Date();
        System.out.println("����ʱ��"+date1);
        System.out.println("ʱ�仨��"+(date1.getTime()-date.getTime()));
    }
}
