package com.testerhome.hogwarts.wework.contact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department;
    String random=String.valueOf(System.currentTimeMillis());
    @BeforeEach
    void setUp() {
        if(department==null){
            department=new Department();
        }
    }

    @Test
    void list() {
        department.list("").then().statusCode(200);
        department.list("33").then().statusCode(200);
    }

    @Test
    void create() {
        department.create("seveniruby_d1", "33").then().body("errcode", equalTo(60008));
        department.create("seveniruby_d1", "33").then().body("errcode", equalTo(60008));
        department.create("思寒department"+random, "1").then().body("errcode", equalTo(0));
    }

    @Test
    void createByMap(){
        HashMap<String, Object> map=new HashMap<String, Object>(){{
            put("name", String.format("seveniruby_d1_map%s", random));
            put("parentid", "1");
        }
        };
        department.create(map).then().body("errcode", equalTo(0));
    }

    @Test
    void createWithChinese() {
        department.create("思寒department"+random, "1").then().body("errcode", equalTo(0));
    }

    @Test
    void delete() {
        String nameOld="seveniruby_d1"+random;
        department.create(nameOld, "1");
        Integer idInt=department.list("").path("department.find{ it.name=='"+ nameOld +"' }.id");
        System.out.println(idInt);
        String id=String.valueOf(idInt);
        department.delete(id).then().body("errcode", equalTo(0));
    }


    @Test
    void update() {
        String nameOld="seveniruby_d1"+random;
        department.create(nameOld, "1");
        Integer idInt=department.list("").path("department.find{ it.name=='"+ nameOld +"' }.id");
        System.out.println(idInt);
        String id=String.valueOf(idInt);
        department.update("seveniruby_d2"+random,  id).then().body("errcode", equalTo(0));
    }

}