package com.testerhome.hogwarts.wework.devtools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void listApp() {

        App app=new App();
        app.listApp().then().statusCode(200);
    }
}