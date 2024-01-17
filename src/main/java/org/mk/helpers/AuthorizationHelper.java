package org.mk.helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.mk.misc.Constants.*;

@Slf4j
public class AuthorizationHelper {

    /**
     * Method for authentication and receiving Bearer token.
     *
     * @param clientId Xray ClientId value;
     * @param clientSecret Xray ClientSecret value;
     *
     * @return <b>String</b> with Bearer token.
     */
    public static String userAuthorization(String clientId, String clientSecret) {

        String requestBody = new JSONObject()
                .put("client_id", clientId)
                .put("client_secret", clientSecret).toString();

        Response response = given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(requestBody)
                .when()
                    .baseUri(XRAY_BASE_URL)
                    .post("/authenticate")
                .then()
                    .extract().response();

        String authParsedToken = response.getBody().asString().replaceAll("\"", "");
        log.debug("TOKEN: {}", authParsedToken);

        return authParsedToken;
    }

}
