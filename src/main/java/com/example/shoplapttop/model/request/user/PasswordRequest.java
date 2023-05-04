package com.example.shoplapttop.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PasswordRequest {
    private String newPassword;
    private String newSecondPassword;
    private String oldPassword;
}
