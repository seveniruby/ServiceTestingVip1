package com.testerhome.hogwarts.wework.contact;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void list() {
        Department department=new Department();
        department.list("").then().statusCode(200).body("department.name[0]", equalTo("定向班第一期"));
        department.list("33").then().statusCode(200).body("department.name[0]", equalTo("定向班第一期"));

    }
}