package org.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CartDto {

    @JsonProperty("cart")
    private List<ProductDto> cart;

    @JsonProperty("total_price")
    private Long totalPrice;

    @JsonProperty("total_discount")
    private Long totalDiscount;

    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("quantity")
    private Integer quantity;

}
