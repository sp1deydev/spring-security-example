package com.example.demo.controller;

import com.example.demo.dto.Request.AuthRequest;
import com.example.demo.dto.Request.TokenRequest;
import com.example.demo.dto.Response.AuthResponse;
import com.example.demo.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody(required = false) AuthRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/introspect")
    public boolean introspect(@RequestBody(required = false) TokenRequest accessToken) {
        return authService.introspect(accessToken.getToken());
    }
}
