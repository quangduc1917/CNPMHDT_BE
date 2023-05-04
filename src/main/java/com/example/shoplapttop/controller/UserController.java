package com.example.shoplapttop.controller;

import com.example.shoplapttop.model.request.user.PasswordRequest;
import com.example.shoplapttop.model.request.user.UserUpdateRequest;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.user.UserDetailResponse;
import com.example.shoplapttop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
    @RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    ResponseEntity<UserDetailResponse> getInfo(HttpServletRequest request){
        return new ResponseEntity(userService.getUserInfo(request),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<UserDetailResponse> findUserById(@PathVariable long id){
        return new ResponseEntity(userService.getUserById(id),HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    ResponseEntity<?> updateUser(@PathVariable long id , @RequestBody UserUpdateRequest updateRequest){
        userService.updateUser(id,updateRequest);
        return new ResponseEntity(new ApiResponse(true,"Update success"),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity(new ApiResponse(true,"Delete success"),HttpStatus.OK);
    }

    @PostMapping("/password")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    ResponseEntity<?> updatePassword(HttpServletRequest request , @RequestBody PasswordRequest passwordRequest){
        String message = userService.updatePassword( request ,passwordRequest);
        return new ResponseEntity(new ApiResponse(true,message),HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<UserDetailResponse>> getAllUser( @RequestParam(required = false) int offset,@RequestParam(required = false) int limit, @RequestParam(required = false) String keyWork){
        return new ResponseEntity(userService.getAllUser(offset,limit, keyWork, keyWork, keyWork),HttpStatus.OK);
    }


    @PostMapping(value = "/uploadAvatar")
    @PreAuthorize("hasAnyRole('USER') OR hasAnyRole('ADMIN')")
    public ResponseEntity<?> uploadFile(HttpServletRequest request,@RequestParam("file") MultipartFile file) {
        return new ResponseEntity(new ApiResponse(true,userService.updateImgUser(request, file)),HttpStatus.OK);
    }


}
