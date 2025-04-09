package com.example.demo.service;

import com.example.demo.dto.Request.AuthRequest;
import com.example.demo.dto.Response.AuthResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.ApplicationException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import com.example.demo.utils.BcryptUtils;
import com.example.demo.utils.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
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
public class AuthService {
    UserRepository userRepository;
    JwtService jwtService;

    public AuthResponse authenticate(AuthRequest request) {
        this.validateAuthenticateRequest(request);
        log.info("[authenticate] - authenticate with username {} START", request.getUsername());

        User user = userRepository.findByUsername(request.getUsername());
        if(user == null) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "User not found");
        }

        if(!BcryptUtils.matches(request.getPassword(), user.getPassword())) {
            log.info("[authenticate] - authenticate failed with username {}", request.getUsername());
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Password is incorrect");
        }
        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
        log.info("[authenticate] - authenticate successfully with username {} DONE", request.getUsername());
        return new AuthResponse(accessToken, refreshToken);
    }

    public boolean introspect(String accessToken) {
        log.info("[introspect] - introspect START");
        boolean isValid = true;
        try {
            jwtService.verifyToken(accessToken);
        }
        catch (ApplicationException e) {
            isValid = false;
        }

        log.info("[introspect] - introspect DONE");
        return isValid;
    }

    public void logout(String accessToken) {

    }

    public void refreshToken(String refreshToken) {

    }

    private void validateAuthenticateRequest(AuthRequest request) {
        if(StringUtils.isBlank(request.getUsername())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Username is invalid");
        }
        if(StringUtils.isBlank(request.getPassword())) {
            throw new ApplicationException(ErrorCode.INVALID_REQUEST, "Password is invalid");
        }
    }
}
