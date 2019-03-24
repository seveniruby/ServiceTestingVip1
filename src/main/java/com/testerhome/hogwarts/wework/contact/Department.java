package com.testerhome.hogwarts.wework.contact;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.List;

public class Department extends Contact{
    public Response list(String id){
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("id", id);
        return getResponseFromYaml("/api/list.yaml", map);
    }

    public Response create(String name, String parentid){
        //让用例更清晰
        HashMap<String, Object> map=new HashMap<>();
        map.put("_file", "/data/create.json");
        map.put("name", name);
        map.put("parentid", parentid);

        return getResponseFromYaml("/api/create.yaml", map);
    }
    public Response create(HashMap<String, Object> map){
        map.put("_file", "/data/create.json");
        return getResponseFromYaml("/api/create.yaml", map);
    }

    public Response delete(String id){
        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("id", id);
        return getResponseFromYaml("/api/delete.yaml", map);
    }

    public Response update(String name, String id){


        HashMap<String, Object> map=new HashMap<>();
        map.put("_file", "/data/update.json");
        map.put("name", name);
        map.put("id", id);

        return getResponseFromYaml("/api/update.yaml", map);
    }

    public Response update(HashMap<String, Object> map){
        //todo:
        return getResponseFromHar(
                "demo.har.json",
                "https://work.weixin.qq.com/wework_admin/party?action=addparty" ,
                map
        );
    }

    public Response deleteAll(){
        List<Integer> idList=list("").then().log().all().extract().path("department.id");
        System.out.println(idList);
        idList.forEach(id->delete(id.toString()));
        return null;
    }

    public Response updateAll(HashMap<String, Object> map){
        //todo:
        return readApiFromYaml("readApiFromYaml.json", map);
    }
}
