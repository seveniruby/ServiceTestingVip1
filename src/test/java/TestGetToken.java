import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

public class TestGetToken {
    @Test
    void testToken(){
        RestAssured.given().log().all()
                .queryParam("corpid", WeworkConfig.getInstance().corpid)
                .queryParam("corpsecret", WeworkConfig.getInstance().secret)
            .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
            .then().log().all().statusCode(200).body("errcode", equalTo(0));
    }
}
