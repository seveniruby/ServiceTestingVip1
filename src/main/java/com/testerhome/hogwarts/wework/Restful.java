package com.testerhome.hogwarts.wework;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Restful {
    HashMap<String, Object> query=new HashMap<String, Object>();
    public RequestSpecification requestSpecification=given();
    public Response send(){
        requestSpecification=given().log().all();
        query.entrySet().forEach( entry-> {
            requestSpecification.queryParam(entry.getKey(), entry.getValue());
        });

        return requestSpecification.when().request("get", "baidu.com");
    }

    public static String template(String path, HashMap<String, Object> map){
        DocumentContext documentContext= JsonPath.parse(Restful.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

}
