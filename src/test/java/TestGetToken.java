import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class TestGetToken {
    @Test
    void testToken(){
        RestAssured.given().log().all()
                .queryParam("corpid", "wwd6da61649bd66fea")
                .queryParam("corpsecret", "1JPyY9GvPLZfpvxEDjok-Xt_9v7HIBYJhZUoO6EgNGY")
            .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
            .then().log().all().statusCode(200).body("errcode", equalTo(0));
    }
}
