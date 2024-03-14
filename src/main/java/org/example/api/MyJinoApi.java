package org.example.api;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.example.utils.ApiAdminClient;

public class MyJinoApi<T> extends ApiAdminClient {
    @Step("Отправить POST-запрос по адрессу {url} в сервис {endpoint}")
    public String postData(String url, String body, String endpoint) {
        return request(url)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(endpoint)
                .then().statusCode(HttpStatus.SC_CREATED).extract().response().asString();
    }

    @Step("Отправить POST-запрос по адрессу {url} в сервис {endpoint}")
    public T postDataOk(String url, String body, String endpoint, Class<T> clazz) {
        return request(url)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .when().post(endpoint)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(clazz);
    }

    @Step("Отправить GET-запрос по адрессу {url} в сервис {endpoint}")
    public T getProducts(String url, String endpoint,Class<T> clazz) {
        return request(url)
                .contentType(ContentType.JSON)
                .when().get(endpoint)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(clazz);
    }

    @Step("Отправить PUT-запрос по адрессу {url} в сервис {endpoint}")
    public T putData(String url,String body, String endpoint,Class<T> clazz) {
        return request(url)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(body)
                .when().put(endpoint)
                .then().statusCode(HttpStatus.SC_OK).extract().response().as(clazz);
    }
    @Step("Отправить DELETE-запрос по адрессу {url} в сервис {endpoint}")
    public String deleteData(String url, String endpoint) {
        return request(url)
                .accept(ContentType.JSON)
                .when().delete(endpoint)
                .then().statusCode(HttpStatus.SC_OK).extract().asString();
    }

}
