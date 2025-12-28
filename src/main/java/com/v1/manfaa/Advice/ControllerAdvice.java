package com.v1.manfaa.Advice;

import com.v1.manfaa.Api.ApiException;
import com.v1.manfaa.Api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> APIException(ApiException apiExecption) {
        String message = apiExecption.getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(message));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> ValidException(MethodArgumentNotValidException e){
        String error = e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(400).body(new ApiResponse(error));
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadable(){
        return ResponseEntity.status(400).body(new ApiResponse("your input not readable"));
    }

    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<?> urlError(){
        return ResponseEntity.status(404).body(new ApiResponse("wrong Url"));
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)

    public ResponseEntity<?> SQLError(ApiException apiException){
        return ResponseEntity.status(400).body(new ApiResponse(apiException.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException duplicateEntry) {
        return ResponseEntity.status(400).body(new ApiResponse(duplicateEntry.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> TypeMismatchError(MethodArgumentTypeMismatchException mismatchError){
        return ResponseEntity.status(400).body(new ApiResponse(("wrong value type entered did you do a word in place of a number?")));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationException(ConstraintViolationException e){
        String error = e.getConstraintViolations().iterator().next().getMessage();
        return ResponseEntity.status(400).body(new ApiResponse(error));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> DuplicateKeyException(DuplicateKeyException duplicateEntry) {
        return ResponseEntity.status(400).body(new ApiResponse("the email or username is already taken please choose another"));
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> NullPointerException(NullPointerException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e){
        return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFound(UsernameNotFoundException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    // Handle Access Denied (403)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException e){
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
