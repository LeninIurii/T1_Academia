package org.example.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoints {
    PLATFORM_REGISTER("/register"),
    PLATFORM_LOGIN("/login"),
    PLATFORM_PRODUCTS("/products"),
    PLATFORM_SPECIFIC_PRODUCTS("/products/"),
    PLATFORM_CART("/cart"),
    PLATFORM_PRODUCT_CART("/cart/");

    private final String endpoint;

}
