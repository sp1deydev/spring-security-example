package com.example.demo.exception;

import com.example.demo.utils.ErrorCode;
import lombok.Data;

@Data
public class ApplicationException extends RuntimeException {
    private String code;
    private String message;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }

    public ApplicationException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getErrorCode();
        this.message = message;
    }
}
