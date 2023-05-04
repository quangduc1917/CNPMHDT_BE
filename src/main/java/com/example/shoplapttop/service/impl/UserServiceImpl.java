package com.example.shoplapttop.service.impl;

import com.example.shoplapttop.entity.User;
import com.example.shoplapttop.exception.ResourceNotFoundException;
import com.example.shoplapttop.mapper.user.UserDetailResponseMapper;
import com.example.shoplapttop.model.request.user.PasswordRequest;
import com.example.shoplapttop.model.request.user.ResetPasswordRequest;
import com.example.shoplapttop.model.request.user.UserUpdateRequest;
import com.example.shoplapttop.model.response.user.UserDetailResponse;
import com.example.shoplapttop.repository.UserRepository;
import com.example.shoplapttop.security.JwtTokenProvider;
import com.example.shoplapttop.service.FileStorageService;
import com.example.shoplapttop.service.UserService;
import com.example.shoplapttop.utils.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailResponseMapper userDetailResponseMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Override
    public UserDetailResponse getUserInfo(HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);

        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(() -> new UsernameNotFoundException("Don't find user by id = " + userId));

        return userDetailResponseMapper.to(findUser.get());
    }

    @Override
    public UserDetailResponse getUserById(long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID",userId));
        return userDetailResponseMapper.to(findUser.get());
    }

    @Override
    public void updateUser(long userId, UserUpdateRequest updateRequest) {
        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID",userId));

        User user = findUser.get();
        user.setName(updateRequest.getName());
        user.setAddress(updateRequest.getAddress());
        user.setNumberPhone(updateRequest.getNumberPhone());

        userRepository.save(user);

    }

    @Override
    public void deleteUser(long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID",userId));

        User user = findUser.get();
        user.setStatus(1);
        userRepository.save(user);
    }

    @Override
    public String updatePassword(HttpServletRequest request, PasswordRequest passwordRequest) {
        String message = "";
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", request));

        User user = findUser.get();

        if (passwordEncoder.matches(passwordRequest.getOldPassword(),user.getPassword())
                && passwordRequest.getNewPassword().equalsIgnoreCase(passwordRequest.getNewSecondPassword())){
            user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
            userRepository.save(user);
            message = "SUCCESS";
        }else if (!passwordEncoder.matches(passwordRequest.getOldPassword(),user.getPassword())
                && passwordRequest.getNewPassword().equalsIgnoreCase(passwordRequest.getNewSecondPassword())){
            message = "Old password is incorrect";
        }else if (passwordEncoder.matches(passwordRequest.getOldPassword(),user.getPassword())
                && !passwordRequest.getNewPassword().equalsIgnoreCase(passwordRequest.getNewSecondPassword())){
            message = "New password is incorrect";
        }else{
            message = "Update password failed";
        }

        return message;

    }

    @Override
    public Page<UserDetailResponse> getAllUser(int offset, int limit, String email, String numberPhone, String userName) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<User> users = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.conjunction();
                if (!email.isBlank() && !numberPhone.isBlank() && !userName.isBlank()){
                    Predicate pEmail = criteriaBuilder.like(root.get("email"),"%"+email+"%");
                    Predicate pNumberPhone = criteriaBuilder.like(root.get("numberPhone"),"%"+numberPhone+"%");
                    Predicate pUserName = criteriaBuilder.like(root.get("userName"),"%"+userName+"%");
                    Predicate predicate = criteriaBuilder.or(pEmail,pNumberPhone,pUserName);
                    p = criteriaBuilder.and(p,predicate);
                }

                p = criteriaBuilder.and(p,criteriaBuilder.equal(root.get("status"),0));
                query.orderBy(criteriaBuilder.asc(root.get("userId")));
                return p;
            }
        },pageRequest);
        return users.map(userDetailResponseMapper::to);
    }

    @Override
    public String updateImgUser(HttpServletRequest request, MultipartFile file) {
        String token = JwtUtil.getToken(request);
        Long userId = jwtTokenProvider.getUserIdFromJWT(token);
        Optional<User> findUser = userRepository.findById(userId);
        findUser.orElseThrow(()->new ResourceNotFoundException("Id not found!","ID", request));

        String fileName = fileStorageService.storeFile(file);

        User user = findUser.get();
        user.setImgAvatar(fileName);
        userRepository.save(user);

        return "SUCCESS";
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {

        String message = "";
        Optional<User> findUser = userRepository.findByEmail(resetPasswordRequest.getEmail());
        User user = findUser.get();
        int code = (int) Math.floor(((Math.random() * 899999) + 100000));
        String resetPassword = String.valueOf(code);
        user.setPassword(passwordEncoder.encode(resetPassword));
        userRepository.save(user);
        System.out.println(code);
        return  resetPassword;
    }

}
