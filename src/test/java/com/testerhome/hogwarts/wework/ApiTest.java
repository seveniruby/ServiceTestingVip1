package com.testerhome.hogwarts.wework;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

class ApiTest {

    @Test
    void templateFromYaml() {
        Api api=new Api();
        api.getResponseFromYaml("/api/list.yaml", null).then().statusCode(200);
    }

    @Test
    void request(){
        RequestSpecification req=given().log().all();
        req.queryParam("id", 1);
        req.queryParam("d", "xxxx");
        req.get("http://www.baidu.com");

    }

    @Test
    void resource(){
        URL url=getClass().getResource("/api/app.har.json");
        System.out.println(url.getFile());
        System.out.println(url.getPath());
    }

    @Test
    void getApiFromHar() {
        Api api=new Api();
        System.out.println(api.getApiFromHar("/api/app.har.json", ".*tid=67.*").url);
        System.out.println(api.getApiFromHar("/api/app.har.json", ".*tid=41.*").url);
        System.out.println(api.getApiFromHar("/api/app.har.json", ".*tid=21.*").url);
    }

    @Test
    void matches(){
        String s="https://work.weixin.qq.com/api/devtools/devhandler.php?tid=67&access_token=gs4n_tfZfSWNnLxtJx_Qsww8tpRN_7fsglgvhencsjNO1uR4mvylY2vfy42sX_Oub1i1rjstiWi3D-bk4qybWhpwPHR9yQ9D-T-huOvRCO0RzLrcetj5foV1wgoXhb6fKm5f8oZa-SH4hbgenoL-FYfEuxvxOaKusrWpNAwl4NSBD_4_l4eDPFysBGTj1HDrvqt57Nij_P-jzT1jFV9v_Q&f=json";
        System.out.println(s.matches(".*tid=67.*"));

    }

    @Test
    void getResponseFromHar() {
        Api api=new Api();
        api.getResponseFromHar("/api/app.har.json", ".*tid=67.*", null);
    }

    @Test
    void mustache() throws IOException {
        HashMap<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("name", "Mustache");

        Writer writer = new OutputStreamWriter(System.out);
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader("name: ddddddddddd {{name}} "), "example");
        mustache.execute(writer, scopes);
        writer.flush();
    }
}