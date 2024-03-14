package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.example.api.MyJinoApi;
import org.example.builder.MyJinoEntitiesBuilder;
import org.example.constants.Endpoints;
import org.example.constants.PropertiesConstants;
import org.example.model.CartDto;
import org.example.model.ProductDto;
import org.example.model.auth.UserDto;
import org.example.utils.ApiClient;
import org.example.utils.AuthorizationFilter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AcTest extends ApiClient {
    private static final MyJinoApi MY_JINO_API = new MyJinoApi();
    private static final MyJinoEntitiesBuilder MY_JINO_ENTITIES_BUILDER = new MyJinoEntitiesBuilder();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test(description = "1 Регестрируем пользователя",dataProvider = "userData",  priority = 0)
    public void postRegisterTest(String name, String password) {
        MY_JINO_API.postData(PropertiesConstants.SSION, MY_JINO_ENTITIES_BUILDER.generateUser(name, password), Endpoints.PLATFORM_REGISTER.getEndpoint());
    }

    @Test(description = "2 Заходим с пользовательскими данными Логин: {name}, Пароль: {password}",dataProvider = "userData", dependsOnMethods = "postRegisterTest", priority = 1)
    public void postLoginTest(String name, String password) {
        UserDto entitiesUpload1 = (UserDto) MY_JINO_API.postDataOk(PropertiesConstants.SSION, MY_JINO_ENTITIES_BUILDER.generateUser(name, password), Endpoints.PLATFORM_LOGIN.getEndpoint(), UserDto.class);

        AuthorizationFilter authorizationFilter = new AuthorizationFilter(entitiesUpload1.getAccessToken());
        RestAssured.filters(authorizationFilter);
    }

    @Test(description = "3 Получаем лист продуктов", dependsOnMethods = "postLoginTest",priority = 2)
    public void getProductsTest() {
       MY_JINO_API.getProducts(PropertiesConstants.SSION, Endpoints.PLATFORM_PRODUCTS.getEndpoint(), List.class);
    }

    @Test(description = "4 Добовляем продукт", dependsOnMethods = "postLoginTest", priority = 3)
    public void postProductsTest() {
        MY_JINO_API.postDataOk(PropertiesConstants.SSION,MY_JINO_ENTITIES_BUILDER.generateProduct(1,20,"Comp","Electronic",15), Endpoints.PLATFORM_PRODUCTS.getEndpoint(), CartDto.class);
    }

    @Test(description = "5 Получаем ранее созданный родукт",dependsOnMethods = "postLoginTest", priority = 4)
    public void getProductTest() {
        MY_JINO_API.getProducts(PropertiesConstants.SSION, Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint()+1, List.class);
    }

    @Test(description = "6 Редактируем ранее созданный родукт",dependsOnMethods = "postLoginTest", priority = 5)
    public void putProductTest() {
        MY_JINO_API.putData(PropertiesConstants.SSION,MY_JINO_ENTITIES_BUILDER.updateProduct(11,"Computer","Electronic",30), Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint()+1, ProductDto.class);
    }

    @Test(description = "7 Удаляем ранее созданный родукт", dependsOnMethods = {"postLoginTest","putProductTest"}, priority = 6)
    public void deleteProductTest() {
        MY_JINO_API.deleteData(PropertiesConstants.SSION, Endpoints.PLATFORM_SPECIFIC_PRODUCTS.getEndpoint()+1);
    }

    @Test(description = "8 Получаем корзину пользователя ", dependsOnMethods = {"postLoginTest"}, priority = 7)
    public void getCartTest() {
        MY_JINO_API.getProducts(PropertiesConstants.SSION, Endpoints.PLATFORM_CART.getEndpoint(), List.class);
    }

    @Test( description = "9 Добовляем в корзину",dependsOnMethods = {"postLoginTest"}, priority = 8)
    public void postCartTest() {
        MY_JINO_API.postData(PropertiesConstants.SSION,MY_JINO_ENTITIES_BUILDER.generateCart(1,2), Endpoints.PLATFORM_CART.getEndpoint());
    }

    @Test( description = "10 Удаляем из корзины", dependsOnMethods = {"postLoginTest","postCartTest"}, priority = 9)
    public void deleteCartTest() {
        MY_JINO_API.deleteData(PropertiesConstants.SSION, Endpoints.PLATFORM_PRODUCT_CART.getEndpoint()+2);
    }



    @DataProvider(name = "userData")
    public Object[][] randomPermissionNameTenant() {
        String name = "UserTest123456789";
        String password = "UserTest";
        return new Object[][]{{name, password}};
    }

}

