package org.example.utils;

import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.spi.AuthFilter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class AuthorizationFilter implements AuthFilter {
    private String authorizationValue;

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        requestSpec.header("Authorization", "Bearer " + authorizationValue);
        return ctx.next(requestSpec, responseSpec);
    }
}
