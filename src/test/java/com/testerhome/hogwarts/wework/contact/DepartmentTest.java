package com.testerhome.hogwarts.wework.contact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department;
    @BeforeEach
    void setUp() {
        if(department==null){
            department=new Department();
        }
    }

    @Test
    void list() {
        department.list("").then().statusCode(200).body("department.name[0]", equalTo("定向班第一期"));
        department.list("33").then().statusCode(200).body("department.name[0]", equalTo("定向班第一期"));
    }

    @Test
    void create() {
        department.create("seveniruby_d1", "33").then().body("errcode", equalTo(60008));
        department.create("seveniruby_d1", "33").then().body("errcode", equalTo(60008));
    }
}