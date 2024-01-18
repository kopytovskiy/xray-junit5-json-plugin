package io.github.kopytovskiy.helpers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.github.kopytovskiy.misc.Constants.XRAY_BASE_URL;

@Slf4j
public class XrayImportHelper {

    /**
     * Method for importing results in Xray/Jira.
     *
     * @param authToken Bearer token used for authorization purposes;
     * @param testResultsFile JSON file with test results;
     * @param infoFile JSON file with Jira fields;
     */
    public static void importResults(String authToken, File testResultsFile, File infoFile) {

        Response response = given()
                    .headers("Authorization", "Bearer " + authToken)
                    .contentType(ContentType.MULTIPART)
                    .accept(ContentType.JSON)
                    .multiPart("info", infoFile)
                    .multiPart("results", testResultsFile)
                .when()
                    .baseUri(XRAY_BASE_URL)
                    .post("/import/execution/multipart")
                .then()
                    .extract().response();

        if (response.statusCode() == 200) {
            log.info("Tests successfully imported, congratulations!");
        } else {
            log.error("Tests import failed, status code: {} \n Error message: {}", response.statusCode(), response.getBody().asPrettyString());
        }

    }

}

