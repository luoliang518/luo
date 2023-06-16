package com.luo.login.test;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

//@SpringBootTest(classes = {UserApplication.class})
public class TestAssured {
    @Test
    public void getfunction1() throws Exception {

        //结构验证
        get("http://localhost:518/router/loginSuccess")
                .prettyPeek();
    }

    @Test
    public void getfunction2() throws Exception {
        //  given().param()
        given().contentType(ContentType.JSON)
                .body("{\n" +
                "  \"account\": \"123\",\n" +
                "  \"username\": \"123\",\n" +
                "  \"password\": \"123\",\n" +
                "  \"phoneNumber\": \"123\",\n" +
                "  \"rolesIds\": [\n" +
                "    \"1\"\n" +
                "  ]\n" +
                "}")
                .when().post("http://localhost:518/user/createUser")
                .prettyPeek();
    }
}
