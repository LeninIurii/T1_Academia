package org.example.model.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserDto {

    @JsonProperty("password")
    private String password;

    @JsonProperty("username")
    private String username;

    @JsonProperty("access_token")
    private String accessToken;


}