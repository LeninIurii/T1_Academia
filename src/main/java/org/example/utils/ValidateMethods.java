package org.example.utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
@Slf4j
public class ValidateMethods {
    @Step("Проверяем, что тело ответа соответствует JSON-схеме")
    public boolean checkResponseBodySchemeStep(Response response, String schemeName) {
        log.info("Проверяем, что тело ответа соответствует JSON-схеме");
        response.then().body(matchesJsonSchemaInClasspath(schemeName));
        return true;
    }
}
