package com.testerhome.hogwarts.wework;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.reset;

public class Api {
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
        DocumentContext documentContext= JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public Response templateFromHar(String path, String pattern, HashMap<String, Object> map){
        //todo: 支持从har文件读取接口定义并发送
        //从har中读取请求，进行更新
        DocumentContext documentContext= JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(), entry.getValue());
        });

        String method=documentContext.read("method");
        String url=documentContext.read("url");
        return requestSpecification.when().request(method, url);
    }

    public Response templateFromSwagger(String path, String pattern, HashMap<String, Object> map){
        //todo: 支持从swagger自动生成接口定义并发送
        //从har中读取请求，进行更新
        DocumentContext documentContext= JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(), entry.getValue());
        });

        String method=documentContext.read("method");
        String url=documentContext.read("url");
        return requestSpecification.when().request(method, url);
    }
    public Response templateFromYaml(String path, HashMap<String, Object> map){
        //fixed: 根据yaml生成接口定义并发送

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Restful restful=mapper.readValue(WeworkConfig.class.getResourceAsStream(path), Restful.class);

            if(restful.method.toLowerCase().contains("get")) {
                map.entrySet().forEach(entry -> {
                    restful.query.replace(entry.getKey(), entry.getValue().toString());
                    System.out.println(restful.query);
                });
            }

            //this.requestSpecification=given();
            //System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
            restful.query.entrySet().forEach(entry->{
                this.requestSpecification=this.requestSpecification.queryParam(entry.getKey(),entry.getValue());
            });
            return this.requestSpecification.log().all().request(restful.method, restful.url)
                    .then().log().all().extract().response();


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //todo: 支持wsdl soap

    public Response readApiFromYaml(String path, HashMap<String, Object> map){
        //todo: 动态调用
        return null;
    }

}
