package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.util.exception.*;
import by.anpoliakov.tiffbank.domain.dto.UserExceptionResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {
    //TODO: обработать лучшим образом
    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(UserNotCreatedException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(ValidationException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(UserNotFoundException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(UserAccountException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(ContactException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(ExpiredJwtException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(AccountException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<UserExceptionResponse> handleException(TransferException e) {
        UserExceptionResponse response = new UserExceptionResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
