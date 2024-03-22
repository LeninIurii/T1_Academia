package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpStatus;
import org.example.api.MyJinoApi;
import org.example.builder.MyJinoEntitiesBuilder;
import org.example.constants.Endpoints;
import org.example.constants.PropertiesConstants;
import org.example.model.auth.UserDto;
import org.example.utils.ApiClient;
import org.example.utils.AuthorizationFilter;
import org.example.utils.UtilsMethods;
import org.example.utils.ValidateMethods;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AcTest extends ApiClient {
    private static final MyJinoApi MY_JINO_API = new MyJinoApi();
    private static final MyJinoEntitiesBuilder MY_JINO_ENTITIES_BUILDER = new MyJinoEntitiesBuilder();
    private static final ValidateMethods VALIDATE_METHODS = new ValidateMethods();
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private String NewUser = "User" + UtilsMethods.randomNumberString(10);
    private String NewUserPassword = "Password" + UtilsMethods.randomNumberString(10);


    @SneakyThrows
    @Test(description = "1 Заходим с пользовательскими данными Логин: {name}, Пароль: {password} / регестрируем новго пользователя", dataProvider = "userData", priority = 1)
    public void postLoginTest(String name, String password) {
        Response entitiesUpload1 = MY_JINO_API.postCheckDataOk(PropertiesConstants.SSION, MY_JINO_ENTITIES_BUILDER.generateUser(name, password), Endpoints.PLATFORM_LOGIN.getEndpoint());

        if (entitiesUpload1.statusCode() != 200) {
            MY_JINO_API.postData(PropertiesConstants.SSION, MY_JINO_ENTITIES_BUILDER.generateUser(NewUser, NewUserPassword), Endpoints.PLATFORM_REGISTER.getEndpoint());
            Response entitiesUpload = MY_JINO_API.postCheckDataOk(PropertiesConstants.SSION, MY_JINO_ENTITIES_BUILDER.generateUser(NewUser, NewUserPassword), Endpoints.PLATFORM_LOGIN.getEndpoint());
            System.out.println("Новый пользватель зарегестрирован: \nUser: " + NewUser + "\nUserPassword: " + NewUserPassword);
            AuthorizationFilter authorizationFilter = new AuthorizationFilter(OBJECT_MAPPER.readValue(entitiesUpload.getBody().asString(), UserDto.class).getAccessToken());
            RestAssured.filters(authorizationFilter);
        } else {
            AuthorizationFilter authorizationFilter = new AuthorizationFilter(OBJECT_MAPPER.readValue(entitiesUpload1.getBody().asString(), UserDto.class).getAccessToken());
            RestAssured.filters(authorizationFilter);
        }
    }

    @Test(description = "2 Получаем лист продуктов", dependsOnMethods = "postLoginTest", priority = 2)
    public void getProductsTest() {
        Response getProduct = MY_JINO_API.getProducts(PropertiesConstants.SSION, Endpoints.PLATFORM_PRODUCTS.getEndpoint());

        Assert.assertTrue(VALIDATE_METHODS.checkResponseBodySchemeStep(getProduct, "shemas/product.json"));
        Assert.assertEquals(getProduct.statusCode(), HttpStatus.SC_OK);
        Assert.assertNotEquals(getProduct.statusCode(), HttpStatus.SC_CREATED);
        Assert.assertNotEquals(getProduct.statusCode(), HttpStatus.SC_BAD_REQUEST);
        Assert.assertNotEquals(getProduct.statusCode(), HttpStatus.SC_BAD_REQUEST);

    }

    @Test(description = "3 Добовляем продукт", dependsOnMethods = "postLoginTest", priority = 3)
    public void postProductsTest() {
        Response cart = MY_JINO_API.postCheckDataOk(PropertiesConstants.SSION,
                MY_JINO_ENTITIES_BUILDER.generateProduct(2, 20, "Comp", "Electronic", 15),
                Endpoints.PLATFORM_PRODUCTS.getEndpoint());

        Assert.assertNotEquals(cart.statusCode(), HttpStatus.SC_CREATED);
        Response cartNullNameCateg = MY_JINO_API.postCheckDataOk(PropertiesConstants.SSION,
                MY_JINO_ENTITIES_BUILDER.generateProduct(1, 20, null, null, 15),
                Endpoints.PLATFORM_PRODUCTS.getEndpoint());

        Assert.assertEquals(cartNullNameCateg.statusCode(), HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test(description = "5 Получаем ранее созданный родукт", dependsOnMethods = "postLoginTest", priority = 4)
    public void getProductTest() {
        Response product = MY_JINO_API.getProducts(PropertiesConstants.SSION, Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint() + 2);
        Response productFalse = MY_JINO_API.getProducts(PropertiesConstants.SSION, Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint() + 200);
        Assert.assertEquals(product.statusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(productFalse.statusCode(), HttpStatus.SC_NOT_FOUND);

    }

    @Test(description = "6 Редактируем ранее созданный родукт", dependsOnMethods = "postLoginTest", priority = 5)
    public void putProductTest() {
        Response put = (Response) MY_JINO_API.putData(PropertiesConstants.SSION, MY_JINO_ENTITIES_BUILDER.updateProduct(11, "Computer", "Electronic", 30), Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint() + 2, Response.class);
        Response putFalse = (Response) MY_JINO_API.putData(PropertiesConstants.SSION, MY_JINO_ENTITIES_BUILDER.updateProduct(11, "Computer", "Electronic", 30), Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint() + 200, Response.class);

        Assert.assertEquals(put.statusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(putFalse.statusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(description = "7 Удаляем ранее созданный родукт", dependsOnMethods = "postLoginTest", priority = 6)
    public void deleteProductTest() {
        Response del = (Response) MY_JINO_API.deleteData(PropertiesConstants.SSION, Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint() + 2, Response.class);
        Response delEmptu = (Response) MY_JINO_API.deleteData(PropertiesConstants.SSION, Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint(), Response.class);
        Response delFalse = (Response) MY_JINO_API.deleteData(PropertiesConstants.SSION, Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint() + 200, Response.class);
        Assert.assertEquals(del.statusCode(), HttpStatus.SC_OK);
        Assert.assertEquals(delFalse.statusCode(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(description = "8 Получаем корзину пользователя ", dependsOnMethods = {"postLoginTest"}, priority = 7)
    public void getCartTest() {
        Response cart = MY_JINO_API.getProducts(PropertiesConstants.SSION, Endpoints.PLATFORM_CART.getEndpoint());
        Assert.assertTrue(VALIDATE_METHODS.checkResponseBodySchemeStep(cart, "shemas/shoppingCartResponse.json"));
        Assert.assertEquals(cart.statusCode(), HttpStatus.SC_OK);
    }


    @DataProvider(name = "userData")
    public Object[][] randomPermissionNameTenant() {
        String name = "UserTest123456789";
        String password = "UserTest";
        return new Object[][]{{name, password}};
    }

}

