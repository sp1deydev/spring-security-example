package com.example.demo.service;

import com.example.demo.dto.Request.UserRequest;
import com.example.demo.dto.Response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.BcryptUtils;
import com.example.demo.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    public UserResponse register(UserRequest request) {
        this.validateRegisterRequest(request);
        log.info("[register] - register user with username {} START", request.getUsername());

        if(userRepository.existsByUsername(request.getUsername())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "User is exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BcryptUtils.encode(request.getPassword()));
        User savedUser = userRepository.save(user);

        log.info("[register] - register user with username {} DONE", request.getUsername());
        return new UserResponse(savedUser.getUsername(), savedUser.getPassword());
    }

    public void deleteUser(String id) {
        log.info("[deleteUser] - deleteUser user with id {} START", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.INVALID_REQUEST, "User not found"));
        userRepository.delete(user);

        log.info("[deleteUser] - deleteUser user with id {} DONE", id);
    }

    private void validateRegisterRequest(UserRequest request) {
        if(StringUtils.isBlank(request.getUsername())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Username is invalid");
        }
        if(StringUtils.isBlank(request.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Password is invalid");
        }
    }
}
