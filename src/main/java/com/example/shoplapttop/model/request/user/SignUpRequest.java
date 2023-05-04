package com.example.shoplapttop.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
}
