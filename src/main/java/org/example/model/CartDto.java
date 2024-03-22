package org.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
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
