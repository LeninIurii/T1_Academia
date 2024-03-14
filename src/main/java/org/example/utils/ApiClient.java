package org.example.utils;


import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.PropertiesConstants;

import java.nio.charset.Charset;

import static io.restassured.RestAssured.given;
import static org.example.constants.PropertiesConstants.STAND_PROPERTIES;

@Slf4j
public abstract class ApiClient {
    public RequestSpecification request(String nginx) {

        return given().spec(new RequestSpecBuilder()
                .setBaseUri(initBaseUrlRestAssured(nginx))
                .setConfig(RestAssuredConfig.newConfig().encoderConfig(EncoderConfig.encoderConfig().and().defaultContentCharset(Charset.defaultCharset())
                        .encodeContentTypeAs("multipart/form-data", ContentType.JSON)))
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build().log().all());

    }


    String initBaseUrlRestAssured(String nginx) {
        String baseUrl = STAND_PROPERTIES.getProperty(nginx) + ":" + STAND_PROPERTIES.getProperty(PropertiesConstants.PORT);
        RestAssured.baseURI = baseUrl;
        log.info("Сконфигурирован baseUrl = {}", baseUrl);
        return baseUrl;
    }


}
