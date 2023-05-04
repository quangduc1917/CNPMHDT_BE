package com.example.shoplapttop.model.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateRequest {
    private String name;
    private String numberPhone;
    private String address;
}
