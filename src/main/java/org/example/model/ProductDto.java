package org.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class  ProductDto {
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