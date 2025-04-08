package com.example.demo.service;

import com.example.demo.dto.Request.UserRequest;
import com.example.demo.dto.Response.UserResponse;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    public UserResponse register(UserRequest request) {
        return null;
    }

    public void deleteUser(String id) {

    }

//    private void validateRegisterRequest(UserRequest request) {
//        if(request.getUsername().isBlank()) {
//
//        }
//    }
}
