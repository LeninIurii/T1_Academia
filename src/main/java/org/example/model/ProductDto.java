package org.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class ProductDto {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("price")
    private Long price;

    @JsonProperty("name")
    private String name;

    @JsonProperty("discount")
    private Integer discount;

    @JsonProperty("category")
    private String category;
}