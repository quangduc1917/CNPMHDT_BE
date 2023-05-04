package com.example.shoplapttop.mapper.user;

import com.example.shoplapttop.entity.User;
import com.example.shoplapttop.mapper.BaseMapper;
import com.example.shoplapttop.model.response.user.UserDetailResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDetailResponseMapper extends BaseMapper<UserDetailResponse, User> {
}
