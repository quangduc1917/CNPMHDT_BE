package com.example.shoplapttop.model.response.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class UserDetailResponse {
    private Long userId;
    private String userName;
    private String email;
    private String name;
    private String numberPhone;
    private String address;
    private Integer status;
    private String imgAvatar;
}
