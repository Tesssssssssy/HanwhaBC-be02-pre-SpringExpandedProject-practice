package com.example.expandedproject.exception;

import com.example.expandedproject.exception.entityException.CartException;
import com.example.expandedproject.exception.entityException.MemberException;
import com.example.expandedproject.exception.entityException.ProductException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
public class GlobalExceptionAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity handleMemberException(MemberException e) {
        log.error("MemberException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity handleCartException(CartException e) {
        log.error("CartException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(ProductException.class)
    public ResponseEntity handleProductException(ProductException e) {
        log.error("ProductException : [{}] - {}", e.getErrorCode().getStatus(), e.getMessage());
        return makeResponseEntity(e.getErrorCode());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return makeResponseEntity(ErrorCode.INVALID_INPUT);
    }

    private ResponseEntity makeResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus()).body(
                ErrorResponse.builder()
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build()
        );
    }
}
