package com.luo.login.test;

import com.alibaba.fastjson.JSONObject;
import com.luo.login.UserApplication;
import com.luo.login.utils.UserBaseService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import javax.annotation.Resource;

//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {UserApplication.class})
public class TestNg extends AbstractTransactionalTestNGSpringContextTests {
    @Resource
    private RestTemplate restTemplate;
    @BeforeSuite
    protected void testBeforeSuite() {
        System.out.println("22222222");
    }

    @Test(enabled = false)
    public void routerTest() {
        String res = restTemplate.postForObject("http://localhost:518/router/loginSuccess", null, String.class);
        System.out.println();
        System.out.println(res);
    }

    @Test(enabled = false)
    public void userTest() {
        String params = "{\n" +
                "  \"account\": \"123\",\n" +
                "  \"username\": \"123\",\n" +
                "  \"password\": \"123\",\n" +
                "  \"phoneNumber\": \"123\",\n" +
                "  \"rolesIds\": [\n" +
                "    \"1\"\n" +
                "  ]\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(params);
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(jsonObject);
        JSONObject res = restTemplate.postForObject("http://localhost:518/user/createUser", httpEntity, JSONObject.class);
        System.out.println(res);
    }
    @Test
    public void testUserBaseServiceImpl() {
        UserBaseService userBaseService = null;
        if (applicationContext != null) {
            userBaseService = (UserBaseService)this.applicationContext.getBean("userBaseServiceImpl");
        }
        if (userBaseService != null) {
            System.out.println(userBaseService.sout());
        }
    }

    @AfterSuite
    protected void testAfterSuite() {
        System.out.println("33333333333333");
    }
}
