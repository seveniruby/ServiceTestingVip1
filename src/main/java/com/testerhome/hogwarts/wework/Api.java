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

import static io.restassured.RestAssured.*;

public class Api {
    HashMap<String, Object> query = new HashMap<String, Object>();

    public RequestSpecification getDefaultRequestSpecification() {
        return given().log().all();
    }

    public static String template(String path, HashMap<String, Object> map) {
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return documentContext.jsonString();
    }

    public Response templateFromHar(String path, String pattern, HashMap<String, Object> map) {
        //todo: 支持从har文件读取接口定义并发送
        //从har中读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });

        String method = documentContext.read("method");
        String url = documentContext.read("url");
        return getDefaultRequestSpecification().when().request(method, url);
    }

    public Response templateFromSwagger(String path, String pattern, HashMap<String, Object> map) {
        //todo: 支持从swagger自动生成接口定义并发送
        //todo: 分析swagger codegen
        //从har中读取请求，进行更新
        DocumentContext documentContext = JsonPath.parse(Api.class
                .getResourceAsStream(path));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(), entry.getValue());
        });

        String method = documentContext.read("method");
        String url = documentContext.read("url");
        return getDefaultRequestSpecification().when().request(method, url);
    }


    public Response templateFromYaml(String path, HashMap<String, Object> map) {
        //fixed: 根据yaml生成接口定义并发送

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Restful restful = mapper.readValue(WeworkConfig.class.getResourceAsStream(path), Restful.class);

            if (restful.method.toLowerCase().contains("get")) {
                map.entrySet().forEach(entry -> {
                    restful.query.replace(entry.getKey(), entry.getValue().toString());
                    System.out.println(restful.query);
                });
            }

            if (restful.method.toLowerCase().contains("post")) {
                if (map.containsKey("_body")) {
                    restful.body = map.get("_body").toString();
                }
                if (map.containsKey("_file")) {
                    String filePath = map.get("_file").toString();
                    map.remove("_file");
                    restful.body = template(filePath, map);
                }
            }


            RequestSpecification requestSpecification = getDefaultRequestSpecification();

            //System.out.println(mapper.writeValueAsString(WeworkConfig.getInstance()));
            if (restful.query != null) {
                restful.query.entrySet().forEach(entry -> {
                    requestSpecification.queryParam(entry.getKey(), entry.getValue());
                });
            }

            if (restful.body != null) {
                requestSpecification.body(restful.body);
            }

            return requestSpecification.log().all()
                    .when().request(restful.method, restful.url)
                    .then().log().all()
                    .extract().response();


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    //todo: 支持wsdl soap

    public Response readApiFromYaml(String path, HashMap<String, Object> map) {
        //todo: 动态调用
        return null;
    }

}
