package com.testerhome.hogwarts.wework.contact;

import io.restassured.response.Response;

import java.util.HashMap;

public class Member extends Contact{
    public Response create(HashMap<String, Object> map){
        String body=template("/data/member.json", map);
        return getDefaultRequestSpecification().body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/user/create")
                .then().log().all().extract().response();

    }

}
