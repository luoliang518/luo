package com.luo.user.test;

import com.luo.user.utils.UserBaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class TestJunit {
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void TestRouterControl() {
        UserBaseService userBaseService = (UserBaseService)applicationContext.getBean("userBaseServiceImpl");
        System.out.println(userBaseService.sout());
    }
}
