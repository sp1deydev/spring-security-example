package com.example.demo.service;

import com.example.demo.dto.Request.UserRequest;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthService {
    UserRepository userRepository;

    public void authenticate(UserRequest request) {

    }

    public void introspect(String accessToken) {

    }

    public void logout(String accessToken) {

    }
}
