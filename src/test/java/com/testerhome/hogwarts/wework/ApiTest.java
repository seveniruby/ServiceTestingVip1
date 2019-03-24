package com.testerhome.hogwarts.wework;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class ApiTest {

    @Test
    void templateFromYaml() {
        Api api=new Api();
        api.templateFromYaml("/api/list.yaml", null).then().statusCode(200);
    }

    @Test
    void request(){
        RequestSpecification req=given().log().all();
        req.queryParam("id", 1);
        req.queryParam("d", "xxxx");
        req.get("http://www.baidu.com");

    }
}