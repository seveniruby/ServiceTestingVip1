package com.testerhome.hogwarts.wework.contact;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.testerhome.hogwarts.wework.Wework;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

public class Department extends Contact{
    public Response list(String id){
        Response response= requestSpecification
                .param("id", id)
        .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/list").then().extract().response();
        reset();
        return response;
    }

    public Response create(String name, String parentid){
        reset();
        String body=JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"))
                .set("$.name", name)
                .set("parentid", parentid).jsonString();
        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().log().all().extract().response();

    }
    public Response create(HashMap<String, Object> map){
        reset();

        DocumentContext documentContext=JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/create.json"));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(), entry.getValue());
        });
        return requestSpecification
                .body(documentContext.jsonString())
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
                .then().extract().response();

    }

    public Response delete(String id){
        reset();
        return requestSpecification
                .queryParam("id", id)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().extract().response();
    }

    public Response update(String name, String id){
        reset();
        String body=JsonPath.parse(this.getClass()
                .getResourceAsStream("/data/update.json"))
                .set("$.name", name)
                .set("id", id).jsonString();
        return requestSpecification
                .body(body)
                .when().post("https://qyapi.weixin.qq.com/cgi-bin/department/update")
                .then().extract().response();

    }

    public Response update(HashMap<String, Object> map){
        return templateFromHar(
                "demo.har.json",
                "https://work.weixin.qq.com/wework_admin/party?action=addparty" ,
                map
        );
    }

    public Response deleteAll(){
        reset();
        List<Integer> idList=list("").then().log().all().extract().path("department.id");
        System.out.println(idList);
        idList.forEach(id->delete(id.toString()));
        return null;
    }

    public Response updateAll(HashMap<String, Object> map){
        return api("api.json", map);
    }
}
