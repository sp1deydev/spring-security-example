package com.example.demo.exception;

import com.example.demo.dto.Response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("ERROR: ", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException ex) {
        log.info("[handleApplicationException] - handle exception: {} with message: {}", ex.getCode(), ex.getMessage());

        ErrorResponse responseData = new ErrorResponse(ex.getCode(), ex.getMessage());
        String traceId = UUID.randomUUID().toString();

        responseData.setRequestId(traceId);
        return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
    }

}
