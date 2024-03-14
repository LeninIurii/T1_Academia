package org.example.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class LoginData {

    private String url;
    private String domain;
}