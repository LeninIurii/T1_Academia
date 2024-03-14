package org.example.builder;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.model.CartDto;
import org.example.model.ProductDto;
import org.example.model.auth.UserDto;

public class MyJinoEntitiesBuilder {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public String generateUser(String username, String password) {
        return objectMapper.writeValueAsString(UserDto.builder()
                .username(username)
                .password(password)
                .build());
    }

    @SneakyThrows
    public String generateProduct(int id, long price, String name, String category, int discount) {
        return objectMapper.writeValueAsString(ProductDto.builder()
                .id(id)
                .price(price)
                .name(name)
                .category(category)
                .discount(discount).build());
    }

    @SneakyThrows
    public String updateProduct(long price, String name, String category, int discount) {
        return objectMapper.writeValueAsString(ProductDto.builder()
                .price(price)
                .name(name)
                .category(category)
                .discount(discount).build());
    }

    @SneakyThrows
    public String generateCart(int quantity, int productId) {
        return objectMapper.writeValueAsString(CartDto.builder()
                .quantity(quantity)
                .productId(productId)
                .build());
    }

}
