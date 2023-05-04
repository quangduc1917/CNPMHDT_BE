package com.example.shoplapttop.service;

import com.example.shoplapttop.model.request.user.PasswordRequest;
import com.example.shoplapttop.model.request.user.ResetPasswordRequest;
import com.example.shoplapttop.model.request.user.UserUpdateRequest;
import com.example.shoplapttop.model.response.user.UserDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    UserDetailResponse getUserInfo(HttpServletRequest request);

    UserDetailResponse getUserById(long userId);

    void updateUser(long userId, UserUpdateRequest updateRequest);

    void deleteUser(long userId);

    String updatePassword(HttpServletRequest request, PasswordRequest passwordRequest);

    Page<UserDetailResponse> getAllUser( int offset, int limit, String email, String numberPhone, String userName);

    String updateImgUser(HttpServletRequest request, MultipartFile file);
    String resetPassword( ResetPasswordRequest resetPasswordRequest);
}
