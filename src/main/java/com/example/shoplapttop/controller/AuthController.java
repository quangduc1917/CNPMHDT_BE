package com.example.shoplapttop.controller;

import com.example.shoplapttop.entity.Role;
import com.example.shoplapttop.entity.RoleName;
import com.example.shoplapttop.entity.User;
import com.example.shoplapttop.exception.AppException;
import com.example.shoplapttop.model.request.user.LoginRequest;
import com.example.shoplapttop.model.request.user.ResetPasswordRequest;
import com.example.shoplapttop.model.request.user.SignUpRequest;
import com.example.shoplapttop.model.response.ApiResponse;
import com.example.shoplapttop.model.response.JwtAuthenticationResponse;
import com.example.shoplapttop.repository.RoleRepository;
import com.example.shoplapttop.repository.UserRepository;
import com.example.shoplapttop.security.JwtTokenProvider;
import com.example.shoplapttop.service.UserService;
import com.example.shoplapttop.utils.EmailServiceImpl;
import com.example.shoplapttop.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    UserService userService;
    @Autowired
    EmailServiceImpl emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        System.out.println("check dang ki");
        System.out.println("pass: "+signUpRequest.getPassword());
        System.out.println("eail: "+signUpRequest.getEmail());
        System.out.println("us: "+signUpRequest.getUsername());
        if(userRepository.existsByUserName(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getUsername(),
                 signUpRequest.getPassword(),signUpRequest.getEmail());

        user.setPassword(passwordEncoder.encode(user.getPassword()));



        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        user.setStatus(0);

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUserName()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }


    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest reset) {
        if(!userRepository.existsByEmail(reset.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email not exist"),
                    HttpStatus.BAD_REQUEST);
        }
        String message = userService.resetPassword(reset);
        emailService.sendSimpleEmail(reset.getEmail(), "MK",message);
        System.out.println(reset.getEmail());
        return new ResponseEntity(new ApiResponse(true, "ok!"),
                HttpStatus.OK);
    }

}
