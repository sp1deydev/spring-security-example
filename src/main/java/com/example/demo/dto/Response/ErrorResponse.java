package com.example.demo.dto.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    String requestId;
    String code;
    String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

